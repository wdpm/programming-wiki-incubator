import asyncio
from asyncio import Queue
from random import randrange
from typing import List


class Product:
    def __init__(self, name: str, checkout_time: float):
        self.name = name
        self.checkout_time = checkout_time


class Customer:
    def __init__(self, customer_id: int, products: List[Product]):
        self.customer_id = customer_id
        self.products = products


async def checkout_customer(queue: Queue, cashier_number: int):
    while not queue.empty(): #A
        customer: Customer = queue.get_nowait()
        print(f'Cashier {cashier_number} '
              f'checking out customer '
              f'{customer.customer_id}')

        for product in customer.products: #B
            print(f"Cashier {cashier_number} "
                  f"checking out customer "
                  f"{customer.customer_id}'s {product.name}")
            await asyncio.sleep(product.checkout_time)

        print(f'Cashier {cashier_number} '
              f'finished checking out customer '
              f'{customer.customer_id}')

        # Once a customer is checked out, we call queue.task_done. This signals to the queue
        # that our worker has finished its current work item.
        queue.task_done()


async def main():
    customer_queue = Queue()

    all_products = [Product('beer', 2),
                    Product('bananas', .5),
                    Product('sausage', .2),
                    Product('diapers', .2)]

    for i in range(10): #C
        # get 10 random  products
        products = [all_products[randrange(len(all_products))]
                    for _ in range(randrange(10))]

        # create 10 customers with random products
        # nowait?
        # There are two ways of getting and retrieving an item from a queue:
        # - one that is a coroutine and blocks,
        # - and one that is nonblocking and is a regular method

        # The get_nowait and put_nowait variants instantly perform the non-blocking method calls and return
        customer_queue.put_nowait(Customer(i, products))

    # 3 cashiers to consume checkout
    cashiers = [asyncio.create_task(checkout_customer(customer_queue, i))
                for i in range(3)] #D

    await asyncio.gather(customer_queue.join(), *cashiers)

asyncio.run(main())

# Cashier 0 checking out customer 0
# Cashier 0 checking out customer 0's sausage
# Cashier 1 checking out customer 1
# Cashier 1 finished checking out customer 1
# Cashier 1 checking out customer 2
# Cashier 1 checking out customer 2's sausage
# Cashier 2 checking out customer 3
# Cashier 2 checking out customer 3's bananas
# Cashier 0 checking out customer 0's bananas
# Cashier 1 checking out customer 2's beer
# Cashier 2 checking out customer 3's sausage
# Cashier 2 checking out customer 3's sausage
# Cashier 0 checking out customer 0's diapers
# Cashier 2 checking out customer 3's sausage
# Cashier 0 checking out customer 0's sausage
# Cashier 2 checking out customer 3's beer
# Cashier 0 finished checking out customer 0
# Cashier 0 checking out customer 4
# Cashier 0 checking out customer 4's diapers
# Cashier 0 checking out customer 4's sausage
# Cashier 0 checking out customer 4's beer
# Cashier 1 checking out customer 2's diapers
# Cashier 1 checking out customer 2's beer
# Cashier 2 checking out customer 3's sausage
# Cashier 2 finished checking out customer 3
# Cashier 2 checking out customer 5
# Cashier 2 checking out customer 5's beer
# Cashier 0 checking out customer 4's sausage
# Cashier 0 checking out customer 4's bananas
# Cashier 0 finished checking out customer 4
# Cashier 0 checking out customer 6
# Cashier 0 checking out customer 6's beer
# Cashier 1 checking out customer 2's diapers
# Cashier 1 checking out customer 2's diapers
# Cashier 1 checking out customer 2's beer
# Cashier 2 checking out customer 5's bananas
# Cashier 2 checking out customer 5's diapers
# Cashier 2 checking out customer 5's bananas
# Cashier 0 checking out customer 6's diapers
# Cashier 0 checking out customer 6's sausage
# Cashier 2 checking out customer 5's diapers
# Cashier 0 checking out customer 6's sausage
# Cashier 2 finished checking out customer 5
# Cashier 2 checking out customer 7
# Cashier 2 finished checking out customer 7
# Cashier 2 checking out customer 8
# Cashier 2 checking out customer 8's beer
# Cashier 1 checking out customer 2's diapers
# Cashier 0 checking out customer 6's beer
# Cashier 1 finished checking out customer 2
# Cashier 1 checking out customer 9
# Cashier 1 finished checking out customer 9
# Cashier 2 checking out customer 8's sausage
# Cashier 0 checking out customer 6's diapers
# Cashier 2 checking out customer 8's beer
# Cashier 0 checking out customer 6's bananas
# Cashier 0 checking out customer 6's sausage
# Cashier 0 checking out customer 6's sausage
# Cashier 0 finished checking out customer 6
# Cashier 2 finished checking out customer 8