package com.portfolio.vector

import com.portfolio.domain.{ StockWeight, VectorWeights }

import scala.annotation.tailrec

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

  // will generate the columns of the matrix
  def createVectors(stocksNames: Seq[String], percentageToDistribute: Int = 100): Seq[VectorWeights] =
    createAllPermutations(stocksNames, percentageToDistribute, Seq(Nil))
      .filter(_.map(_.weight).sum == (percentageToDistribute.toDouble / 100))
      .map(_.sortBy(s => s.stockName))
      .map(VectorWeights)

  @tailrec
  private def createAllPermutations(stocksNames: Seq[String], percentageToDistribute: Int, acc: Seq[Seq[StockWeight]]): Seq[Seq[StockWeight]] =
    if (stocksNames.isEmpty) acc
    else {
      createAllPermutations(
        stocksNames.tail,
        percentageToDistribute,
        Range.inclusive(0, percentageToDistribute)
          .reverse
          .flatMap(weight => acc.map(v => StockWeight(stocksNames.head, weight.toDouble / 100) +: v)))
    }

}
