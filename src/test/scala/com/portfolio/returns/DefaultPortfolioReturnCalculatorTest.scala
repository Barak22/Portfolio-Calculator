package com.portfolio.returns

import com.portfolio.domain.{ StockWeight, VectorReturn, VectorWeights }
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class DefaultPortfolioReturnCalculatorTest extends Specification {

  private val portfolioReturnCalculator = new DefaultPortfolioReturnCalculator

  "DefaultPortfolioReturnCalculator" should {
    "calculate return for 1 stock 1 vector" in new Context {
      portfolioReturnCalculator.calcReturn(
        stocksReturn,
        Iterator(VectorWeights(Seq(StockWeight(stockName1, 0.03))))).toSeq must
        containTheSameElementsAs(
          Seq(VectorReturn(Seq(StockWeight(stockName1, 0.03)), 0.03 * stock1Er)))
    }

    "calculate return for 2 stocks 1 vector" in new Context {
      val Er = 0.02 * stock1Er + 0.01 * stock2Er
      portfolioReturnCalculator.calcReturn(stocksReturn,
        Iterator(VectorWeights(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01))))).toSeq must
        containTheSameElementsAs(
          Seq(VectorReturn(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01)), Er)))
    }

    "calculate return for 2 stocks and 2 vectors" in new Context {
      val Er = 0.02 * stock1Er + 0.01 * stock2Er
      portfolioReturnCalculator.calcReturn(stocksReturn,
        Iterator(
          VectorWeights(Seq(StockWeight(stockName1, 0.03), StockWeight(stockName2, 0))),
          VectorWeights(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01))))).toSeq must
        containTheSameElementsAs(
          Seq(VectorReturn(Seq(StockWeight(stockName1, 0.03), StockWeight(stockName2, 0)), 0.03 * stock1Er),
            VectorReturn(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01)), Er)))
    }
  }

  trait Context extends Scope {
    val stockName1 = "stock1"
    val stockName2 = "stock2"
    val stock1Er = 0.05
    val stock2Er = 0.1
    val stocksReturn = Map(
      stockName1 -> stock1Er,
      stockName2 -> stock2Er
    )
  }

}
