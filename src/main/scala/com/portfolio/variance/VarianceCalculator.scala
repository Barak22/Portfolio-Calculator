package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorData }

trait VarianceCalculator {
  def calcMinimalVarianceForReturn(covData: Seq[CovData], indexesEr: Map[String, Double],desiredReturn: Int): Option[Seq[VectorData]]
}
