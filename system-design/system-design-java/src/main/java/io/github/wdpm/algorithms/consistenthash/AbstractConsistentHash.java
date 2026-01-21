package io.github.wdpm.algorithms.consistenthash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 抽象的一致哈希
 *
 * @author evan
 * @date 2020/5/6
 */
public abstract class AbstractConsistentHash {

    /**
     * 新增节点
     *
     * @param key
     * @param value
     */
    protected abstract void add(long key, String value);

    /**
     * 排序节点
     */
    protected abstract void sort();

    /**
     * 根据key查找节点
     *
     * @param key
     * @return
     */
    public abstract String firstNodeValue(String key);

    /**
     * 添加节点列表
     *
     * @param values
     */
    public void process(List<String> values) {
        for (String value : values) {
            add(hash(value), value);
        }

        sort();
    }

    /**
     * 该hash算法非常关键，必须充分考虑到每一位。作用：将String转化为Long类型的hash值。
     * <p>
     * 传统的String.hashCode()生成的hash值很可能偏小或者偏大
     *
     * @param key
     * @return
     */
    protected Long hash(String key) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported.", e);
        }
        md5.reset();
        byte[] keyBytes;
        keyBytes = key.getBytes(StandardCharsets.UTF_8);
        md5.update(keyBytes);

        byte[] digest = md5.digest();
        long hashCode = ((long) (digest[3] & 0xFF) << 24)
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | ((long) (digest[0] & 0xFF));
        return hashCode & 0xffffffffL;
    }

}
