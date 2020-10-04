package com.portfolio.app

import com.portfolio.domain.VectorStdev
import com.portfolio.mapper.ObjectMapperFactory
import com.portfolio.service.{FetchDataService, PortfolioCalculatorService}
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

class Handler extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("application/json")
    resp.setStatus(HttpServletResponse.SC_OK)
    resp.getWriter.println(s"""{"msg": "Hello World!"}""")
  }
}


class CalculateEfficientFrontierHandler(fetchDataService: FetchDataService, portfolioCalculatorService: PortfolioCalculatorService) extends HttpServlet {
  val mapper = ObjectMapperFactory.createObjectMapper()

  override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("application/json")
    resp.setStatus(HttpServletResponse.SC_OK)
    fetchDataService.downloadStockData()
    val minimumPortfolios = portfolioCalculatorService.calculateEfficientFrontier("efficient-frontier")
    val res = CalculateEfficientFrontierResponse(minimumPortfolios.values.toSeq)
    resp.getWriter.write(mapper.writeValueAsString(res))
  }
}

case class CalculateEfficientFrontierResponse(vectors: Seq[VectorStdev])
