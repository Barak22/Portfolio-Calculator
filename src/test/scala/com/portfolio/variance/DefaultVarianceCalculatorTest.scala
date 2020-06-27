package com.portfolio.variance

import com.portfolio.domain.VectorData
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class DefaultVarianceCalculatorTest extends Specification {

  private val varianceCalculator = new DefaultVarianceCalculator()

  "DefaultVarianceCalculator" should {
    //    "calculate minimal variance for one index" in new Context {
    //      val covDataForOneIndex = Seq(CovData(index1, index1, 5))
    //      val index1Er = Map(index1 -> 10d)
    //      varianceCalculator.calcMinimalVarianceForReturn(1, covDataForOneIndex, index1Er, 10) must
    //      beSome(Seq(VectorData(index1, 100)))
    //    }

    "generateVectors" >> {
      "generate vectors for 1 stock" in new Context {
        varianceCalculator.createVectors(Seq("stock1"), 3) must
          containTheSameElementsAs(Seq(Seq(VectorData("stock1",3))))
      }

      "generate vectors for 2 stocks" in new Context {
        varianceCalculator.createVectors(Seq("stock1", "stock2"), 3) must
          containTheSameElementsAs(Seq(
            Seq(VectorData("stock1", 3), VectorData("stock2", 0)),
            Seq(VectorData("stock1", 2), VectorData("stock2", 1)),
            Seq(VectorData("stock1", 1), VectorData("stock2", 2)),
            Seq(VectorData("stock1", 0), VectorData("stock2", 3))))
      }

      "generate vectors for 3 stocks" in new Context {
        varianceCalculator.createVectors(Seq("stock1", "stock2", "stock3"), 3) must
          containTheSameElementsAs(Seq(
            Seq(VectorData("stock1", 3), VectorData("stock2", 0), VectorData("stock3", 0)),
            Seq(VectorData("stock1", 2), VectorData("stock2", 1), VectorData("stock3", 0)),
            Seq(VectorData("stock1", 2), VectorData("stock2", 0), VectorData("stock3", 1)),
            Seq(VectorData("stock1", 1), VectorData("stock2", 2), VectorData("stock3", 0)),
            Seq(VectorData("stock1", 1), VectorData("stock2", 1), VectorData("stock3", 1)),
            Seq(VectorData("stock1", 1), VectorData("stock2", 0), VectorData("stock3", 2)),
            Seq(VectorData("stock1", 0), VectorData("stock2", 3), VectorData("stock3", 0)),
            Seq(VectorData("stock1", 0), VectorData("stock2", 2), VectorData("stock3", 1)),
            Seq(VectorData("stock1", 0), VectorData("stock2", 1), VectorData("stock3", 2)),
            Seq(VectorData("stock1", 0), VectorData("stock2", 0), VectorData("stock3", 3))))
      }
    }

  }


  trait Context extends Scope {
    val index1 = "index1"

    //    def haveTheSameVectorsAs(expected: Seq[Seq[Int]]): Matcher[Seq[Seq[Int]]] =
    //      (actual: Seq[Seq[Int]]) =>
    //        actual.foreach(vector => vector must containTheSameElementsAs())
  }

}
