package com.portfolio.io

import java.io.File

import com.github.tototoshi.csv.CSVReader
import com.portfolio.domain.{StockWeight, VectorStdev}

class DefaultPortfoliosReader(path: String) extends PortfoliosReader {
  override def readVectorsResultFile(fileName: String): Iterator[VectorStdev] = {
    CSVReader.open(new File(s"$path/$fileName.csv"))
      .iteratorWithHeaders
      .map {
        line =>
          line.foldLeft(VectorStdev.empty) {
            case (initial, ("E(r)", value)) => initial.copy(Er = value.toDouble)
            case (initial, ("StDev", value)) => initial.copy(stdev = value.toDouble)
            case (initial, (stockName, value)) => initial.copy(weights = initial.weights :+ StockWeight(stockName, value.toDouble))
          }
      }
  }
}
