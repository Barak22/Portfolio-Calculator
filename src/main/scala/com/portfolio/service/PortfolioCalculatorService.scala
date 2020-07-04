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
                                 desiredReturn: 10) {

  // TODO: Need to refactor this method
  def calculateMarketPortfolio() = {
    val indexesRawData: Seq[IndexRawData] = dataReader.readFiles()
    val stocksNames = indexesRawData.map(_.stockFileName)
    val indexesReturns: Seq[StockReturnData] = indexesRawData.map {
      indexRawData =>
//        println(s"indexesRawData = ${indexesRawData}")
        val indexReturns: Seq[ReturnData] = returnsCalculator.calculateReturns(indexRawData.stockData)
        StockReturnData(indexRawData.stockFileName, indexReturns)
    }

    // The E(r) is per month!!
    val stocksEr: Map[String, Double] = indexesReturns
      .map(i =>
        i.stockFileName -> PortfolioCalculatorService.calcAverageReturn(i.stockData.map(_.r))).toMap

    val covData: Seq[CovData] =
      for {
        s1 <- indexesReturns
        s2 <- indexesReturns
      } yield {
        val s1Returns = s1.stockData.map(_.r)
        val s2Returns = s2.stockData.map(_.r)
        val s1Er = stocksEr(s1.stockFileName)
        val s2Er = stocksEr(s2.stockFileName)

        val cov = covCalculator.clacCov(s1Returns, s2Returns, s1Er, s2Er)
        CovData(s1.stockFileName, s2.stockFileName, cov)
      }

    val vectors = VectorCreator.createVectors(stocksNames)
    val vectorsForDesiredEr = portfolioReturnCalculator.calcReturn(stocksEr, vectors)
//      .map()
//        .max
        .filter(v => v.Er > ((desiredReturn - 2).toDouble / 100) && v.Er < (desiredReturn + 2).toDouble / 100)
    println(s"stocksEr = ${stocksEr}")
    println(s"vectorsForDesiredEr = ${vectorsForDesiredEr}")
//    varianceCalculator.calcVariance(vectorsForDesiredEr, covData)

    // find the min variance vector for a desired E(r).
  }


}

object PortfolioCalculatorService {
  def calcAverageReturn(returns: Seq[Double]) = {
    Math.pow(returns.sum / returns.size, 12)
  }
}
