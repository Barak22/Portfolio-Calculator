package com.portfolio.variance

import com.portfolio.domain.{ CovData, StockWeight, VectorVariance, VectorWeights }
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class DefaultVarianceCalculatorTest extends Specification {

  private val varianceCalculator = new DefaultVarianceCalculator()

  "DefaultVarianceCalculator" should {

    "calcVariance" >> {
      "calculate the variance for 1 stock 1 vector" in new Context {
        varianceCalculator.calcVariance(Seq(VectorWeights(Seq(StockWeight("stock1", 3)))), Seq(CovData("stock1", "stock1", 0.00001))) must
          containTheSameElementsAs(Seq(VectorVariance(Seq(StockWeight("stock1", 3)), 0.00009)))
      }

      "calculate the variance for 2 stocks 1 vector" in new Context {
        varianceCalculator.calcVariance(
          Seq(VectorWeights(Seq(StockWeight("stock1", 2), StockWeight("stock2", 1)))),
          Seq(CovData("stock1", "stock1", 0.00001),
            CovData("stock1", "stock2", 0.00004),
            CovData("stock2", "stock1", 0.00004),
            CovData("stock2", "stock2", 0.00009))) must
          containTheSameElementsAs(Seq(VectorVariance(Seq(StockWeight("stock1", 2), StockWeight("stock2", 1)), 0.00029)))
      }

      "calculate the variance for 2 stocks and 2 vectors" in new Context {
        varianceCalculator.calcVariance(
          Seq(VectorWeights(Seq(StockWeight("stock1", 3), StockWeight("stock2", 0))), VectorWeights(Seq(StockWeight("stock1", 2), StockWeight("stock2", 1)))),
          Seq(CovData("stock1", "stock1", 0.00001),
            CovData("stock1", "stock2", 0.00004),
            CovData("stock2", "stock1", 0.00004),
            CovData("stock2", "stock2", 0.00009))) must
          containTheSameElementsAs(
            Seq(VectorVariance(Seq(StockWeight("stock1", 3), StockWeight("stock2", 0)), 0.00009),
              VectorVariance(Seq(StockWeight("stock1", 2), StockWeight("stock2", 1)), 0.00029)))
      }

    }

  }


  trait Context extends Scope {
  }

}
