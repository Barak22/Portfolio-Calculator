package com.portfolio.app

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler

object MyServer extends App {

  val port = System.getProperty("port", "3000").toInt
  val server = new Server(port)

  val servlet = new ServletHandler
  server.setHandler(servlet)

  servlet.addServletWithMapping(classOf[Handler], "/hello")

  server.join()
  server.start()
}
