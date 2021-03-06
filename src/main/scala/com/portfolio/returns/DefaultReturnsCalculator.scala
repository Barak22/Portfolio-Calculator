package com.portfolio.returns

import com.portfolio.domain.{ RawLine, ReturnData }

import scala.annotation.tailrec

class DefaultReturnsCalculator extends ReturnsCalculator {

  override def calculateReturns(stockData: Seq[RawLine]): Seq[ReturnData] = {
    doCalculation(stockData.sliding(2), Seq.empty)
  }


  @tailrec
  private def doCalculation(window: Iterator[Seq[RawLine]], acc: Seq[ReturnData]): Seq[ReturnData] = {
    if (!window.hasNext) acc
    else {
      val firstData :: secondData :: Nil = window.next()
      val r = Math.log(firstData.adjClose / secondData.adjClose)

      val returnData = ReturnData(firstData.date, r)
      doCalculation(window, acc :+ returnData)
    }
  }
}
