package com.portfolio.vector

import com.portfolio.domain.{ StockWeight, VectorWeights }

object VectorCreator {
  /*
    n = 3         n = 2       n = 1

    s1  s2  s3    s1  s2        s1
    3   0   0     3   0         3
    2   1   0     2   1
    2   0   1     1   2
    1   2   0     0   3
    1   1   1
    1   0   2
    0   3   0
    0   2   1
    0   1   2
    0   0   3
     */

  def createVectors(stocksNames: Seq[String], percentageToDistribute: Int = 100): Seq[VectorWeights] = {
    if (stocksNames.tail.isEmpty) {
      Seq(VectorWeights(Seq(StockWeight(stocksNames.head, percentageToDistribute.toDouble / 100))))
    } else {
      for {
        weight <- 0 to percentageToDistribute
        vectors <- createVectors(stocksNames.tail, percentageToDistribute - weight)
      } yield vectors.copy(weights = StockWeight(stocksNames.head, weight.toDouble / 100) +: vectors.weights)
    }
  }
}
