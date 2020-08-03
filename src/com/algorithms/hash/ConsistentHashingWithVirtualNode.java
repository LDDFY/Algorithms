package com.algorithms.hash;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 一致性hash算法带虚拟节点
 *
 * @author :changhao
 * @date :Created in 2020/8/3 21:58
 */
public class ConsistentHashingWithVirtualNode {
	/**
	 * 待添加入Hash环的服务器列表
	 */
	private static List<String> SERVER_LIST =
		Arrays.asList(
			"192.168.0.0:111",
			"192.168.0.1:111",
			"192.168.0.2:111",
			"192.168.0.3:111",
			"192.168.0.4:111");

	/**
	 * 真实结点列表,考虑到服务器上线、下线的场景，即添加、删除的场景会比较频繁，这里使用LinkedList会更好
	 */
	private static final List<String> REAL_NODES = new LinkedList<>();

	/**
	 * 虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
	 */
	private static final SortedMap<Integer, String> VIRTUAL_NODES = new TreeMap<>();

	/**
	 * 虚拟节点的数目，这里写死，为了演示需要，一个真实结点对应5个虚拟节点
	 */
	private static final int VIRTUAL_NODES_SIZE = 5;

	static {
		// 先把原始的服务器添加到真实结点列表中
		REAL_NODES.addAll(SERVER_LIST);
		REAL_NODES.forEach(e -> IntStream.range(0, VIRTUAL_NODES_SIZE).forEach(r -> {
			String virtualNodeName = e + "&&VN" + r;
			int hash = HashFunction.FNV1_32_HASH(virtualNodeName);
			System.out.println("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
			VIRTUAL_NODES.put(hash, virtualNodeName);
		}));
	}

	public static String getServerURL(String node) {
		int hash = HashFunction.FNV1_32_HASH(node);
		SortedMap<Integer, String> subMap = VIRTUAL_NODES.tailMap(hash);
		if (subMap.isEmpty()) {
			subMap = VIRTUAL_NODES;
		}
		String url = subMap.get(subMap.firstKey());
		return url.substring(0, url.indexOf("&&"));
	}

	public static void main(String[] args) {
		VIRTUAL_NODES.forEach((k, v) -> System.out.println(k + ":\t" + v));
		System.out.println("--------------------------------");
		Arrays.asList(
			"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333", "223.213.34.67:2341"
		).forEach(e -> System.out.println(getServerURL(e)));
	}
}
