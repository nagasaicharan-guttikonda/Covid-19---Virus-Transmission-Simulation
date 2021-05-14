package edu.neu.info6205.helper;

import java.util.Random;

/**
 * a singleton mode random generator
 *
 * @author Joseph Yuanhao Li
 * @date 4/12/21 13:30
 */
public class RandomUtil {
    private static RandomUtil randomUtil = new RandomUtil();

    private final Random random;
    private RandomUtil(){
        random = new Random(31);
    }

    public static Random random(){
        return randomUtil.random;
    }
}
