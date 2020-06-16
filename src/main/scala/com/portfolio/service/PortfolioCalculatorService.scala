package com.portfolio.service

import com.portfolio.data_reader.DataReader
import com.portfolio.domain.{ IndexRawData, IndexReturnData, ReturnData }
import com.portfolio.returns.ReturnsCalculator

class PortfolioCalculatorService(dataReader: DataReader,
                                 returnsCalculator: ReturnsCalculator) {
  def calculateMarketPortfolio() = {
    val indexesRawData: Seq[IndexRawData] = dataReader.readFiles()
    val indexesReturns = indexesRawData.map {
      indexRawData =>
        val indexReturns: Seq[ReturnData] = returnsCalculator.calculateReturns(indexRawData.stockData)
        IndexReturnData(indexRawData.stockFileName, indexReturns)
    }
  }
}
