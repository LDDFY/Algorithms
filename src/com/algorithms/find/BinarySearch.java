package com.algorithms.find;

import java.util.Arrays;

/**
 * 搜索之-二分搜索
 */
public class BinarySearch {

    public static int recursiveRank(int key, int[] arr, int low, int height) {
        if (low > height) {
            return -1;
        }
        int mind = (low + height) / 2;
        if (key > arr[mind]) {
            return recursiveRank(key, arr, mind + 1, height);
        } else if (key < arr[mind]) {
            return recursiveRank(key, arr, low, height - 1);
        } else {
            return mind;
        }
    }

    public static int rank(int key, int[] arr) {
        int low = 0;
        int height = arr.length - 1;
        while (low <= height) {
            int mind = (low + height) / 2;
            if (key < arr[mind]) {
                height = mind - 1;
            } else if (key > arr[mind]) {
                low = mind + 1;
            } else {
                return mind;
            }
        }
        return -1;
    }

    public static void print(int[] arr) {
        for (Integer i : arr) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] arr = new int[]{5, 36, 41, 49, 64, 64, 78, 86, 92, 97};
        Arrays.sort(arr);
        print(arr);
        System.out.println(rank(64, arr));
        System.out.println(recursiveRank(64, arr, 0, arr.length - 1));
    }
}
