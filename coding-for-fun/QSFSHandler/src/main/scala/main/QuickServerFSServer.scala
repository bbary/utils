package main

import java.io.IOException
import java.net.SocketTimeoutException

import org.quickserver.net.server.{ClientCommandHandler, ClientHandler}


class QuickServerFSServer extends ClientCommandHandler {

  @throws(classOf[IOException])
  @throws(classOf[SocketTimeoutException])
  def gotConnected(handler: ClientHandler){
    handler.sendSystemMsg("Connection opened : "+
      handler.getSocket().getInetAddress())
    handler.sendClientMsg("Welcome to bba's home")
  }

  @throws(classOf[IOException])
  def lostConnection(handler:ClientHandler){
    handler.sendSystemMsg("Connection lost : " +
      handler.getSocket().getInetAddress())
  }

  @throws(classOf[IOException])
  def closingConnection(handler:ClientHandler ){
    handler.sendSystemMsg("Connection closed : " +
      handler.getSocket().getInetAddress())
  }

  @throws(classOf[IOException])
  @throws(classOf[SocketTimeoutException])
  def handleCommand(handler: ClientHandler, command: String){

    Props.setHandler(handler)

    if(command.toLowerCase().equals("help")) {
      handler.sendClientMsg("Usage:")

      handler.sendClientMsg("\tset rootdir <path_to_root_dir>: set root dir of the remote file system")
      handler.sendClientMsg("\tget rootdir : get parametrized root dir of the remote file system")
      handler.sendClientMsg("\tmkdir <dir>: create directory")
      handler.sendClientMsg("\tls <dir> | <file>: list directory contents")
      handler.sendClientMsg("\tcat <path_to_file>: display file content")

    } else if(command.toLowerCase().equals("quit")) {
        handler.sendClientMsg("Good Bye")
        handler.closeConnection()
    } else if(command.toLowerCase().equals("test")) {
        handler.sendClientMsg("test is OK")

    } else if(command.toLowerCase().startsWith("set rootdir")) {
      if (command.split(" ").size > 2) {
        Props.setRootDir(command.split(" ")(2))
        handler.sendSystemMsg("rootDir is updated to "+command.split(" ")(2))
        handler.sendClientMsg("rootDir is updated to "+command.split(" ")(2))
      } else {
        handler.sendClientMsg("Error : usage  set rootdir <path_to_root_dir>")
      }
    } else if(command.toLowerCase().equals("get rootdir")) {
        handler.sendClientMsg("rootDir is "+Props.getRootDir)
    } else if(command.toLowerCase().startsWith("cat")) {
        var path: String = ""
        if (command.split(" ").size > 1) {
          path = command.split(" ")(1)
          val get: Get = new Get(path)
          get.run
          handler.sendClientMsg(get.toString)
        } else {
          handler.sendClientMsg("Error : usage   cat <path_to_file>")
        }
    } else if(command.toLowerCase().startsWith("mkdir")) {
      var path: String = ""
      if (command.split(" ").size > 1) {
        handler.sendClientMsg("not implemented")
      } else {
        handler.sendClientMsg("Error : usage mkdir <dir>")
      }
    } else if(command.toLowerCase().startsWith("ls")) {
        var path: String = ""
        var options: String = ""
        if (command.split(" ").size == 1) {
          val list: List = new List("", null)
          list.run
          handler.sendClientMsg(list.toString)
        } else if (command.split(" ").size <= 2) {
          path = command.split(" ")(1)
          val list: List = new List(path, null)
          list.run
          handler.sendClientMsg(list.toString)
        } else if (command.split(" ").size > 2){
          path = command.split(" ")(1)
          options = command.split(" ")(2)
          val list: List = new List(path, options)
          list.run
          handler.sendClientMsg(list.toString)
        } else {
          handler.sendClientMsg("Error : usage   ls <file>|<dir>")
        }
    } else{
      handler.sendClientMsg("Error : this command is not recognized. type help for help")
    }

  }


}