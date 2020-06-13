package com.portfolio.service

import com.portfolio.data_reader.DataReader
import com.portfolio.returns.ReturnsCalculator

class PortfolioCalculatorService(dataReader: DataReader,
                                 returnsCalculator: ReturnsCalculator) {
  def calculateMarketPortfolio() = {
    val indexesRawData = dataReader.readFiles()
    //    val returns = returnsCalculator.calculateReturns(indexesRawData)
  }
}
