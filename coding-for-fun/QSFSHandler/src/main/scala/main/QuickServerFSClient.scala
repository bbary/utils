package main

import java.io._
import java.net.Socket

object QuickServerFSClient {
  def main(args: Array[String]): Unit = {
    var br:BufferedReader = null
    var out:PrintWriter = null
    var socket:Socket = null
    var port: Int = 8030
    if (args.length < 2) {
      println("Usage : " + "\n host  port " +args(0))
      sys.exit(1)
    }
    try {
      port = args(1).toInt
      print("Connecting.. ")
      socket = new Socket(args(0), port)
      println("Connected to " + args(0) + ":" + port + "\n")
      br = new BufferedReader(new InputStreamReader(socket.getInputStream))
      out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream)), true)
      var recData = br.readLine
      println("S: " + recData)
      out.println("test")
      println("C: test")
      recData = br.readLine
      println("S: " + recData)
      out.println("quit")
      println("C: quit")
      recData = br.readLine
      println("S: " + recData)
    } catch {
      case e: Exception =>
        println("Error " + e)
    } finally try
        if (socket != null) socket.close()
    catch {
      case er: Exception =>
        println("Error closing: " + er)
    }
  }
}
