package com.portfolio.returns

import com.portfolio.converters.Converters.`String -> DateTime`
import com.portfolio.domain.{ RawLine, ReturnData }
import org.specs2.mutable.Specification

class DefaultReturnsCalculatorTest extends Specification {

  private val returnsCalculator = new DefaultReturnsCalculator()

  "ReturnsCalculator" should {
    "should return [ 0 ] when calculating the same number" in {
      val stockData = Seq(RawLine("2020-05-28".toDateTime, 1), RawLine("2020-05-01".toDateTime, 1))

      returnsCalculator.calculateReturns(stockData) must
        beEqualTo(Seq(ReturnData("2020-05-28".toDateTime, 0)))
    }

    "should return [ 0.03 ] when calculating [ 303.070007/302.970001 ]" in {
      val stockData = Seq(RawLine("2020-05-28".toDateTime, 303.070007), RawLine("2020-05-01".toDateTime, 302.970001))

      returnsCalculator.calculateReturns(stockData) must
        beEqualTo(Seq(ReturnData("2020-05-28".toDateTime, 0.03)))

    }

    "should return [ 0.03, 4.21 ] when calculating [ 303.070007/302.970001, 302.970001/290.480011 ]" in {
      val stockData = Seq(
        RawLine("2020-05-28".toDateTime, 303.070007),
        RawLine("2020-05-01".toDateTime, 302.970001),
        RawLine("2020-04-01".toDateTime, 290.480011)
      )

      returnsCalculator.calculateReturns(stockData) must
        beEqualTo(Seq(ReturnData("2020-05-28".toDateTime, 0.03), ReturnData("2020-05-01".toDateTime, 4.21)))

    }
  }
}
