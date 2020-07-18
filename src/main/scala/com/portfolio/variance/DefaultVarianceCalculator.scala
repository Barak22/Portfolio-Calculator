package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorReturn, VectorVariance }
import com.portfolio.helper.Utils
import com.portfolio.measure.DurationMeasurer

class DefaultVarianceCalculator(measurer: DurationMeasurer) extends VarianceCalculator {

  override def calcVariance(vectors: Seq[VectorReturn], covData: Map[String, CovData]): Iterator[VectorVariance] =
    vectors.iterator.map(vector => calcVarianceHelper(vector, covData))


  private def calcVarianceHelper(vector: VectorReturn, covData: Map[String, CovData]): VectorVariance = {
    val weights = vector.weights
    val variance = weights.map {
      s1 =>
        weights.map {
          s2 =>
            val cov = covData(Utils.makeKeyFromTwoStocksNames(s1.stockName, s2.stockName)).cov
            roundNumber(s1.weight * s2.weight * cov)
        }.sum
    }.sum

    VectorVariance(weights, vector.Er, variance)
  }

  private def roundNumber(num: Double) =
    Math.round(num * 1000000000).toDouble / 1000000000
}
