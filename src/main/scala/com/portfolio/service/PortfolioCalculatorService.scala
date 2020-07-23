package com.portfolio.service

import com.portfolio.cov.CovCalculator
import com.portfolio.domain._
import com.portfolio.helper.{ CalculatorHelper, Utils }
import com.portfolio.io.{ DataReader, DataWriter }
import com.portfolio.measure.DurationMeasurer
import com.portfolio.returns.{ PortfolioReturnCalculator, ReturnsCalculator }
import com.portfolio.stdev.STDEVCalculator
import com.portfolio.variance.VarianceCalculator
import com.portfolio.vector.VectorCreator

class PortfolioCalculatorService(dataReader: DataReader,
                                 dataWriter: DataWriter,
                                 returnsCalculator: ReturnsCalculator,
                                 covCalculator: CovCalculator,
                                 varianceCalculator: VarianceCalculator,
                                 portfolioReturnCalculator: PortfolioReturnCalculator,
                                 desiredReturn: Int,
                                 measurer: DurationMeasurer) {

  def calculateMarketPortfolio(): Unit = {
    val indexesRawData: Seq[IndexRawData] = dataReader.readFiles()
    val stocksNames = indexesRawData.map(_.stockFileName)
    val indexesReturns: Seq[StockReturnData] = calculateStockReturns(indexesRawData)

    val monthlyStocksEr: Map[String, Double] = calculateTheMonthlyAverageReturnPerStock(indexesReturns)
    val yearlyStocksEr: Map[String, Double] = calculateTheAnnualAverageReturnPerStock(indexesReturns)

    val covData: Map[String, CovData] = calculateStocksCovariance(indexesReturns, monthlyStocksEr)

    val vectors = measurer.measure("VectorCreator.createVectors", VectorCreator.createVectors(stocksNames, 5))
    val vectorsForDesiredEr = measurer.measure("filterVectorsWhichComplyDesiredReturn", filterVectorsWhichComplyDesiredReturn(yearlyStocksEr, vectors))
    val vectorsWithVariance = measurer.measure("varianceCalculator.calcVariance", varianceCalculator.calcVariance(vectorsForDesiredEr, covData))
    val vectorsWithStandardDeviation = measurer.measure("STDEVCalculator.calculateStdev", STDEVCalculator.calculateStdev(vectorsWithVariance))
    val vectorsWithRoundedNumbers = measurer.measure("CalculatorHelper.roundNumbers", CalculatorHelper.roundNumbers(vectorsWithStandardDeviation))

    measurer.measure("dataWriter.writeVectors", dataWriter.writeVectors("results-with-5-indexes.csv", vectorsWithRoundedNumbers))
  }


  private def filterVectorsWhichComplyDesiredReturn(yearlyStocksEr: Map[String, Double], vectors: Iterator[VectorWeights]) = {
    portfolioReturnCalculator.calcReturn(yearlyStocksEr, vectors)
      .filter(v => v.Er > ((desiredReturn - 2).toDouble / 100) && v.Er < (desiredReturn + 2).toDouble / 100)
  }

  private def calculateStocksCovariance(indexesReturns: Seq[StockReturnData], monthlyStocksEr: Map[String, Double]) = {
    (for {
      s1 <- indexesReturns
      s2 <- indexesReturns
    } yield {
      val s1Returns = s1.stockData.map(_.r)
      val s2Returns = s2.stockData.map(_.r)
      val s1Er = monthlyStocksEr(s1.stockFileName)
      val s2Er = monthlyStocksEr(s2.stockFileName)

      val cov = covCalculator.clacCov(s1Returns, s2Returns, s1Er, s2Er)
      Utils.makeKeyFromTwoStocksNames(s1.stockFileName, s2.stockFileName) -> CovData(s1.stockFileName, s2.stockFileName, cov)
    }).toMap
  }

  private def calculateTheAnnualAverageReturnPerStock(indexesReturns: Seq[StockReturnData]) = {
    indexesReturns
      .map(i => i.stockFileName -> PortfolioCalculatorService.calcYearlyAverageReturn(i.stockData.map(_.r)))
      .toMap
  }

  private def calculateTheMonthlyAverageReturnPerStock(indexesReturns: Seq[StockReturnData]) = {
    indexesReturns
      .map(i =>
        i.stockFileName -> PortfolioCalculatorService.calcAverageReturn(i.stockData.map(_.r))).toMap
  }

  private def calculateStockReturns(indexesRawData: Seq[IndexRawData]) = {
    indexesRawData.map {
      indexRawData =>
        val indexReturns: Seq[ReturnData] = returnsCalculator.calculateReturns(indexRawData.stockData)
        StockReturnData(indexRawData.stockFileName, indexReturns)
    }
  }
}

object PortfolioCalculatorService {
  def calcAverageReturn(returns: Seq[Double]): Double = {
    returns.sum / returns.size
  }

  def calcYearlyAverageReturn(returns: Seq[Double]): Double = {
    Math.pow(1 + returns.sum / returns.size, 12) - 1
  }

}
