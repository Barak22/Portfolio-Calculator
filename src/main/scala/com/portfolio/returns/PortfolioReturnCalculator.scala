package com.portfolio.returns

import com.portfolio.domain.{ VectorReturn, VectorWeights }

trait PortfolioReturnCalculator {

  def calcReturn(indexesEr: Map[String, Double], vectors: Iterator[VectorWeights]): Iterator[VectorReturn]

}
