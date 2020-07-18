package com.portfolio.helper

object Utils {
  def makeKeyFromTwoStocksNames(stockName1: String, stockName2: String) =
    s"$stockName1-$stockName2"
}
