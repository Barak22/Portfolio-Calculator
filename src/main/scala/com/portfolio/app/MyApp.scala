package com.portfolio.app

import com.portfolio.analysis.PortfoliosAnalyzer
import com.portfolio.cov.DefaultCovCalculator
import com.portfolio.domain.Path
import com.portfolio.http.YahooFinanceHttpClient
import com.portfolio.io.{ CsvDataWriter, DefaultPortfoliosReader, YahooFinanceCsvDataReader }
import com.portfolio.measure.DurationMeasurer
import com.portfolio.returns.{ DefaultPortfolioReturnCalculator, DefaultReturnsCalculator }
import com.portfolio.service.{ FetchDataService, PortfolioCalculatorService }
import com.portfolio.variance.DefaultVarianceCalculator

object MyApp extends App {
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
  val portfoliosAnalyzer = new PortfoliosAnalyzer(portfoliosReader, dataWriter)
  val dataProvider = new YahooFinanceHttpClient

  val portfolioCalculatorService = new PortfolioCalculatorService(
    dataReader,
    dataWriter,
    returnsCalculator,
    covCalculator,
    varianceCalculator,
    portfolioReturnCalculator,
    desiredReturn = 10,
    measurer = measurer,
    portfoliosAnalyzer)

  val fetchDataService = new FetchDataService(dataProvider, dataWriter)

  //    measurer.measure("calculateMarketPortfolio", portfolioCalculatorService.calculateAllPortfolios(allPortfoliosFileName))
  measurer.measure("calculateEfficientFrontier", portfolioCalculatorService.calculateEfficientFrontier(efficientFrontierFileName))
  //    fetchDataService.downloadStockData()
}
