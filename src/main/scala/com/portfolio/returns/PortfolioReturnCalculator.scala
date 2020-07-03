package com.portfolio.returns

import com.portfolio.domain.{ VectorReturn, VectorWeights }

trait PortfolioReturnCalculator {

  def calcReturn(indexesEr: Map[String, Double], vectors: Seq[VectorWeights]): Seq[VectorReturn]

}
