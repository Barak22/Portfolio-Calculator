package com.portfolio.variance

import com.portfolio.domain.{ CovData, StockWeight, VectorVariance, VectorWeights }

import scala.annotation.tailrec

class DefaultVarianceCalculator extends VarianceCalculator {

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
  def createVectors(stocksNames: Seq[String], percentageToDistribute: Int): Seq[VectorWeights] =
    createAllPermutations(stocksNames, percentageToDistribute, Seq(Nil))
      .filter(_.map(_.weight).sum == percentageToDistribute)
      .map(_.sortBy(s => s.stockName))
      .map(VectorWeights)

  @tailrec
  private def createAllPermutations(stocksNames: Seq[String], percentageToDistribute: Int, acc: Seq[Seq[StockWeight]]): Seq[Seq[StockWeight]] =
    if (stocksNames.isEmpty) acc
    else {
      createAllPermutations(
        stocksNames.tail,
        percentageToDistribute,
        Range.inclusive(0, percentageToDistribute).reverse.flatMap(k => acc.map(v => StockWeight(stocksNames.head, k) +: v)))
    }

  def calcVariance(vectors: Seq[VectorWeights], covData: Seq[CovData], acc: Seq[VectorVariance]): Seq[VectorVariance] = {
    if (vectors.isEmpty) acc
    else {
      val vector = vectors.head.weights
      val variance = vector.map {
        s1 =>
          vector.map {
            s2 =>
              val cov = covData.find(c =>
                (c.s1.equals(s1.stockName) && c.s2.equals(s2.stockName)) ||
                  (c.s2.equals(s1.stockName) && c.s1.equals(s2.stockName))
              ).get.cov

              s1.weight * s2.weight * cov
          }.sum
      }.sum

      calcVariance(vectors.tail, covData, acc :+ VectorVariance(vector, variance))
    }
  }

  override def calcMinimalVarianceForReturn(stocksNames: Seq[String],
                                            covData: Seq[CovData],
                                            indexesEr: Map[String, Double],
                                            desiredReturn: Int): Option[Seq[StockWeight]] = {
    val vectors = createVectors(stocksNames, 100)
    val variance = calcVariance(vectors, covData, Nil)
    //        val Er = calaReturn(vectors)
    ???
  }
}
