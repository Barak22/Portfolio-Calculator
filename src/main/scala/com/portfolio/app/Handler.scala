package com.portfolio.app

import javax.servlet.http.{ HttpServlet, HttpServletRequest, HttpServletResponse }

class Handler extends HttpServlet {

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html")
    resp.setStatus(HttpServletResponse.SC_OK)
    resp.getWriter.println("Hello World!")
  }
}
