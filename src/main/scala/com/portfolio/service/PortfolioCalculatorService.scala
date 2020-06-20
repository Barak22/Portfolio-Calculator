package com.portfolio.service

import com.portfolio.cov.CovCalculator
import com.portfolio.data_reader.DataReader
import com.portfolio.domain.{ CovData, IndexRawData, IndexReturnData, ReturnData }
import com.portfolio.returns.ReturnsCalculator

class PortfolioCalculatorService(dataReader: DataReader,
                                 returnsCalculator: ReturnsCalculator,
                                 covCalculator: CovCalculator) {
  def calculateMarketPortfolio() = {
    val indexesRawData: Seq[IndexRawData] = dataReader.readFiles()
    val indexesReturns: Seq[IndexReturnData] = indexesRawData.map {
      indexRawData =>
        val indexReturns: Seq[ReturnData] = returnsCalculator.calculateReturns(indexRawData.stockData)
        IndexReturnData(indexRawData.stockFileName, indexReturns)
    }

    val indexesEr: Map[String, Double] = indexesReturns
      .map(i =>
        i.stockFileName -> PortfolioCalculatorService.calAverageReturn(i.stockData.map(_.r))).toMap

    val covData: Seq[CovData] =
      for {
        s1 <- indexesReturns
        s2 <- indexesReturns
      } yield {
        val r1 = s1.stockData.map(_.r)
        val r2 = s2.stockData.map(_.r)
        val s1Er = indexesEr(s1.stockFileName)
        val s2Er = indexesEr(s2.stockFileName)

        val cov = covCalculator.clacCov(r1, r2, s1Er, s2Er)
        CovData(s1.stockFileName, s2.stockFileName, cov)
      }

    // find the min variance vector for a desired E(r).
  }


}

object PortfolioCalculatorService {
  def calAverageReturn(returns: Seq[Double]) = {
    returns.foldLeft(0d)((acc, r) => acc + r) / returns.size
  }
}
