package com.portfolio.cov

class DefaultCovCalculator extends CovCalculator {

  override def clacCov(s1: Seq[Double], s2: Seq[Double], s1Er: Double, s2Er: Double): Double = {
    val cov = (s1 zip s2).foldLeft(0d) {
      case (acc, (r1, r2)) => acc + ((r1 - s1Er) * (r2 - s2Er))
    }

    cov / (s1.size - 1)
  }
}
