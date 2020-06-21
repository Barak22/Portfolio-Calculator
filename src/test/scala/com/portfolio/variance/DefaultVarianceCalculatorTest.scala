package com.portfolio.variance

import com.portfolio.domain.{ CovData, VectorData }
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class DefaultVarianceCalculatorTest extends Specification {

  private val varianceCalculator = new DefaultVarianceCalculator()

  "DefaultVarianceCalculator" should {
    "calculate minimal variance for one index" in new Context {
      val covDataForOneIndex = Seq(CovData(index1, index1, 5))
      val index1Er = Map(index1 -> 10d)
      varianceCalculator.calcMinimalVarianceForReturn(covDataForOneIndex, index1Er, 10) must
      beSome(Seq(VectorData(index1, 100)))
    }
  }

  trait Context extends Scope {
    val index1 = "index1"
  }

}
