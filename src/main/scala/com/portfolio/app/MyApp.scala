package com.portfolio.app

import com.portfolio.cov.DefaultCovCalculator
import com.portfolio.data_reader.CsvDataReader
import com.portfolio.returns.{ DefaultPortfolioReturnCalculator, DefaultReturnsCalculator }
import com.portfolio.service.PortfolioCalculatorService
import com.portfolio.variance.DefaultVarianceCalculator

object MyApp extends App {
  val pathToData = "/Users/barakm/Projects/Portfolio-Calculator-Scala/src/main/resources/market-portfolio"
  val dataReader = new CsvDataReader(pathToData)
  val returnsCalculator = new DefaultReturnsCalculator()
  val covCalculator = new DefaultCovCalculator()
  val varianceCalculator = new DefaultVarianceCalculator()
  val portfolioReturnCalculator = new DefaultPortfolioReturnCalculator()

  new PortfolioCalculatorService(
    dataReader,
    returnsCalculator,
    covCalculator,
    varianceCalculator,
    portfolioReturnCalculator,
    10).calculateMarketPortfolio()
}
