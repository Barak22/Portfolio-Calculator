package com.portfolio.variance

import com.portfolio.domain.{ CovData, StockWeight, VectorVariance, VectorWeights }
import com.portfolio.vector.VectorCreator

class DefaultVarianceCalculator extends VarianceCalculator {

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
    val vectors = VectorCreator.createVectors(stocksNames, 100)
    val variance = calcVariance(vectors, covData, Nil)
    //        val Er = calaReturn(vectors)
    ???
  }
}
