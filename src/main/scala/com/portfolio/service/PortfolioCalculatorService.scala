package com.portfolio.service

import com.portfolio.cov.CovCalculator
import com.portfolio.data_reader.DataReader
import com.portfolio.domain.{ CovData, IndexRawData, IndexReturnData, ReturnData }
import com.portfolio.returns.ReturnsCalculator
import com.portfolio.variance.VarianceCalculator

class PortfolioCalculatorService(dataReader: DataReader,
                                 returnsCalculator: ReturnsCalculator,
                                 covCalculator: CovCalculator,
                                 varianceCalculator: VarianceCalculator,
                                 desiredReturn: 10) {
  def calculateMarketPortfolio() = {
    val indexesRawData: Seq[IndexRawData] = dataReader.readFiles()
    val stocksNames = indexesRawData.map(_.stockFileName)
    val indexesReturns: Seq[IndexReturnData] = indexesRawData.map {
      indexRawData =>
        val indexReturns: Seq[ReturnData] = returnsCalculator.calculateReturns(indexRawData.stockData)
        IndexReturnData(indexRawData.stockFileName, indexReturns)
    }

    val indexesEr: Map[String, Double] = indexesReturns
      .map(i =>
        i.stockFileName -> PortfolioCalculatorService.calcAverageReturn(i.stockData.map(_.r))).toMap

    val covData: Seq[CovData] =
      for {
        s1 <- indexesReturns
        s2 <- indexesReturns
      } yield {
        val s1Returns = s1.stockData.map(_.r)
        val s2Returns = s2.stockData.map(_.r)
        val s1Er = indexesEr(s1.stockFileName)
        val s2Er = indexesEr(s2.stockFileName)

        val cov = covCalculator.clacCov(s1Returns, s2Returns, s1Er, s2Er)
        CovData(s1.stockFileName, s2.stockFileName, cov)
      }

    varianceCalculator.calcMinimalVarianceForReturn(stocksNames, covData, indexesEr, desiredReturn)

    // find the min variance vector for a desired E(r).
  }


}

object PortfolioCalculatorService {
  def calcAverageReturn(returns: Seq[Double]) = {
    returns.foldLeft(0d)((acc, r) => acc + r) / returns.size
  }
}
