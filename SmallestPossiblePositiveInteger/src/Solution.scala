object Solution {
  def firstMissingPositive(nums: Array[Int]): Int = {
    // iterate throught array to find the smallest and the largest integer
    // and define array range.
    if( nums !=null && !nums.isEmpty ) {
    var smallest = nums(0)
    var largest = nums(0)
    val posArray = new Array[Int](nums.size)
    for (i <- 0 until nums.size) {
      if (nums(i) > 0) {
        if (nums(i) < smallest)
          smallest = nums(i)
        if (nums(i) > largest)
          largest = nums(i)
          posArray(i) = nums(i)
      }
    }
    // if the smallest is not 1 then it is 1 or need to traverse
    if (smallest > 1)
      return 1
    val newArray = new Array[Int](largest + 1)
    for (i <- 0 until posArray.size) {
      newArray(posArray(i)) = 1
    }
    for (i <- 1 until newArray.size) {
      if (newArray(i) == 0)
        return i
    }
    return largest + 1
  }
    1
  }
  

}