
import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.collection.convert.decorateAsJava
import scala.collection.mutable.ArrayBuffer
import java.util.ArrayList

object Solution {


  def findSubstring(s: String, words: Array[String]): List[Int] = {
    // construct a map.
    // create start index.
    if(words.isEmpty)
     return List.empty
    var wordList: java.util.List[String] = new ArrayList()
    words.foreach(w => wordList.add(w))
    val wordLen = wordList.get(0).length
    if(wordLen > s.length)
      List.empty
    var i = 0
    var j = i
    var idx = -1
    var continue = true
    val wordMap: Map[Int, Map[Int, Int]] = Map()
    var potentialList: ListBuffer[Int] = ListBuffer.empty
    while (canProgress(i, s.length, wordList.size(), wordLen)) {
      if (!(j + wordLen > s.length))
        idx = matchIndex(s.substring(j, j + wordLen), wordList)
      if (idx >= 0) {
        wordList.remove(idx)
        if (!wordMap.contains(i)) {
          wordMap.put(i, Map[Int, Int]())
        }
        var subMap = wordMap.get(i).++(Map(j -> idx))
        j = j + wordLen
        idx = -1
      } else {
        // i as start offset does not satisfy the req
        if (wordList.isEmpty())
          potentialList += i
        i = i + 1
        j = i
        wordList = new ArrayList()
        words.foreach(w => wordList.add(w))
      }
    }
    potentialList.toList
  }

  def canProgress(startPos: Int, len: Int, noOfWords: Int, wordLength: Int): Boolean = {
    val possibleCombs = (len - startPos + 1) / wordLength
    possibleCombs >= noOfWords
  }

  def matchIndex(pattern: String, words: java.util.List[String]): Int = {
    words.indexOf(pattern)
  }

}