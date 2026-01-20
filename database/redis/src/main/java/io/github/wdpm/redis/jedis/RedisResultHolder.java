package io.github.wdpm.redis.jedis;

/**
 * @author evan
 * @date 2020/6/8
 */
public class RedisResultHolder<T> {
    private T value;

    public RedisResultHolder() {
    }

    public RedisResultHolder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
