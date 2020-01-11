package com.hari.conding.leet.fibo;
import java.util.HashMap;
import java.util.Map;

public class Solution {

	private final Map<Integer, Integer> memoizeMap;

	public Solution() {
		memoizeMap = new HashMap<>();
		memoizeMap.put(0, 0);
		memoizeMap.put(1, 1);
	}

	public int fib(int N) {
		return tailRecursionFib(0, N, 0);
	}

	private int tailRecursionFib(int iter, int N, int acc) {
		if (iter == N + 1)
			return acc;
		return tailRecursionFib(iter + 1, N,
				memoizeMap.computeIfAbsent(iter, iter1 -> memoizeMap.get(iter1 -1) + memoizeMap.get(iter1 - 2)));
	}

}
