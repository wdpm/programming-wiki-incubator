# CopyOnWriteArrayList

copy on write 写时复制是什么意思？

写操作时，复制原来数组到新数组，再增删改，最后重设数组引用。

## CopyOnWriteArrayList源码解析

### 构造方法

```java
    public CopyOnWriteArrayList() {
        setArray(new Object[0]);
    }

    public CopyOnWriteArrayList(Collection<? extends E> c) {
        Object[] elements;
        if (c.getClass() == CopyOnWriteArrayList.class)
            elements = ((CopyOnWriteArrayList<?>)c).getArray();
        else {
            elements = c.toArray();
            // c.toArray might (incorrectly) not return Object[] (see 6260652)
            if (elements.getClass() != Object[].class)
                elements = Arrays.copyOf(elements, elements.length, Object[].class);
        }
        setArray(elements);
    }

    public CopyOnWriteArrayList(E[] toCopyIn) {
        setArray(Arrays.copyOf(toCopyIn, toCopyIn.length, Object[].class));
    }
```

三个构造方法：都是将输入转化为对象数组，即Object[]。

### 添加元素

```java
public boolean add(E e)
public void add(int index, E element)
public boolean addIfAbsent(E e)
public int addAllAbsent(Collection<? extends E> c)
public boolean addAll(Collection<? extends E> c)
public boolean addAll(int index, Collection<? extends E> c)
```

add(E e)

```java
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
```

<img src="assets/CopyOnWriteArrayList#add.PNG" style="zoom:50%;" />

先复制Array快照，然后在新数组上添加元素。整个过程原子性是通过独占锁实现的。

其他版本的add方法都是使用这种独占锁，内部先复制Array快照，然后再添加元素实现。

### 获取元素

```java
public E get(int index) {
    return get(getArray(), index);
}

@SuppressWarnings("unchecked")
private E get(Object[] a, int index) {
	return (E) a[index];
}

final Object[] getArray() {
    return array;
}

private transient volatile Object[] array;
```

第一步：getArray（）获取整个数组。

第二步：根据index获取指定位置的元素。

**array只是使用volatile修饰，所以不能保证操作原子性**。Get操作会产生写时复制的弱一致性问题。

### 修改指定元素

```java
    public E set(int index, E element) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            E oldValue = get(elements, index);

            if (oldValue != element) {
                int len = elements.length;
                Object[] newElements = Arrays.copyOf(elements, len);
                newElements[index] = element;
                setArray(newElements);
            } else {
                // Not quite a no-op; ensures volatile write semantics
                setArray(elements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }
```

`Not quite a no-op; ensures volatile write semantics`

这句话的意思是：当oldValue等于要设置的value时，并不是什么都不做。为了保证voltile写语义，还是重新设置了elements的内容。

### 删除元素

```java
    public E remove(int index) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            E oldValue = get(elements, index);
            int numMoved = len - index - 1;//需要移动的元素数目
            if (numMoved == 0)//说明删除的刚好是尾部的一个元素
                setArray(Arrays.copyOf(elements, len - 1));//直接截断到len-1即可
            else {
                Object[] newElements = new Object[len - 1];//新建长度为len-1的数组
                System.arraycopy(elements, 0, newElements, 0, index);//复制前部分
                System.arraycopy(elements, index + 1, newElements, index,//复制后部分
                                 numMoved);
                setArray(newElements);
            }
            return oldValue;
        } finally {
            lock.unlock();
        }
    }
```

### 弱一致性的迭代器

```java
    public Iterator<E> iterator() {
        return new COWIterator<E>(getArray(), 0);
    }
```

```java
    static final class COWIterator<E> implements ListIterator<E> {
        /** Snapshot of the array */
        private final Object[] snapshot;
        /** Index of element to be returned by subsequent call to next.  */
        private int cursor;

        private COWIterator(Object[] elements, int initialCursor) {
            cursor = initialCursor;
            snapshot = elements;
        }

        public boolean hasNext() {
            return cursor < snapshot.length;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            if (! hasNext())
                throw new NoSuchElementException();
            return (E) snapshot[cursor++];
        }

        @SuppressWarnings("unchecked")
        public E previous() {
            if (! hasPrevious())
                throw new NoSuchElementException();
            return (E) snapshot[--cursor];
        }
        ...
    }
```

如果在线程1遍历元素过程中，线程2也对元素增删改，但是线程1操作的依然是旧的array副本。因为线程2的结果对线程不可见。这就是弱一致性。

测试例子

```java
    static volatile CopyOnWriteArrayList<String> list= new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        list.add("hello");
        list.add("world");
        list.add("foo");

        Thread thread = new Thread(() -> {
            list.set(1, "dfhueyhf");
            list.remove(2);
        });

        Iterator<String> it = list.iterator();

        thread.start();
        thread.join();

        while (it.hasNext()) {
           System.out.println(it.next());
        }
    }
```

```
hello
world
foo
```

说明子线程thread的全部操作对main线程不可见。

