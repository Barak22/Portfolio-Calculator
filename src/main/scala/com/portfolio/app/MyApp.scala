package com.portfolio.app

import com.portfolio.cov.DefaultCovCalculator
import com.portfolio.domain.Path
import com.portfolio.io.{ CsvDataWriter, YahooFinanceCsvDataReader }
import com.portfolio.measure.DurationMeasurer
import com.portfolio.returns.{ DefaultPortfolioReturnCalculator, DefaultReturnsCalculator }
import com.portfolio.service.PortfolioCalculatorService
import com.portfolio.variance.DefaultVarianceCalculator

object MyApp extends App {
  val measurer = new DurationMeasurer()
  val dataReader = new YahooFinanceCsvDataReader(Path.yahooFinanceFilesPath)
  val dataWriter = new CsvDataWriter(Path.productionResultsFilesPath)
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
