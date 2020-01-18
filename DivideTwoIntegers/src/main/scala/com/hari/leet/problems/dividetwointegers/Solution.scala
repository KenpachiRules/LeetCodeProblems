package com.hari.leet.problems.dividetwointegers

object Solution1 {

  val isNegated: (Int, Int) => Boolean = (input1, input2) => if (input1 < 0 && input2 < 0) false
  else if (input1 > 0 && input2 > 0) false else true

  val MAX_VALUE_COND: (Int, Int) => Boolean = (dividend, divisor) => dividend > Integer.MAX_VALUE && divisor == 1
  val MIN_VALUE_COND: (Int, Int) => Boolean = (dividend, divisor) => dividend <= Integer.MIN_VALUE && divisor == -1

  val negate: Int => Int = input => if (input > 0) -input else input

  def divide(dividend: Int, divisor: Int): Int = {
    // take the reverse approach , negate and check for the condition of
    // greater than zero.
    if (MIN_VALUE_COND(dividend, divisor))
      return Integer.MAX_VALUE
    else if (MAX_VALUE_COND(dividend, divisor))
      return Integer.MAX_VALUE
    var upDividend = negate(dividend)
    var upDivisor = negate(divisor)
    var result = 0
    if (upDivisor < upDividend)
      return 0
    else {
      while (upDividend < 0 && upDivisor >= upDividend) {
        upDividend -= upDivisor
        result += 1
      }
    }
    if (isNegated(dividend, divisor))
      -result
    else
      result
  }

}