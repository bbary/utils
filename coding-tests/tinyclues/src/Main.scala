/**
  * Created by brahim on 9/12/17.
  */
object Main extends Finder with Counter{

  def main(args: Array[String]): Unit ={
    // Find missing elements
    val entry: Array[Long] = Array(9,9,1)
    val result: Array[Long] = findMissing(entry)

    // Find distinct count

    // Part #1
    val entry1: Array[Long] = Array(9,9,1,1,1,77,77,1, 2, 2)
    //val entry1: Array[Long] = Array()
    //val entry1: Array[Long] = null
    val result1: Long = countDistinct(entry1)
    println(result1)

    // Part #2
    /* Response:
    if we use the same function, it will work but the performance will not be very good, use rather dstinct function
    arr.distinct.length   because it uses a HashSet and is  O(n)
     */


  }

  override def findMissing(arr: Array[Long]): Array[Long] = {
    if(arr != null && !arr.isEmpty) {
      scala.util.Sorting.quickSort(arr)
      var result: Array[Long] = new Array[Long](0)
      var lastLong = arr(0)
      arr.foreach { l =>
        if (lastLong + 1 < l) {
          for (i <- lastLong + 1 to l - 1) {
            result :+= i
          }
        }
        lastLong = l
      }
      if(result.isEmpty){
        println("info: no missing numbers")
        sys.exit
      }

      return result
    }
    else{
      println("error: entry array can't be empty or null")
      sys.exit
    }
  }

  override def countDistinct(arr: Array[Long]): Long = {
    if(arr != null && !arr.isEmpty) {

      // method 1
      scala.util.Sorting.quickSort(arr)
      var result: Long = 1
      var lastLong = arr(0)
      arr.foreach { l =>
        if ( l != lastLong ) {
          result += 1
          lastLong = l
        }
      }
      return result

      //method 2
      /*
      arr.distinct.length
      */

    }
    else{
      println("error: entry array can't be empty or null")
      sys.exit
    }
  }
}
