package com.portfolio.app

import com.portfolio.data_reader.CsvDataReader
import com.portfolio.returns.DefaultReturnsCalculator
import com.portfolio.service.PortfolioCalculatorService

object MyApp extends App {
  val pathToData = "/Users/barakm/Projects/Portfolio-Calculator-Scala/src/main/resources/market-portfolio"
  val dataReader = new CsvDataReader(pathToData)
  val returnsCalculator = new DefaultReturnsCalculator()
  new PortfolioCalculatorService(dataReader, returnsCalculator).calculateMarketPortfolio()
}