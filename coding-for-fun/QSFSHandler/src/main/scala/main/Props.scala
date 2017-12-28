package main

import org.quickserver.net.server.ClientHandler

object Props {

  var root="./testing_dir"
  var handler: ClientHandler = null

  def getRootDir = root
  def setRootDir(path: String): Unit ={
    root=path
  }

  def getHandler = handler
  def setHandler(h: ClientHandler): Unit ={
    handler = h
  }
}
