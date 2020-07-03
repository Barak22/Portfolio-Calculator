package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorVariance, VectorWeights }

trait VarianceCalculator {
  def calcVariance(vectors: Seq[VectorWeights], covData: Seq[CovData]): Seq[VectorVariance]
}
