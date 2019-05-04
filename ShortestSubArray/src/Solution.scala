object Solution {
  val defaultVal: Tuple2[Int, Int] = (-1, -1)
  var shortArraySize: Tuple2[Int, Int] = defaultVal

  def shortestSubarray(A: Array[Int], K: Int): Int = {

    val emptySum = Sum(-1, 0)
    traverseFwd(A, 0, emptySum, K)
    if (shortArraySize.equals(defaultVal))
      -1
    else
      (shortArraySize._2 - shortArraySize._1) + 1
  }

  def traverseFwd(arr: Array[Int], pos: Int, sum: Sum, totalSum: Int): Tuple2[Int, Int] = {
    if (pos == arr.size)
      return shortArraySize
    if (arr(pos) >= totalSum) {
      shortArraySize = (pos, pos)
      return shortArraySize
    }
    // if not then try reaching out to next element.
    val delta = totalSum - (sum.sum + arr(pos))
    if (delta <= 0) {
      // sequence is achieved but not sure whether it is the smallest or the largest.
      val startIdx = traverseBackward(arr, pos - 1, totalSum - arr(pos))
      if (shortArraySize.equals(defaultVal))
        shortArraySize = (startIdx, pos)
      else if ((pos - startIdx) < (shortArraySize._2 - shortArraySize._1))
        shortArraySize = (startIdx, pos)
      shortArraySize
    } else {
      val nxt = Sum(pos, arr(pos) + sum.sum)
      traverseFwd(arr, pos + 1, nxt, totalSum)
    }
  }

  def traverseBackward(arr: Array[Int], pos: Int, totalSum: Int): Int = {
    if (pos <= 0) {
      return 0
    }
    val delta = totalSum - arr(pos)
    if (delta <= 0)
      pos
    else
      traverseBackward(arr, pos - 1, totalSum - arr(pos))
  }

}

case class Sum(pos: Int, sum: Int)