
object Solution {

  def jump(nums: Array[Int]): Int = {
    val jumpI = new JumpIndex
    if (nums.length == 1)
      0
    recurseJump(nums, 0, jumpI, new scala.collection.mutable.Stack[Int]())
    jumpI.jumps
  }

  def main(args: Array[String]): Unit = {
    println(jump(Array[Int](8,2,4,4,4,9,5,2,5,8,8,0,8,6,9,1,1,6,3,5,1,2,6,6,0,4,8,6,0,3,2,8,7,6,5,1,7,0,3,4,8,3,5,9,0,4,0,1,0,5,9,2,0,7,0,2,1,0,8,2,5,1,2,3,9,7,4,7,0,0,1,8,5,6,7,5,1,9,9,3,5,0,7,5)))
  }

  def recurseJump(nums: Array[Int], index: Int, lowestJumpIndex: JumpIndex, jumpStack: scala.collection.mutable.Stack[Int]): Unit = {
    val distanceToBeCovered = nums.size - 1 - index
    if (distanceToBeCovered == 0)
      return
    if (nums(index) < distanceToBeCovered ) {
      if(lowestJumpIndex.jumps != 0 && jumpStack.size > lowestJumpIndex.jumps)
        return
      for (i <- 1 to nums(index) ) {
        val tempStack = new scala.collection.mutable.Stack[Int]()
        tempStack.pushAll(jumpStack)
        tempStack.push(i)
        recurseJump(nums,index + i, lowestJumpIndex, tempStack)
      }

    } else {
      var currentJumpIndex = 1
      while (!jumpStack.isEmpty) {
        jumpStack.pop()
        currentJumpIndex += 1
      }
      if (lowestJumpIndex.jumps == 0 || currentJumpIndex <= lowestJumpIndex.jumps)
        lowestJumpIndex.jumps = currentJumpIndex
      return
    }
  }

}

class JumpIndex {
  var jumps: Int = 0
}