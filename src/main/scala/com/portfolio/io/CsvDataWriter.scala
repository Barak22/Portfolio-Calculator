package com.portfolio.io

import java.io.File

import com.github.tototoshi.csv.CSVWriter
import com.portfolio.domain.VectorStdev
import com.portfolio.http.Price

class CsvDataWriter(stocksDataPath: String, resultsPath: String) extends DataWriter {
  override def writeVectors(fileName: String, vectors: Iterator[VectorStdev]): Unit = {
    new File(resultsPath).mkdir()
    val writer: CSVWriter = CSVWriter.open(new File(s"$resultsPath/$fileName.csv"))

    writeRowsToFile(writer, vectors)
  }

  override def writeStockData(fileName: String, stockData: Iterator[Price]): Unit = {
    new File(stocksDataPath).mkdir()
    val writer: CSVWriter = CSVWriter.open(new File(s"$stocksDataPath/$fileName.csv"))

    writer.writeRow(Seq("Date", "Adj Close"))
    stockData.foreach {
      case Price(date, adjclose) =>
        writer.writeRow(Seq(date.toString("yyyy-MM-dd"), adjclose))
    }
  }

  // TODO: Add AtomicBoolean instead of isFirstIteration
  @scala.annotation.tailrec
  private def writeRowsToFile(writer: CSVWriter, vectors: Iterator[VectorStdev], isFirstIteration: Boolean = true): Unit = {
    if (vectors.hasNext) {
      val vector = vectors.next()
      if (isFirstIteration) {
        val headers = vector.weights.map(_.stockName) ++ Seq("E(r)", "StDev")
        val row = vector.weights.map(_.weight) ++ Seq(vector.Er, vector.stdev)
        writer.writeRow(headers)
        writer.writeRow(row)
      } else {
        val row = vector.weights.map(_.weight) ++ Seq(vector.Er, vector.stdev)
        writer.writeRow(row)
      }
      writeRowsToFile(writer, vectors, isFirstIteration = false)
    }
  }

}
