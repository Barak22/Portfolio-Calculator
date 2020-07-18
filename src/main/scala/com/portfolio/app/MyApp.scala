package com.portfolio.app

import com.portfolio.cov.DefaultCovCalculator
import com.portfolio.io.{ CsvDataReader, TxtDataWriter }
import com.portfolio.measure.DurationMeasurer
import com.portfolio.returns.{ DefaultPortfolioReturnCalculator, DefaultReturnsCalculator }
import com.portfolio.service.PortfolioCalculatorService
import com.portfolio.variance.DefaultVarianceCalculator

object MyApp extends App {
  val pathToData = "./src/main/resources/market-portfolio"
  val measurer = new DurationMeasurer()
  val dataReader = new CsvDataReader(pathToData)
  val dataWriter = new TxtDataWriter()
  val returnsCalculator = new DefaultReturnsCalculator()
  val covCalculator = new DefaultCovCalculator()
  val varianceCalculator = new DefaultVarianceCalculator(measurer)
  val portfolioReturnCalculator = new DefaultPortfolioReturnCalculator()

  measurer.measure("calculateMarketPortfolio", new PortfolioCalculatorService(
    dataReader,
    dataWriter,
    returnsCalculator,
    covCalculator,
    varianceCalculator,
    portfolioReturnCalculator,
    desiredReturn = 10,
    measurer = measurer).calculateMarketPortfolio())
}
