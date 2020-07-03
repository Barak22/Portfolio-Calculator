package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorVariance, VectorWeights }

class DefaultVarianceCalculator extends VarianceCalculator {

  override def calcVariance(vectors: Seq[VectorWeights], covData: Seq[CovData]): Seq[VectorVariance] =
    calcVarianceHelper(vectors, covData, Nil)

  def calcVarianceHelper(vectors: Seq[VectorWeights], covData: Seq[CovData], acc: Seq[VectorVariance]): Seq[VectorVariance] = {
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

              roundNumber(s1.weight * s2.weight * cov)
          }.sum
      }.sum

      calcVarianceHelper(vectors.tail, covData, acc :+ VectorVariance(vector, variance))
    }
  }

  private def roundNumber(num: Double) =
    Math.round(num * 1000000000).toDouble / 1000000000
}
