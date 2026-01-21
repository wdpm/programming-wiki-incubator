package io.github.wdpm.algorithms;

import java.util.Arrays;

/**
 * 数组向右移动K次
 *
 * @author evan
 * @date 2020/5/7
 */
public class ArrayShift {

    /**
     * <li>1. 先将整个数组逆序</li>
     * <li>2. 将前K个元素，翻转</li>
     * <li>3. 将后len-K个元素，翻转</li>
     * <p>
     * 举例：[1,2,3,4,5,6,7] 右移3次
     * <pre>
     * 1. => [7,6,5,4,3,2,1]
     * 2. => [5,6,7,4,3,2,1]
     * 3. => [5,6,7,1,2,3,4]
     * </pre>
     *
     * @param array
     * @param count
     * @return array after shifted
     */
    public int[] rightShift(int[] array, int count) {
        int[] arrCopy = Arrays.copyOf(array, array.length);

        // reverse whole list [0...len-1] len elements
        reverseArray(arrCopy, 0, arrCopy.length);

        // reverse first K element .[0...k-1] k elements
        reverseArray(arrCopy, 0, count);

        // reverse last len-K element [k...len-1] len-k elements
        reverseArray(arrCopy, count, arrCopy.length);

        return arrCopy;
    }

    /**
     * @param array     array to reverse
     * @param fromIndex the initial index, inclusive
     * @param toIndex   the final index, exclusive
     */
    private void reverseArray(int[] array, int fromIndex, int toIndex) {
        // fromIndex   (fromIndex+toIndex-1)/2   toIndex-1
        // i             center                    ?
        //
        // center= (i+?)/2=(fromIndex+toIndex-1)/2
        // ?= fromIndex+toIndex-1-i
        for (int i = fromIndex; i < fromIndex + (toIndex - fromIndex) / 2; i++) {
            int temp = array[i];
            array[i] = array[fromIndex + toIndex - 1 - i];
            array[fromIndex + toIndex - 1 - i] = temp;
        }
    }

    public static void main(String[] args) {
        int[]      array = {1, 2, 3, 4, 5, 6, 7};
        ArrayShift shift = new ArrayShift();

        int[] arr1 = shift.rightShift(array, 3);
        Arrays.stream(arr1).forEach(System.out::println);

        int[] arr2 = shift.rightShift(array, 5);
        Arrays.stream(arr2).forEach(System.out::println);
    }

}
