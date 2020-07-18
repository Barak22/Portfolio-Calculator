package com.portfolio.vector

import com.portfolio.domain.{ StockWeight, VectorWeights }
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class VectorCreatorTest extends Specification {

  "VectorCreator" should {
    "generateVectors" >> {
      "generate vectors for 1 stock" in new Context {
        VectorCreator.createVectors(Seq(stockName1), 3).toSeq must
          containTheSameElementsAs(Seq(VectorWeights(Seq(StockWeight(stockName1, 0.03)))))
      }

      "generate vectors for 2 stocks" in new Context {
        VectorCreator.createVectors(Seq(stockName1, stockName2), 3).toSeq must
          containTheSameElementsAs(Seq(
            VectorWeights(Seq(StockWeight(stockName1, 0.03), StockWeight(stockName2, 0))),
            VectorWeights(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01))),
            VectorWeights(Seq(StockWeight(stockName1, 0.01), StockWeight(stockName2, 0.02))),
            VectorWeights(Seq(StockWeight(stockName1, 0), StockWeight(stockName2, 0.03)))))
      }

      "generate vectors for 3 stocks" in new Context {
        VectorCreator.createVectors(Seq(stockName1, stockName2, stockName3), 3).toSeq must
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
    }


  }

  trait Context extends Scope {
    val stockName1 = "stock1"
    val stockName2 = "stock2"
    val stockName3 = "stock3"
  }

}
