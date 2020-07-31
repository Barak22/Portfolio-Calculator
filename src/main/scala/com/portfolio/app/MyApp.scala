package com.portfolio.app

import com.portfolio.analysis.PortfoliosAnalyzer
import com.portfolio.cov.DefaultCovCalculator
import com.portfolio.domain.Path
import com.portfolio.io.{ CsvDataWriter, DefaultPortfoliosReader, YahooFinanceCsvDataReader }
import com.portfolio.measure.DurationMeasurer
import com.portfolio.returns.{ DefaultPortfolioReturnCalculator, DefaultReturnsCalculator }
import com.portfolio.service.PortfolioCalculatorService
import com.portfolio.variance.DefaultVarianceCalculator

object MyApp extends App {
  val measurer = new DurationMeasurer()
  val dataReader = new YahooFinanceCsvDataReader(Path.yahooFinanceDirPath)
  val dataWriter = new CsvDataWriter(Path.productionResultsDirPath)
  val returnsCalculator = new DefaultReturnsCalculator()
  val covCalculator = new DefaultCovCalculator()
  val varianceCalculator = new DefaultVarianceCalculator(measurer)
  val portfolioReturnCalculator = new DefaultPortfolioReturnCalculator()
  val portfoliosReader = new DefaultPortfoliosReader(Path.productionResultsDirPath)
  val portfoliosAnalyzer = new PortfoliosAnalyzer(portfoliosReader, dataWriter)

  val service = new PortfolioCalculatorService(
    dataReader,
    dataWriter,
    returnsCalculator,
    covCalculator,
    varianceCalculator,
    portfolioReturnCalculator,
    desiredReturn = 10,
    measurer = measurer,
    portfoliosAnalyzer)

  measurer.measure("calculateMarketPortfolio", service.calculateMarketPortfolio())
  //  measurer.measure("calculateEfficientFrontier", service.calculateEfficientFrontier())
}
