package com.portfolio.cov

import com.portfolio.service.PortfolioCalculatorService
import org.specs2.mutable.Specification

class DefaultCovCalculatorTest extends Specification {
  private val covCalculator = new DefaultCovCalculator()

  "DefaultCovCalculator" should {
    "return 146.1 for [5, 12, 18, 23, 45], [2, 8, 18, 20, 28]" in {
      val s1: Seq[Double] = Seq(5, 12, 18, 23, 45)
      val s2: Seq[Double] = Seq(2, 8, 18, 20, 28)
      val s1Er = PortfolioCalculatorService.calcMonthlyAverageReturn(s1)
      val s2Er = PortfolioCalculatorService.calcMonthlyAverageReturn(s2)
      covCalculator.clacCov(s1, s2, s1Er, s2Er) must
        beEqualTo(146.1)
    }

  }

}
