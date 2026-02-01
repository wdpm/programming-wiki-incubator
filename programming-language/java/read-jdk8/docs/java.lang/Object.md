# Object

![](../images/Object-UML.png)

## 方法API

### equals(Object obj)
```java
public boolean equals(Object obj) {
    return (this == obj);
}
```
### toString()
```java
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```

### wait(long timeout, int nanos)
```java
public final void wait(long timeout, int nanos) throws InterruptedException {
    if (timeout < 0) {
        throw new IllegalArgumentException("timeout value is negative");
    }

    if (nanos < 0 || nanos > 999999) {
        throw new IllegalArgumentException(
                            "nanosecond timeout value out of range");
    }

    if (nanos > 0) {
        timeout++;
    }

    wait(timeout);
}
```
