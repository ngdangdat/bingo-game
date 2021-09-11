package helpers

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object GameHelper {
  def generateRoll(): List[Int] = {
    val ts: Long = System.currentTimeMillis()
    Random.setSeed(ts)
    Random.shuffle((1 to 90).toList)
  }

  def generateSheet(): List[Int] = {
    val ts: Long = System.currentTimeMillis()
    Random.setSeed(ts)
    val nums: List[Int] = Random.shuffle((1 to 90).toList)
    var numIndex: Int = 0
    val bingoSheet: ArrayBuffer[List[Int]] = ArrayBuffer[List[Int]]()
    for (i <- 1 to 18) {
      Random.setSeed(ts + i)
      val l: List[Int] = Random.shuffle((0 to 8).toList).take(5)
      bingoSheet += (0 to 8).toList.zipWithIndex.map(e => {
        if (l.contains(e._2)) {
          val n: Int = nums(numIndex)
          numIndex += 1
          n
        } else -1
      })
    }
    bingoSheet.flatten.toList
  }
}
