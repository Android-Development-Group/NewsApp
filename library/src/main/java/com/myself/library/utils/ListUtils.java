package com.myself.library.utils;

import android.support.v4.util.ArrayMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * list工具类
 * Created by guchenkai on 2015/12/15.
 */
public final class ListUtils {

    /**
     * list非空判断
     *
     * @param list
     * @param <D>
     * @return
     */
    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty() || list.size() <= 0;
    }

    /**
     * 截取list
     *
     * @param source list源
     * @param start  开始标号
     * @param end    结束标号
     * @return 截取后的list
     */
    public static <T extends Serializable> List<T> cutOutList(List<T> source, int start, int end) {
        List<T> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(source.get(i));
        }
        return result;
    }

    /**
     * 判断两个集合是否相等
     *
     * @param a   第一个集合
     * @param b   第二个集合
     * @param <T>
     * @return 是否相等
     */
    public static <T extends Serializable> boolean equals(Collection<T> a, Collection<T> b) {
        if (a == null) return false;
        if (b == null) return false;
        if (a.isEmpty() && b.isEmpty()) return true;
        if (a.size() != b.size()) return false;
        List<T> alist = new ArrayList<>(a);
        List<T> blist = new ArrayList<>(b);
        Collections.sort(alist, new Comparator<T>() {
            public int compare(T o1, T o2) {
                return o1.hashCode() - o2.hashCode();
            }

        });
        Collections.sort(blist, new Comparator<T>() {
            public int compare(T o1, T o2) {
                return o1.hashCode() - o2.hashCode();
            }

        });
        return alist.equals(blist);
    }

    /**
     * ArrayMap按照Key排序
     *
     * @param map
     * @param <K>
     * @param <V>
     */
    public static <K, V> ArrayMap<K, V> sortByKey(ArrayMap<K, V> map) {
        ArrayMap<K, V> arrayMap = new ArrayMap<>();
        K[] keys = (K[]) map.keySet().toArray();
        Arrays.sort(keys);
        for (K key : keys) {
            arrayMap.put(key, map.get(key));
        }
        return arrayMap;
    }

    /**
     * 归类List
     *
     * @param source
     * @param groupIndex
     * @param <T>
     * @return
     */
    public static <T extends Serializable> List<ArrayList<T>> group(List<T> source, int groupIndex) {
        List<ArrayList<T>> result = new ArrayList<>();
        ArrayList<T> group = new ArrayList<>();
        for (T t : source) {
            group.add(t);
            if (group.size() == groupIndex) {
                result.add(group);
                group.clear();
            }
        }
        return result;
    }
}
