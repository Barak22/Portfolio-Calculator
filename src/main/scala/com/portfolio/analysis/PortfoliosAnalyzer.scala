package com.portfolio.analysis

import com.portfolio.domain.VectorStdev


class PortfoliosAnalyzer() {
  def analyzePortfolios(vectors: Iterator[VectorStdev]): Map[Double, VectorStdev] =
    getMinimumRiskPortfoliosMap(vectors, Map.empty)

  // TODO: Need to refactor this method.
  // This method collects the minimum risk portfolios for each E(r) level
  @scala.annotation.tailrec
  private def getMinimumRiskPortfoliosMap(
                                           vectors: Iterator[VectorStdev],
                                           minimumPortfolios: Map[Double, VectorStdev]
                                         ): Map[Double, VectorStdev] = {
    if (!vectors.hasNext) minimumPortfolios
    else {
      val vector = vectors.next()
      val newMinimumPortfolios = minimumPortfolios.get(vector.Er).map {
        vectorFromMap =>
          if (vector.stdev < vectorFromMap.stdev) minimumPortfolios + (vector.Er -> vector)
          else minimumPortfolios
      }.getOrElse(minimumPortfolios + (vector.Er -> vector))

      getMinimumRiskPortfoliosMap(vectors, newMinimumPortfolios)
    }
  }
}
