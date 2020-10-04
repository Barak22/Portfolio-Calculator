package com.portfolio.app

import javax.servlet.http.{ HttpServlet, HttpServletRequest, HttpServletResponse }

//class Handler extends HttpServlet {

//  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
//    resp.setContentType("application/json")
//    resp.setStatus(HttpServletResponse.SC_OK)
//    resp.getWriter.println(s"""{"msg": "Hello World!"}""")
//  }
//}


class DownloadStocksHandlerHandler extends HttpServlet {

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("text/html")
    resp.setStatus(HttpServletResponse.SC_OK)
    resp.getWriter.println("Hello World!")
  }
}
