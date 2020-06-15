package com.portfolio.returns

import com.portfolio.domain.{ RawLine, ReturnData }

trait ReturnsCalculator {
  def calculateReturns(stockData: Seq[RawLine]): Seq[ReturnData]
}
