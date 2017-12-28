package main

import java.io.File
import util.Properties
import scala.io.Source

abstract class Command{

  val root = Props.root
  def run()
}
case class List(path: String, options: String) extends Command{

  val result: StringBuilder = new StringBuilder()


  def run(): Unit = {
     val file: File = new File(root+"/"+path)
     if(file.exists && file.isDirectory){
       file.list.foreach( f => result.append(f+Properties.lineSeparator))
     }
     else{
       Props.getHandler.sendClientMsg("Directory doesn't exist or it is not a directory")
       //throw new IllegalArgumentException("File doesn't exist")
     }
  }

  override def toString(): String = {
    return result.toString
  }

}
case class Get(path: String) extends Command{

  val result: StringBuilder = new StringBuilder()

  def run(): Unit = {
    val file: File = new File(root+"/"+path)
    if(file.exists && file.isFile) {
      for (line <- Source.fromFile(root+"/"+path).getLines) {
        result.append(line+Properties.lineSeparator)
      }
    }
    else{
      Props.getHandler.sendClientMsg("File doesn't exist or it is not a regular file")
      //throw new IllegalArgumentException("File doesn't exist or it is not a regular file")
    }
  }

  override def toString(): String = {
    return result.toString
  }
}

/*
case class Result(data: ByteBuffer) extends Command{

  var result: String = ""

  def run(): Unit = {
    result="test result"
  }

  override def toString()= result
}*/
