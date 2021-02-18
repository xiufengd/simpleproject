package com.xiufengd.utils.thirdParty;

import java.util.*;

/**
 * 数学集合操作
 */
public class DigtalUtil {
    public static void main(String[] args) {
        Object[] m = {1, 2, 3, 4, 5};
        Object[] n = {3, 4, 6};

        System.out.println("----------并集------------");
        Object[] b = getB(m, n);
        for (Object i : b) {
            System.out.println(i);
        }

        System.out.println("----------交集------------");
        Object[] j = getJ(m, n);
        for (Object i : j) {
            System.out.println(i);
        }

        System.out.println("----------差集------------");
        Object[] c = getC(m, n);
        for (Object i : c) {
            System.out.println(i);
        }
    }

    /**
     * 求并集
     *
     * @param m
     * @param n
     * @return
     */
    public static Object[] getB(Object[] m, Object[] n) {
        // 将数组转换为set集合
        Set<Object> set1 = new HashSet<Object>(Arrays.asList(m));
        Set<Object> set2 = new HashSet<Object>(Arrays.asList(n));

        // 合并两个集合
        set1.addAll(set2);

        Object[] arr = {};
        return set1.toArray(arr);
    }

    /**
     * 求交集
     *
     * @param m
     * @param n
     * @return
     */
    public static Object[] getJ(Object[] m, Object[] n) {
        List<Object> rs = new ArrayList<Object>();

        // 将较长的数组转换为set
        Set<Object> set = new HashSet<Object>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (Object i : m.length > n.length ? n : m) {
            if (set.contains(i)) {
                rs.add(i);
            }
        }

        Object[] arr = {};
        return rs.toArray(arr);
    }

    /**
     * 求差集
     *
     * @param m
     * @param n
     * @return
     */
    public static Object[] getC(Object[] m, Object[] n) {
        // 将较长的数组转换为set
        Set<Object> set = new HashSet<Object>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (Object i : m.length > n.length ? n : m) {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }

        Object[] arr = {};
        return set.toArray(arr);
    }


    public static List<Object> getC(List<Object> m, List<Object> n) {
        // 将较长的数组转换为set
        Set<Object> set = new HashSet<Object>(Arrays.asList(m.size() > n.size() ? m : n));

        // 遍历较短的数组，实现最少循环
        for (Object i : m.size() > n.size() ? n : m) {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }

        List<Object> list = new ArrayList<>();
        set.stream().forEach(item -> list.add(item));
        return list;
    }


}
