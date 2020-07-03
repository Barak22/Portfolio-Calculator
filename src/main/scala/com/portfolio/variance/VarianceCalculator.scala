package com.portfolio.variance

import com.portfolio.domain.{ CovData, StockWeight }

trait VarianceCalculator {
  def calcMinimalVarianceForReturn(stocksNames: Seq[String],
                                   covData: Seq[CovData],
                                   indexesEr: Map[String, Double],
                                   desiredReturn: Int): Option[Seq[StockWeight]]
}
