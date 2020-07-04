package com.portfolio.cov

trait CovCalculator {

  def clacCov(s1: Seq[Double], s2: Seq[Double], s1Er: Double, s2Er: Double): Double
}
