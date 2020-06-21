package com.portfolio.app

import com.portfolio.cov.DefaultCovCalculator
import com.portfolio.data_reader.CsvDataReader
import com.portfolio.returns.DefaultReturnsCalculator
import com.portfolio.service.PortfolioCalculatorService
import com.portfolio.variance.DefaultVarianceCalculator

object MyApp extends App {
  val pathToData = "/Users/barakm/Projects/Portfolio-Calculator-Scala/src/main/resources/market-portfolio"
  val dataReader = new CsvDataReader(pathToData)
  val returnsCalculator = new DefaultReturnsCalculator()
  val covCalculator = new DefaultCovCalculator()
  val varianceCalculator = new DefaultVarianceCalculator()

  new PortfolioCalculatorService(
    dataReader,
    returnsCalculator,
    covCalculator,
    varianceCalculator,
    10).calculateMarketPortfolio()
}
