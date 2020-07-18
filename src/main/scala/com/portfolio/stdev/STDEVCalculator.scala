package com.portfolio.stdev

import com.portfolio.domain.{ VectorStdev, VectorVariance }

object STDEVCalculator {
  def calculateStdev(vectorsWithVariance: Iterator[VectorVariance]): Iterator[VectorStdev] =
    vectorsWithVariance.map {
      case VectorVariance(weights, er, variance) =>
        VectorStdev(weights, er, Math.sqrt(variance))
    }

}
