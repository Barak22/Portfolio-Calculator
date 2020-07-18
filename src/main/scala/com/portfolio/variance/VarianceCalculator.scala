package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorReturn, VectorVariance }

trait VarianceCalculator {
  def calcVariance(vectors: Iterator[VectorReturn], covData: Map[String, CovData]): Iterator[VectorVariance]
}
