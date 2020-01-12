package com.hari.coding.leet.closest.palindrome;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeTest;
import com.hari.coding.leet.closest.palindrome.Solution;

public class TestClosestPalindrome {

	private Solution sol;

	@BeforeTest
	public void init() {
		sol = new Solution();
	}

	@Test
	public void test2DigitInput() {
		assertTrue("22".equals(sol.nearestPalindromic("19")));
	}

	@Test
	public void test3DigitInput() {
		assertTrue("414".equals(sol.nearestPalindromic("419")));
	}

	@Test
	public void test7DigitInput() {
		assertTrue("1234321".equals(sol.nearestPalindromic("1234789")));
	}
	
	@Test
	public void testAlreadyPalinInput() {
		assertTrue("77".equals(sol.nearestPalindromic("88")));
	}
	
	@Test
	public void test99PalinInput() {
		assertTrue("99".equals(sol.nearestPalindromic("100")));
	}
	
	@Test
	public void test999PalinInput() {
		assertTrue("999".equals(sol.nearestPalindromic("1001")));
	}
	
	@Test
	public void testFailedInput() {
	   String value = sol.nearestPalindromic("807045053224792883");
	   System.out.println(String.format(" Value is %s",value));
	}	
	
	
	
}
