package com.portfolio.variance

import com.portfolio.domain.{ CovData, StockWeight, VectorReturn, VectorVariance }
import com.portfolio.helper.Utils
import com.portfolio.measure.DurationMeasurer
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class DefaultVarianceCalculatorTest extends Specification {

  private val measurer = new DurationMeasurer()
  private val varianceCalculator = new DefaultVarianceCalculator(measurer)

  "DefaultVarianceCalculator" should {

    "calcVariance" >> {
      "calculate the variance for 1 stock 1 vector" in new Context {
        varianceCalculator.calcVariance(Seq(VectorReturn(Seq(StockWeight(stockName1, 0.03)), irrelevantEr)), Map(Utils.makeKeyFromTwoStocksNames(stockName1, stockName1) -> CovData(stockName1, stockName1, 0.00001))).toSeq must
          containTheSameElementsAs(Seq(VectorVariance(Seq(StockWeight(stockName1, 0.03)), irrelevantEr, 0.000000009)))
      }

      "calculate the variance for 2 stocks 1 vector" in new Context {
        varianceCalculator.calcVariance(
          Seq(VectorReturn(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01)), irrelevantEr)),
          Map(
            Utils.makeKeyFromTwoStocksNames(stockName1, stockName1) -> CovData(stockName1, stockName1, 0.00001),
            Utils.makeKeyFromTwoStocksNames(stockName1, stockName2) -> CovData(stockName1, stockName2, 0.00004),
            Utils.makeKeyFromTwoStocksNames(stockName2, stockName1) -> CovData(stockName2, stockName1, 0.00004),
            Utils.makeKeyFromTwoStocksNames(stockName2, stockName2) -> CovData(stockName2, stockName2, 0.00009))).toSeq must
          containTheSameElementsAs(Seq(VectorVariance(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01)), irrelevantEr, 0.000000029)))
      }

      "calculate the variance for 2 stocks and 2 vectors" in new Context {
        varianceCalculator.calcVariance(
          Seq(VectorReturn(Seq(StockWeight(stockName1, 0.03), StockWeight(stockName2, 0)), irrelevantEr), VectorReturn(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01)), irrelevantEr)),
          Map(
            Utils.makeKeyFromTwoStocksNames(stockName1, stockName1) -> CovData(stockName1, stockName1, 0.00001),
            Utils.makeKeyFromTwoStocksNames(stockName1, stockName2) -> CovData(stockName1, stockName2, 0.00004),
            Utils.makeKeyFromTwoStocksNames(stockName2, stockName1) -> CovData(stockName2, stockName1, 0.00004),
            Utils.makeKeyFromTwoStocksNames(stockName2, stockName2) -> CovData(stockName2, stockName2, 0.00009))).toSeq must
          containTheSameElementsAs(
            Seq(VectorVariance(Seq(StockWeight(stockName1, 0.03), StockWeight(stockName2, 0)), irrelevantEr, 0.000000009),
              VectorVariance(Seq(StockWeight(stockName1, 0.02), StockWeight(stockName2, 0.01)), irrelevantEr, 0.000000029)))
      }

    }

  }


  trait Context extends Scope {
    val stockName1 = "stock1"
    val stockName2 = "stock2"
    val irrelevantEr = 10
  }

}
