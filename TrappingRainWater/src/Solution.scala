
object Solution {

  def trap(height: Array[Int]): Int = {
    if (height == null || height.isEmpty)
      0
    var tempTower: Tower = null
    var firstTower: Tower = null
    var nonZeroTower = 0
    var qtyWater = 0
    for (i <- 0 until height.length) {
      if (height(i) > 0) {
        if (firstTower == null) {
          firstTower = makeTower(i)
          firstTower.height = height(i)
          tempTower = firstTower
        } else {
          val newTower = makeTower(i)
          newTower.leftTower = tempTower
          newTower.height = height(i)
          tempTower.rightTower = newTower
          tempTower = newTower
        }
        nonZeroTower += 1
      }
    }
    // perform the computation as long as there are minimum two towers
    while (nonZeroTower > 1) {
      var secTempTower = firstTower
      while (secTempTower != null) {
        if (secTempTower.height > 0 && secTempTower.rightTower != null && secTempTower.rightTower.height > 0) {
          val tempQty = secTempTower.rightTower.pos - secTempTower.pos - 1
          if (tempQty > 0)
            qtyWater += tempQty
        }
        secTempTower = secTempTower.rightTower
      }
      // re-arrange the towers by reducing the height of each tower by 1
      secTempTower = firstTower
      while (secTempTower != null) {
        secTempTower.height -= 1
        if (secTempTower.height == 0) {
          nonZeroTower -= 1
          if (secTempTower.leftTower == null) {
            // first tower , it is going down hence assign a new tower
            firstTower = secTempTower.rightTower
          } else if (secTempTower.leftTower != null && secTempTower.rightTower != null) {
            secTempTower.leftTower.rightTower = secTempTower.rightTower
            secTempTower.rightTower.leftTower = secTempTower.leftTower
          } else if (secTempTower.rightTower == null) {
            secTempTower.leftTower.rightTower = null
          }
        }
        secTempTower = secTempTower.rightTower
      }
    }
    qtyWater
  }

  def makeTower(pos: Int): Tower = Tower(pos)
}

case class Tower(pos: Int) {
  var rightTower: Tower = null
  var leftTower: Tower = null
  var height: Int = 0
}

