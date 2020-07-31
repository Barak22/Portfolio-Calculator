package com.portfolio.io

import java.io.File

import com.github.tototoshi.csv.CSVReader
import com.portfolio.domain.{ StockWeight, VectorStdev }

class DefaultPortfoliosReader(path: String) extends PortfoliosReader {
  override def readVectorsResultFile(stocksNames: Seq[String], fileName: String): Iterator[VectorStdev] = {
    CSVReader.open(new File(s"$path/$fileName"))
      .iteratorWithHeaders
      .map {
        line =>
          val stocksWeight =
            stocksNames
              .map(stockName => {
                val weight = line(stockName).toDouble
                StockWeight(stockName, weight)
              })
          val Er = line("E(r)").toDouble
          val stdev = line("StDev").toDouble
          VectorStdev(stocksWeight, Er, stdev)
      }
  }
}
