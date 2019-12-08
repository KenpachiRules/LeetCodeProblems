
/**
 * Convert Strint to integer:
 * 1) Look for first occurrence of non-empty String , hence trim the leading and lagging white spaces.
 * 2) First non-empty string could be [+/-] sign.
 * 3) This is integer and hence not to worry about handling special characters.
 * 4)
 */
import scala.collection.mutable.Stack
import scala.util.control.Breaks._

object Solution {

  val INT_MAX: Int = 2147483647
  val INT_MIN: Int = -2147483648

  def myAtoi(str: String): Int = {
    //first trim the input for leading and lagging space
    val str1 = str.trim
    // check for if the string is non-empty , if empty return zero
    if (str1 == null || str1.isEmpty())
      return 0
    val chars = str1.toCharArray()
    var i = 0
    var numToCalc: Long = 0L
    var factorial: Long = 1L
    val numQueue: Stack[Int] = new Stack()

    breakable {
      while (i < chars.size) {
        val char1 = chars(i).toInt
        if ((char1 == 43 || char1 == 45) && numQueue.size == 0 && i == 0) {
          numQueue.push(char1)
        } else if (char1 >= 48 && char1 <= 57) {
          // if top of the stack is zero and there is more elements
          // it is no longer an integer , hence empty the queue
          val intVal = char1 - 48
          if (intVal == 0 && (numQueue.isEmpty || (numQueue.size == 1 && (numQueue.top == 43 || numQueue.top == 45)))) {
          } else {
            numQueue.push(intVal)
          }
        } else {
          break
        }
        i += 1
      }
    }
    // if the stack is empty it means it is either an invalid input or the input is 0
    if (numQueue.isEmpty)
      0
    else {
      var sign = 0
      if (numQueue.size > 12) // very big surely.
      {
        val signT = numQueue.apply(numQueue.size - 1)
        if (signT == 45)
          return INT_MIN
        else
          return INT_MAX
      }
      while (!numQueue.isEmpty) {
        val popVal = numQueue.pop
        if (popVal == 43 || popVal == 45) {
          sign = popVal
        } else {
          numToCalc = numToCalc + (popVal * factorial)
          factorial = factorial * 10
          if (numToCalc > INT_MAX) {
            // Then it no longer makes sense to proceed further hence return
            // To find the sign pop all elements until the last and try to see if it is
            // 43 or 45 or if something else.
            var sign = 0
            while (!numQueue.isEmpty) {
              sign = numQueue.pop()
            }
            numToCalc = if (sign == 45) INT_MIN else INT_MAX
          }
        }
      }
      if (sign == 43 || sign == 0)
        return numToCalc.toInt
      else return -numToCalc.toInt
    }
    0
  }

}