package com.hari.leet.problems

import scala.util.control.Breaks._
import scala.collection.mutable.{ Set, ListBuffer, LinkedHashSet }

object Solution {

  val EASY_PRIMES = List(2, 3, 5, 7, 11)
  val PRIMES_PER_DIGITS = List(101, 1001, 10001, 100001, 1000001, 10000001, 100000001)

  def main(args: Array[String]): Unit = {
    println(primePalindrome(1215122))
  }

  def primePalindrome(N: Int): Int = {
    var input = N
    var result = 0
    if (input <= 11) {
      val prime_palindromes: List[Int] = EASY_PRIMES.filter(prime => prime >= input)
      if (!prime_palindromes.isEmpty)
        return prime_palindromes(0)
    }
    // there are no two digit numbers which are a palindrone except for 11 hence if there are 2 digit numbers start exploring 3 digits.
    val indexToStart = if (input.toString().toCharArray.size == 2) 0 else input.toString().toCharArray().size - 3
    for (i <- indexToStart to 6) {
      if (PRIMES_PER_DIGITS(i).toString().size % 2 == 0) {
        // even palindromes
        val num = constructEvenPalindrome(PRIMES_PER_DIGITS(i).toString().toCharArray(), input, 0, PRIMES_PER_DIGITS(i).toString().size - 1, new ListBuffer[Int]())
        if (num != -1)
          return num
        else if (i + 1 < PRIMES_PER_DIGITS.size)
          input = PRIMES_PER_DIGITS(i + 1)
      } else {
        // odd palindromes
        val num = constructOddPalindrome(PRIMES_PER_DIGITS(i).toString().toCharArray(), input, 0, PRIMES_PER_DIGITS(i).toString().size - 1, new ListBuffer[Int]())
        if (num != -1)
          return num
        else if (i + 1 < PRIMES_PER_DIGITS.size)
          input = PRIMES_PER_DIGITS(i + 1)
      }
    }
    input
  }

  def findPrimeOrNot: Int => Boolean = num => {
    if (num % 2 == 0) // split the number into odd or even.
      false
    else {
      // now try diving it with odd numbers less than or equal to the number
      var begin = 3
      var isPrime: Option[Boolean] = None
      while (begin < num && !(isPrime.isDefined && !isPrime.get)) {
        if (num % begin == 0) {
          isPrime = Some(false)
        } else
          begin = begin + 2
      }
      isPrime.getOrElse(true)
    }

  }

  //** test constructing String as palindrome

  def constructEvenPalindrome(chars: Array[Char], input: Int, startPos: Int, endPos: Int, found: ListBuffer[Int]): Int = {
    var inputChars = chars
    if (found.size > 0)
      return found(0)

    for (i <- 0 to 9) {
      if (!(i == 0 && startPos == 0 && endPos == chars.size - 1)) {
        inputChars(startPos) = (i + "").charAt(0)
        inputChars(endPos) = (i + "").charAt(0)
        if (endPos - startPos > 1)
          constructEvenPalindrome(inputChars, input, startPos + 1, endPos - 1, found)
        else {
          val numAsStr = new String(inputChars)
          if (input <= numAsStr.toInt && findPrimeOrNot(numAsStr.toInt) && found.size == 0) {
            found += numAsStr.toInt
            return found(0)
          } else if (found.size > 0)
            return found(0)
        }
      }
    }
    if (found.isEmpty)
      -1
    else
      found(0)
  }

  def constructOddPalindrome(chars: Array[Char], input: Int, startPos: Int, endPos: Int, found: ListBuffer[Int]): Int = {
    var inputChars = chars
    val midIndex = chars.length / 2
    if (found.size > 0)
      return found(0)
    for (i <- 0 to 9) {
      for (j <- 0 to 9) {
        if (!(i == 0 && startPos == 0 && endPos == chars.size - 1)) {
          inputChars(startPos) = (i + "").charAt(0)
          inputChars(endPos) = (i + "").charAt(0)
          inputChars(midIndex) = (j + "").charAt(0)
          if (endPos - startPos > 2)
            constructOddPalindrome(inputChars, input, startPos + 1, endPos - 1, found)
          else {
            val numAsStr = new String(inputChars)
            if (input <= numAsStr.toInt) {
              if (findPrimeOrNot(numAsStr.toInt) && found.size == 0) {
                found += numAsStr.toInt
                return found(0)
              } else if (found.size > 0)
                return found(0)
            }
          }
        }
      }
    }
    if (found.isEmpty)
      -1
    else
      found(0)
  }

}