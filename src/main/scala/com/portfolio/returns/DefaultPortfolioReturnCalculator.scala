package com.portfolio.returns

import com.portfolio.domain.{ StockWeight, VectorReturn, VectorWeights }

class DefaultPortfolioReturnCalculator extends PortfolioReturnCalculator {
  override def calcReturn(stocksEr: Map[String, Double], vectors: Iterator[VectorWeights]): Iterator[VectorReturn] = {
    vectors.map {
      case VectorWeights(weights) =>
        val Er = weights.map {
          case StockWeight(stockName, weight) =>
            stocksEr(stockName) * weight
        }.sum

        VectorReturn(weights, Er)
    }
  }
}
