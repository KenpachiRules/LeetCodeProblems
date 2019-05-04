import scala.collection.mutable.Stack
object Solution {
  def isMatch(s: String, p: String): Boolean = {
    // sanitize regex if there are contiguous multiple * , a single * and multiple stars are same
    var containsStar = false
    val modPattChars = p.toCharArray().zipWithIndex.filter { e =>
      if (e._1 == '*')
        containsStar = true
      if (e._2 > 0 && p(e._2) == '*' && p(e._2 - 1) == '*')
        false
      else
        true
    }.map(e => e._1)
    val inputChars = s.toCharArray()
    var latestPos = 0
    val stackOfChars = new scala.collection.mutable.Stack[Int]()
    if (!containsStar) {
      if (inputChars.length != modPattChars.length)
        return false
    }
    for (i <- 0 until inputChars.length) {
      if (latestPos >= modPattChars.size && (stackOfChars.isEmpty || stackOfChars.top != latestPos - 1))
        return false
      if (latestPos == modPattChars.size) {
        val push = pushBackStack1(stackOfChars, latestPos, inputChars, modPattChars, i)
        latestPos = push._1
        if (!push._2)
          return false
      }

      if (inputChars(i) == modPattChars(latestPos) || modPattChars(latestPos) == '?') {
        // check if previous pos can suite this char and also is it stacked.
/*        if (latestPos > 0 && (inputChars(i) == modPattChars(latestPos - 1) || modPattChars(latestPos - 1) == '*' || modPattChars(latestPos - 1) == '?') && !stackOfChars.isEmpty && stackOfChars.top == latestPos - 1) {
          stackOfChars.push(i)
        }
*/        latestPos += 1
      } else if (modPattChars(latestPos) == '*') {
        // clear the stack if previous elements , at any point of time there needs to be only * sequence backed.
        stackOfChars.clear()
        stackOfChars.push(i)
        if (latestPos < (modPattChars.length - 1) && (modPattChars(latestPos + 1) == '?' || inputChars(i) == modPattChars(latestPos + 1))) {
          latestPos += 1
          stackOfChars.push(i)
          latestPos += 1
        }
      } else {
        val push = pushBackStack1(stackOfChars, latestPos, inputChars, modPattChars, i)
        latestPos = push._1
        if (!push._2)
          return false
      }
    }
    true
  }

  def pushBackStack1(stackOfChars: scala.collection.mutable.Stack[Int], latestPos1: Int, inputChars: Array[Char], modPattChars: Array[Char], i: Int): Tuple2[Int, Boolean] = {
    var latestPos: Int = latestPos1
    if (stackOfChars.isEmpty || i != stackOfChars.top + 1)
      return (latestPos, false)
    val tempStack = new scala.collection.mutable.Stack[Int]()
    while (!stackOfChars.isEmpty) {
      stackOfChars.pop
      latestPos -= 1
      if (inputChars(i) == modPattChars(latestPos) || modPattChars(latestPos) == '?' || modPattChars(latestPos) == '*') {
        tempStack.push(i)
      }
    }
    stackOfChars.pushAll(tempStack)
    (latestPos, !stackOfChars.isEmpty)
  }
  def main(args: Array[String]): Unit = {
  /*  println(isMatch("aa", "a"))
    println(isMatch("aa", "*"))
    println(isMatch("cb", "?a"))
    println(isMatch("adceb", "*a*b"))
    println(isMatch("acdcb", "a*c?b"))

    println(isMatch("abefcdgiescdfimde", "ab*cd?i*de"))
*/    println(isMatch("mississippi", "m??*ss*?i*pi"))
    
  }

}

