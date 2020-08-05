package com.algorithms.hash;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash算法不带虚拟节点
 *
 * @author :changhao
 * @date :Created in 2020/8/3 21:58
 */
public class ConsistentHashingWithoutVirtualNode {
	/**
	 * 待添加入Hash环的服务器列表
	 */
	private static final List<String> SERVER_LIST =
		Arrays.asList(
			"192.168.0.0:111",
			"192.168.0.1:111",
			"192.168.0.2:111",
			"192.168.0.3:111",
			"192.168.0.4:111");

	/**
	 * key表示服务器的hash值，value表示服务器的名称
	 */
	private static final SortedMap<Integer, String> SORTED_MAP = new TreeMap<>();


	static {
		SERVER_LIST.forEach(s -> SORTED_MAP.put(HashFunction.FNV1_32_HASH(s), s));
	}

	public static String getServerUrl(String node) {
		int hash = HashFunction.FNV1_32_HASH(node);
		SortedMap<Integer, String> subMap = SORTED_MAP.tailMap(hash);
		if (subMap.isEmpty()) {
			subMap = SORTED_MAP;
		}
		String url = subMap.get(subMap.firstKey());
		int index = subMap.firstKey();
		int sub = Math.abs(subMap.firstKey() - hash);
		return MessageFormat.format("index:{0}\t sub:{1}\t url:{2}", index, sub, url);
	}

	public static void main(String[] args) {
		SORTED_MAP.forEach((k, v) -> System.out.println(k + ":\t" + v));
		System.out.println("--------------------------------");
		Arrays.asList(
			"127.0.0.1:1111",
			"221.226.0.1:2222",
			"10.211.0.1:3333",
			"222.213.13.23:2323",
			"223.213.34.67:2341"
		).forEach(e -> System.out.println(getServerUrl(e)));
	}
}
