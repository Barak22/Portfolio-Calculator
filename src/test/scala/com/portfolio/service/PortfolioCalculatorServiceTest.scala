package com.portfolio.service

import org.specs2.mutable.Specification

class PortfolioCalculatorServiceTest extends Specification {
  "PortfolioCalculatorService" should {
    "calc average" in {
      PortfolioCalculatorService.calAverageReturn(Seq(1)) must beEqualTo(1)
      PortfolioCalculatorService.calAverageReturn(Seq(1,2)) must beEqualTo(1.5)
      PortfolioCalculatorService.calAverageReturn(Seq(1,2,3,4,5)) must beEqualTo(3)
    }
  }

}
