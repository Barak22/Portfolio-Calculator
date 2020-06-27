package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorData }

import scala.annotation.tailrec

class DefaultVarianceCalculator extends VarianceCalculator {

  /*
  n = 3         n = 2       n = 1

  s1  s2  s3    s1  s2        s1
  3   0   0     3   0         3
  2   1   0     2   1         2
  2   0   1     1   2         1
  1   2   0     0   3         0
  1   1   1
  1   0   2
  0   3   0
  0   2   1
  0   1   2
  0   0   3
   */

  // will generate the columns of the matrix
  def createVectors(stocksNames: Seq[String], percentageToDistribute: Int): Seq[Seq[VectorData]] =
    createAllPermutations(stocksNames, percentageToDistribute, Seq(Nil))
      .filter(_.map(_.weight).sum == percentageToDistribute)
      .map(_.sortBy(s => s.stockName))

  @tailrec
  private def createAllPermutations(stocksNames: Seq[String], percentageToDistribute: Int, acc: Seq[Seq[VectorData]]): Seq[Seq[VectorData]] =
    if (stocksNames.isEmpty) acc
    else {
      createAllPermutations(
        stocksNames.tail,
        percentageToDistribute,
        Range.inclusive(0, percentageToDistribute).reverse.flatMap(k => acc.map(v => VectorData(stocksNames.head, k) +: v)))
    }


  // will generate the rows of the matrix
  def generateVectors() = {
    ???
  }

  override def calcMinimalVarianceForReturn(stocksNames: Seq[String],
                                            covData: Seq[CovData],
                                            indexesEr: Map[String, Double],
                                            desiredReturn: Int): Option[Seq[VectorData]] = {
    val vector = createVectors(stocksNames, 100)
    //        val variance = calcVariance(vector)
    //        val Er = calaReturn(vector)
    ???
  }
}
