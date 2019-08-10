package com.hari.learning.leetcode.utils

import scala.collection.mutable.{ ListBuffer, Set, LinkedHashSet }

/**
 * Utility for generating palindrome in number format.
 * This will generate palindrome numbers for both odd and even number
 * of digits
 *
 * @author harim
 */

object NumericPalindromeGenerator {

  def constructEvenPalindrome(chars: Array[Char], startPos: Int, endPos: Int): ListBuffer[String] = {
    var inputChars = chars
    val palindromes = new ListBuffer[String]()
    for (i <- 0 to 9) {
      if (!(i == 0 && startPos == 0 && endPos == chars.size - 1)) {
        inputChars(startPos) = (i + "").charAt(0)
        inputChars(endPos) = (i + "").charAt(0)
        if (endPos - startPos > 1)
          palindromes ++= constructEvenPalindrome(inputChars, startPos + 1, endPos - 1)
        else {
          palindromes += new String(inputChars)
        }
      }
    }
    palindromes
  }

  def constructOddPalindrome(chars: Array[Char], startPos: Int, endPos: Int): Set[String] = {
    var inputChars = chars
    val palindromes = new LinkedHashSet[String]()
    val midIndex = chars.length / 2
    for (j <- 0 to 9) {
      for (i <- 0 to 9) {
        if (!(i == 0 && startPos == 0 && endPos == chars.size - 1)) {
          inputChars(startPos) = (i + "").charAt(0)
          inputChars(endPos) = (i + "").charAt(0)
          inputChars(midIndex) = (j + "").charAt(0)
          if (endPos - startPos > 2)
            palindromes ++= constructOddPalindrome(inputChars, startPos + 1, endPos - 1)
          else {
            palindromes += new String(inputChars)
          }
        }
      }
    }
    palindromes
  }
}