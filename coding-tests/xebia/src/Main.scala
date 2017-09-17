

import scala.io.Source

/**
  * Created by brahim on 9/17/17.
  */
object Main {

  def main(args: Array[String]): Unit ={

  // read input
  val path: String = "./resources/input"
  try{
    val file = Source.fromFile(path)
  catch(e: Exp)
  var xMax, yMax = 0
  var pos: Position = null
  var line: String = null


  val lines: Iterator[String] = file.getLines()
  line = lines.next
  xMax = line.split(" ")(0).toInt
  yMax = line.split(" ")(1).toInt

  for(line <- lines) {
    pos = new Position(line.split(" ")(0).toInt, line.split(" ")(1).toInt, orientationEnum.parse(line.split(" ")(2).charAt(0)))
    for(step <- lines.next().toCharArray){
       pos.move(moveEnum.parse(step))
    }
    println(pos)
  }

  }

}

