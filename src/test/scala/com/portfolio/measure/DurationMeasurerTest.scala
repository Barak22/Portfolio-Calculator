package com.portfolio.measure


import java.lang.System.Logger

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class DurationMeasurerTest extends Specification with Mockito {

  private val logger = Mockito.mock[Logger]
  private val measurer = new DurationMeasurer()

  // TODO: Need to think how to test it
  "DurationMeasurer" should {
    "measure function" in {
      //      val duration = 2000
      //
      //      def functionToMeasureWith(duration: Int) = {
      //        Thread.sleep(duration)
      //        3
      //      }
      //
      //      measurer.measure("functionToMeasure", functionToMeasureWith(duration)) must beEqualTo(3)
      //      mustCalledLoggerWith("functionToMeasure", duration) must beTrue
      ok
    }
  }

  def mustCalledLoggerWith(methodName: String, duration: Int, deviationInMillis: Int = 20) = ???
}
