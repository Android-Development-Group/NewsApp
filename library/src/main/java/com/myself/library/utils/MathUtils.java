package com.myself.library.utils;

import java.math.BigDecimal;

/**
 * 数学工具
 * Created by guchenkai on 2015/12/17.
 */
public final class MathUtils {

    /**
     * 加法
     *
     * @param num1 被加数
     * @param num2 加数
     * @return 和
     */
    public static String add(String num1, String num2) {
        BigDecimal b1 = new BigDecimal(num1);
        BigDecimal b2 = new BigDecimal(num2);
        return b1.add(b2).toString();
    }

    /**
     * 减法
     *
     * @param num1 被减数
     * @param num2 减数
     * @return 差
     */
    public static String subtract(String num1, String num2) {
        BigDecimal b1 = new BigDecimal(num1);
        BigDecimal b2 = new BigDecimal(num2);
        return b1.subtract(b2).toString();
    }

    /**
     * 乘法
     *
     * @param figure      数字
     * @param coefficient 因数
     * @return 乘积
     */
    public static String multiplication(String figure, int coefficient) {
        BigDecimal b1 = new BigDecimal(figure);
        BigDecimal b2 = new BigDecimal(coefficient);
        return b1.multiply(b2).toString();
    }

    /**
     * 乘法
     *
     * @param figure      数字
     * @param coefficient 因数
     * @return 乘积
     */
    public static String multiplication(String figure, String coefficient) {
        BigDecimal b1 = new BigDecimal(figure);
        BigDecimal b2 = new BigDecimal(coefficient);
        return b1.multiply(b2).toString();
    }

    /**
     * 比较两个数字大小
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 数字大小
     */
    public static boolean compare(String num1, String num2) {
        double number1 = Double.parseDouble(num1);
        double number2 = Double.parseDouble(num2);
        return Math.max(number1, number2) == number1;
    }

    /**
     * 比较两个数字大小
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 数字大小
     */
    public static boolean compareIntFromString(String num1, String num2) {
        double number1 = Integer.parseInt(num1);
        double number2 = Integer.parseInt(num2);
        return number1 > number2;
    }

    /**
     * 比较两个数字大小
     *
     * @param num1 第一个数字
     * @param num2 第二个数字
     * @return 数字大小
     */
    public static boolean compareBigOrEqualsIntFromString(String num1, String num2) {
        double number1 = Integer.parseInt(num1);
        double number2 = Integer.parseInt(num2);
        return number1 >= number2;
    }
}
