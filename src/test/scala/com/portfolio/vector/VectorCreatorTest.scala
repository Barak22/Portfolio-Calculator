package com.portfolio.vector

import com.portfolio.domain.{ StockWeight, VectorWeights }
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class VectorCreatorTest extends Specification {

  "VectorCreator" should {
    "generateVectors" >> {
      "generate vectors for 1 stock" in new Context {
        VectorCreator.createVectors(Seq(stockName1), step = 1, percentageToDistribute = 3).toSeq must
          containTheSameElementsAs(Seq(VectorWeights(Seq(StockWeight(stockName1, 0.03)))))
      }

      "generate vectors for 2 stocks" in new Context {
        VectorCreator.createVectors(Seq(stockName1, stockName2), step = 1, percentageToDistribute = 3).toSeq must
          containTheSameElementsAs(Seq(
            VectorWeights(Seq(StockWeight(stockName1, 0.03), StockWeight(stockName2, 0))),
            VectorWeights(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01))),
            VectorWeights(Seq(StockWeight(stockName1, 0.01), StockWeight(stockName2, 0.02))),
            VectorWeights(Seq(StockWeight(stockName1, 0), StockWeight(stockName2, 0.03)))))
      }

      "generate vectors for 3 stocks" in new Context {
        VectorCreator.createVectors(Seq(stockName1, stockName2, stockName3), step = 1, percentageToDistribute = 3).toSeq must
          containTheSameElementsAs(Seq(
            VectorWeights(Seq(StockWeight(stockName1, 0.03), StockWeight(stockName2, 0), StockWeight(stockName3, 0))),
            VectorWeights(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01), StockWeight(stockName3, 0))),
            VectorWeights(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0), StockWeight(stockName3, 0.01))),
            VectorWeights(Seq(StockWeight(stockName1, 0.01), StockWeight(stockName2, 0.02), StockWeight(stockName3, 0))),
            VectorWeights(Seq(StockWeight(stockName1, 0.01), StockWeight(stockName2, 0.01), StockWeight(stockName3, 0.01))),
            VectorWeights(Seq(StockWeight(stockName1, 0.01), StockWeight(stockName2, 0), StockWeight(stockName3, 0.02))),
            VectorWeights(Seq(StockWeight(stockName1, 0), StockWeight(stockName2, 0.03), StockWeight(stockName3, 0))),
            VectorWeights(Seq(StockWeight(stockName1, 0), StockWeight(stockName2, 0.02), StockWeight(stockName3, 0.01))),
            VectorWeights(Seq(StockWeight(stockName1, 0), StockWeight(stockName2, 0.01), StockWeight(stockName3, 0.02))),
            VectorWeights(Seq(StockWeight(stockName1, 0), StockWeight(stockName2, 0), StockWeight(stockName3, 0.03)))))
      }

      "generate vectors for 2 stocks with step 5" in new Context {
        val numberOfStocks = 2
        VectorCreator.createVectors(Seq(stockName1, stockName2), step = 5).toSeq must
          containTheSameElementsAs(Seq(
            VectorWeights(Seq(StockWeight(stockName1, 1.0), StockWeight(stockName2, 0))),
            VectorWeights(Seq(StockWeight(stockName1, 0.95), StockWeight(stockName2, 0.05))),
            VectorWeights(Seq(StockWeight(stockName1, 0.9), StockWeight(stockName2, 0.1))),
            VectorWeights(Seq(StockWeight(stockName1, 0.85), StockWeight(stockName2, 0.15))),
            VectorWeights(Seq(StockWeight(stockName1, 0.8), StockWeight(stockName2, 0.2))),
            VectorWeights(Seq(StockWeight(stockName1, 0.75), StockWeight(stockName2, 0.25))),
            VectorWeights(Seq(StockWeight(stockName1, 0.7), StockWeight(stockName2, 0.3))),
            VectorWeights(Seq(StockWeight(stockName1, 0.65), StockWeight(stockName2, 0.35))),
            VectorWeights(Seq(StockWeight(stockName1, 0.6), StockWeight(stockName2, 0.4))),
            VectorWeights(Seq(StockWeight(stockName1, 0.55), StockWeight(stockName2, 0.45))),
            VectorWeights(Seq(StockWeight(stockName1, 0.5), StockWeight(stockName2, 0.5))),
            VectorWeights(Seq(StockWeight(stockName1, 0.45), StockWeight(stockName2, 0.55))),
            VectorWeights(Seq(StockWeight(stockName1, 0.4), StockWeight(stockName2, 0.6))),
            VectorWeights(Seq(StockWeight(stockName1, 0.35), StockWeight(stockName2, 0.65))),
            VectorWeights(Seq(StockWeight(stockName1, 0.3), StockWeight(stockName2, 0.7))),
            VectorWeights(Seq(StockWeight(stockName1, 0.25), StockWeight(stockName2, 0.75))),
            VectorWeights(Seq(StockWeight(stockName1, 0.2), StockWeight(stockName2, 0.8))),
            VectorWeights(Seq(StockWeight(stockName1, 0.15), StockWeight(stockName2, 0.85))),
            VectorWeights(Seq(StockWeight(stockName1, 0.1), StockWeight(stockName2, 0.9))),
            VectorWeights(Seq(StockWeight(stockName1, 0.05), StockWeight(stockName2, 0.95))),
            VectorWeights(Seq(StockWeight(stockName1, 0.0), StockWeight(stockName2, 1.0))),
          ))
      }

    }


  }

  trait Context extends Scope {
    val stockName1 = "stock1"
    val stockName2 = "stock2"
    val stockName3 = "stock3"
  }

}
