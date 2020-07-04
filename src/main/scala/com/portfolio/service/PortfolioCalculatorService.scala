package com.portfolio.service

import com.portfolio.cov.CovCalculator
import com.portfolio.data_reader.DataReader
import com.portfolio.domain.{ CovData, IndexRawData, ReturnData, StockReturnData }
import com.portfolio.returns.{ PortfolioReturnCalculator, ReturnsCalculator }
import com.portfolio.variance.VarianceCalculator
import com.portfolio.vector.VectorCreator

class PortfolioCalculatorService(dataReader: DataReader,
                                 returnsCalculator: ReturnsCalculator,
                                 covCalculator: CovCalculator,
                                 varianceCalculator: VarianceCalculator,
                                 portfolioReturnCalculator: PortfolioReturnCalculator,
                                 desiredReturn: Int) {

  // TODO: Need to refactor this method
  def calculateMarketPortfolio(): Unit = {
    val indexesRawData: Seq[IndexRawData] = dataReader.readFiles()
    val stocksNames = indexesRawData.map(_.stockFileName)
    val indexesReturns: Seq[StockReturnData] = indexesRawData.map {
      indexRawData =>
        val indexReturns: Seq[ReturnData] = returnsCalculator.calculateReturns(indexRawData.stockData)
        StockReturnData(indexRawData.stockFileName, indexReturns)
    }


    val monthlyStocksEr: Map[String, Double] = indexesReturns
      .map(i =>
        i.stockFileName -> PortfolioCalculatorService.calcAverageReturn(i.stockData.map(_.r))).toMap

    val yearlyStocksEr: Map[String, Double] = indexesReturns
      .map(i =>
        i.stockFileName -> PortfolioCalculatorService.calcYearlyAverageReturn(i.stockData.map(_.r))).toMap

    val covData: Seq[CovData] =
      for {
        s1 <- indexesReturns
        s2 <- indexesReturns
      } yield {
        val s1Returns = s1.stockData.map(_.r)
        val s2Returns = s2.stockData.map(_.r)
        val s1Er = monthlyStocksEr(s1.stockFileName)
        val s2Er = monthlyStocksEr(s2.stockFileName)

        val cov = covCalculator.clacCov(s1Returns, s2Returns, s1Er, s2Er)
        CovData(s1.stockFileName, s2.stockFileName, cov)
      }

    println(s"covData = $covData")
    val vectors = VectorCreator.createVectors(stocksNames)
    val vectorsForDesiredEr = portfolioReturnCalculator.calcReturn(yearlyStocksEr, vectors)
      .filter(v => v.Er > ((desiredReturn - 2).toDouble / 100) && v.Er < (desiredReturn + 2).toDouble / 100)
    println(s"monthlyStocksEr = $monthlyStocksEr")
    println(s"yearlyStocksEr = $yearlyStocksEr")
    println(s"vectorsForDesiredEr = $vectorsForDesiredEr")
    //    varianceCalculator.calcVariance(vectorsForDesiredEr, covData)

    // find the min variance vector for a desired E(r).
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
