package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorData }

class DefaultVarianceCalculator extends VarianceCalculator {
  override def calcMinimalVarianceForReturn(covData: Seq[CovData], indexesEr: Map[String, Double],desiredReturn: Int): Option[Seq[VectorData]] = ???
}
