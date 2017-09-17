

import java.io.FileNotFoundException

import scala.io.{BufferedSource, Source}

/**
  * Created by brahim on 9/17/17.
  */
object Main {

  def main(args: Array[String]): Unit ={

  // read input
  val path: String = "./resources/input"
  var file: BufferedSource = null
  try
      file = Source.fromFile(path)
  catch {
      case ioe: FileNotFoundException =>
        println("File not found. Check the given Path")
        ioe.printStackTrace()
  }

  var xMax, yMax = 0
  var pos: Position = null
  var line: String = null


  val lines: Iterator[String] = file.getLines().filter( line => !line.isEmpty) // avoid empty lines
  line = lines.next
  if(line.split(" ").length != 2){
    println(s"incorrect line entry '$line', expecting two Ints")
    sys.exit(1)
  }
  xMax = line.split(" ")(0).toInt
  yMax = line.split(" ")(1).toInt

  for(line <- lines) {
    if ( line.split(" ")(0).toInt > xMax || line.split(" ")(1).toInt > yMax){
      println(s"found incorrect coordinates because they are outside of the rectangle '(${line.split(" ")(0).toInt}, ${line.split(" ")(1).toInt})'")
      sys.exit(1)
    }
    if(line.split(" ").length != 3){
      println(s"incorrect line entry '$line', expecting two Ints and a char")
      sys.exit(1)
    }
    pos = new Position(line.split(" ")(0).toInt, line.split(" ")(1).toInt, orientationEnum.parse(line.split(" ")(2).charAt(0)))

    val steps = lines.next()
    if(steps.split(" ").length != 1){
      println(s"incorrect line entry '$steps', expecting a list of characters with no spaces")
      sys.exit(1)
    }
    for(step <- steps.toCharArray){
       pos.move(moveEnum.parse(step), xMax, yMax)
    }
    println(pos)
  }

  }

}

