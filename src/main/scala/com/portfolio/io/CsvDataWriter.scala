package com.portfolio.io

import java.io.File

import com.github.tototoshi.csv.CSVWriter
import com.portfolio.domain.VectorStdev

class CsvDataWriter extends DataWriter {
  val dir = "./src/main/resources/results"

  override def writeVectors(fileName: String, vectors: Iterator[VectorStdev]): Unit = {
    new File(dir).mkdir()
    val writer: CSVWriter = CSVWriter.open(new File(s"$dir/$fileName"))

    writeRowsToFile(writer, vectors)
  }

  // TODO: Add AtomicBoolean instead of isFirstIteration
  @scala.annotation.tailrec
  private def writeRowsToFile(writer: CSVWriter, vectors: Iterator[VectorStdev], isFirstIteration: Boolean = true): Unit = {
    if (vectors.hasNext) {
      val next = vectors.next()
      if (isFirstIteration) {
        val headers = next.weights.map(_.stockName) ++ Seq("E(r)", "StDev")
        val row = next.weights.map(_.weight) ++ Seq(next.Er, next.stdev)
        writer.writeRow(headers)
        writer.writeRow(row)
      } else {
        val row = next.weights.map(_.weight) ++ Seq(next.Er, next.stdev)
        writer.writeRow(row)
      }
      writeRowsToFile(writer, vectors, isFirstIteration = false)
    }
  }

}
