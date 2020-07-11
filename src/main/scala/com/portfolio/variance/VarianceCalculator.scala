package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorReturn, VectorVariance }

trait VarianceCalculator {
  def calcVariance(vectors: Seq[VectorReturn], covData: Seq[CovData]): Seq[VectorVariance]
}
