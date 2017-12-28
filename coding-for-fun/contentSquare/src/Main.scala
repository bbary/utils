import java.io.IOException
import java.util.TreeSet

import scala.collection.JavaConverters._
import scala.io.{BufferedSource, Source}

/**
  * Created by brahim on 10/1/17.
  */
object Main {
  def main(args: Array[String]): Unit = {

    // reading keywords dictionary from a file
    val input_path = "input/keywords"
    var keywords_bs: BufferedSource = null
    try
    {
      keywords_bs = Source.fromFile(input_path)
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        sys.exit(1)
    }

    val keywords : Iterator[String] = keywords_bs.getLines().map(line => line.toLowerCase)
    val keyword_tree = new TreeSet[String](keywords.toList.asJava)


    val suggestions: Array[String] = auto_complete("p", keyword_tree)
    if(suggestions == null){
      println("no match")
      sys.exit(0)
    }
    suggestions.foreach(println)
  }

  /**
    * returns 4 first alphabetic orderd suggestions form the given TreeSet for the given word
    * @param word
    * @param keyword
    * @return
    */
  def auto_complete(word: String, keyword: TreeSet[String]): Array[String] ={
      keyword.subSet(word, word+"z").toArray.take(4).map(word => word.toString)
  }

}
