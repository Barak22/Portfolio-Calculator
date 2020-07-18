package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorReturn, VectorVariance }
import com.portfolio.helper.Utils
import com.portfolio.measure.DurationMeasurer

import scala.annotation.tailrec

class DefaultVarianceCalculator(measurer: DurationMeasurer) extends VarianceCalculator {

  override def calcVariance(vectors: Seq[VectorReturn], covData: Map[String, CovData]): Seq[VectorVariance] =
    calcVarianceHelper(vectors, covData, Nil)

  @tailrec
  private def calcVarianceHelper(vectors: Seq[VectorReturn], covData: Map[String, CovData], acc: Seq[VectorVariance]): Seq[VectorVariance] = {
    if (vectors.isEmpty) acc
    else {
      val vector = vectors.head.weights
      val variance = vector.map {
        s1 =>
          vector.map {
            s2 =>
              val cov = covData(Utils.makeKeyFromTwoStocksNames(s1.stockName, s2.stockName)).cov
              roundNumber(s1.weight * s2.weight * cov)
          }.sum
      }.sum

      calcVarianceHelper(vectors.tail, covData, acc :+ VectorVariance(vector, vectors.head.Er, variance))
    }
  }

  private def roundNumber(num: Double) =
    Math.round(num * 1000000000).toDouble / 1000000000
}
