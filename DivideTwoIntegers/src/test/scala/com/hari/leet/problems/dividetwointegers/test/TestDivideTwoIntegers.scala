package com.hari.leet.problems.dividetwointegers.test

import org.testng.Assert.assertEquals
import com.hari.leet.problems.dividetwointegers.Solution1
import org.testng.annotations.Test

class TestDivideTwoIntegers {

  @Test
  def simpleCase(): Unit = {
    val result = Solution1.divide(40, 5)
    println(s"Result is $result")
    assertEquals(Solution1.divide(40, 5), 8)
  }

  @Test
  def negativeDividend: Unit = {
    val result = Solution1.divide(1, -1)
    println(s"Result is $result")
    assertEquals(result, -1)
  }

  @Test
  def integerOverflow: Unit = {
    val result = Solution1.divide(10, 3)
    println(s"Result is $result")
    assertEquals(result, 3)
  }

  @Test
  def dividendLessThanDivisor: Unit = {
    val result = Solution1.divide(1, 3)
    println(s"Result is $result")
    assertEquals(result, 0)
  }

  @Test
  def exceedIntLimit: Unit = {
    val result = Solution1.divide(java.lang.Integer.parseInt("-2147483648"), -1)
    println(s"Result is $result")
    assertEquals(result, Integer.MAX_VALUE)
  }

  @Test
  def minDividendBy2: Unit = {
    val result = Solution1.divide(java.lang.Integer.parseInt("-2147483648"), 2)
    println(s"Result is $result")
    assertEquals(Solution1.divide(java.lang.Integer.parseInt("-2147483648"), 2), -1073741824)
  }

}