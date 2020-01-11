package com.hari.coding.leet.test.fibo;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import static org.testng.Assert.assertTrue;

import com.hari.conding.leet.fibo.Solution;

public class TestFib {

	private Solution sol;

	@BeforeTest
	public void init() {
		sol = new Solution();
	}

	@Test
	public void testFiboUpto5() {
		assertTrue(sol.fib(5) == 5);
	}

	@Test
	public void testFiboUpto10() {
		assertTrue(sol.fib(10) == 55);
	}

	@Test
	public void testFiboUpto15() {
		assertTrue(sol.fib(15) == 610);
	}

}
