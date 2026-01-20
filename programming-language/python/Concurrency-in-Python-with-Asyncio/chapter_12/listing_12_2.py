import asyncio
from asyncio import Queue
from random import randrange


class Product:
    def __init__(self, name: str, checkout_time: float):
        self.name = name
        self.checkout_time = checkout_time


class Customer:
    def __init__(self, customer_id, products):
        self.customer_id = customer_id
        self.products = products


async def checkout_customer(queue: Queue, cashier_number: int):
    while True:
        customer: Customer = await queue.get()
        print(f'Cashier {cashier_number} '
              f'checking out customer '
              f'{customer.customer_id}')
        for product in customer.products:
            print(f"Cashier {cashier_number} "
                  f"checking out customer "
                  f"{customer.customer_id}'s {product.name}")
            await asyncio.sleep(product.checkout_time)
        print(f'Cashier {cashier_number} '
              f'finished checking out customer '
              f'{customer.customer_id}')
        queue.task_done()


def generate_customer(customer_id: int) -> Customer:  # A
    all_products = [Product('beer', 2),
                    Product('bananas', .5),
                    Product('sausage', .2),
                    Product('diapers', .2)]
    products = [all_products[randrange(len(all_products))]
                for _ in range(randrange(10))]
    return Customer(customer_id, products)


async def customer_generator(queue: Queue):  # B
    customer_count = 0

    while True:
        customers = [generate_customer(i)
                     for i in range(customer_count, customer_count + randrange(5))]
        for customer in customers:
            print('Waiting to put customer in line...')
            await queue.put(customer)
            print('Customer put in line!')
        customer_count = customer_count + len(customers)
        await asyncio.sleep(1)


async def main():
    customer_queue = Queue(5)

    customer_producer = asyncio.create_task(customer_generator(customer_queue))

    cashiers = [asyncio.create_task(checkout_customer(customer_queue, i))
                for i in range(3)]

    await asyncio.gather(customer_producer, *cashiers)


asyncio.run(main())

# 会出现这种场景：消费者队列已满，生产者在阻塞等待，等待队列有空位可以放进去。
# Waiting to put customer in line...
# Cashier 0 finished checking out customer 42
# Cashier 0 checking out customer 46
# Cashier 0 checking out customer 46's bananas
# Customer put in line!

# 优雅停机：停下新的生产（停止生产），处理正在处理的任务，然后关闭服务。
