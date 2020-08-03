package com.algorithms.hash;

/**
 * @author :changhao
 * @date :Created in 2020/8/3 22:06
 */
public abstract class HashFunction {

	/**
	 * 计算key的hash值
	 *
	 * @param key
	 * @return
	 */
	public static int FNV1_32_HASH(String key) {
		final int p = 16777619;
		int hash = (int) 2166136261L;
		for (int i = 0; i < key.length(); i++) {
			hash = (hash ^ key.charAt(i)) * p;
		}
		hash += hash << 13;
		hash ^= hash >> 7;
		hash += hash << 3;
		hash ^= hash >> 17;
		hash += hash << 5;
		// 如果算出来的值为负数则取其绝对值
		return Math.abs(hash);
	}
}
