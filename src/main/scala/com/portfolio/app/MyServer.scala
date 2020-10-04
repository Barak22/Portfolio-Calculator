package com.portfolio.app

import com.portfolio.analysis.PortfoliosAnalyzer
import com.portfolio.cov.DefaultCovCalculator
import com.portfolio.domain.Path
import com.portfolio.http.YahooFinanceHttpClient
import com.portfolio.io.{CsvDataWriter, DefaultPortfoliosReader, YahooFinanceCsvDataReader}
import com.portfolio.measure.DurationMeasurer
import com.portfolio.returns.{DefaultPortfolioReturnCalculator, DefaultReturnsCalculator}
import com.portfolio.service.{FetchDataService, PortfolioCalculatorService}
import com.portfolio.variance.DefaultVarianceCalculator
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{ServletHandler, ServletHolder}

object MyServer extends App {

  val port = System.getProperty("port", "3000").toInt
  val server = new Server(port)
  val config = new Config

  val servlet = new ServletHandler
  server.setHandler(servlet)

  servlet.addServletWithMapping(classOf[Handler], "/hello")

  val calculateEfficientFrontierHandler = new CalculateEfficientFrontierHandler(config.fetchDataService, config.portfolioCalculatorService)
  val holder = new ServletHolder(calculateEfficientFrontierHandler)
  servlet.addServletWithMapping(holder, "/calculate-efficient-frontier")

  server.join()
  server.start()
}

class Config {
  val allPortfoliosFileName = "all-portfolios"
  val efficientFrontierFileName = "efficient-frontier"

  val measurer = new DurationMeasurer()
  val dataReader = new YahooFinanceCsvDataReader(Path.yahooFinanceDirPath)
  val dataWriter = new CsvDataWriter(Path.yahooFinanceDirPath, Path.productionResultsDirPath)
  val returnsCalculator = new DefaultReturnsCalculator()
  val covCalculator = new DefaultCovCalculator()
  val varianceCalculator = new DefaultVarianceCalculator(measurer)
  val portfolioReturnCalculator = new DefaultPortfolioReturnCalculator()
  val portfoliosReader = new DefaultPortfoliosReader(Path.productionResultsDirPath)
  val portfoliosAnalyzer = new PortfoliosAnalyzer()
  val dataProvider = new YahooFinanceHttpClient

  val portfolioCalculatorService = new PortfolioCalculatorService(
    dataReader,
    dataWriter,
    returnsCalculator,
    covCalculator,
    varianceCalculator,
    portfolioReturnCalculator,
    measurer = measurer,
    portfoliosAnalyzer)

  val fetchDataService = new FetchDataService(dataProvider, dataWriter)
}
