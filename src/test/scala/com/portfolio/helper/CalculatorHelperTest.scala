package com.portfolio.helper

import org.specs2.mutable.Specification

class CalculatorHelperTest extends Specification {

  "CalculatorHelper" should {
    "calculate number of vectors" >> {
      "for 1 stock" in {
        CalculatorHelper.calculateNumberOfVectors(1) must beEqualTo(1)
      }

      "for 2 stocks" in {
        CalculatorHelper.calculateNumberOfVectors(2) must beEqualTo(100)
      }

      "for 3 stocks" in {
        CalculatorHelper.calculateNumberOfVectors(3) must beEqualTo(5151)
      }

      "for 4 stocks" in {
        CalculatorHelper.calculateNumberOfVectors(4) must beEqualTo(176851)
      }
    }
  }
}
