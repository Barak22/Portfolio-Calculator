package com.portfolio.io

import java.io.{ File, PrintWriter, Writer }

import com.portfolio.domain.VectorStdev
import com.portfolio.http.Price

import scala.util.Try

class TxtDataWriter extends DataWriter {
  val dir = "./src/main/resources/results"

  override def writeVectors(fileName: String, vectors: Iterator[VectorStdev]): Unit = {
    new File(dir).mkdir()
    val writer = new PrintWriter(new File(s"$dir/$fileName"))

    Try(writeVectorsToFile(writer, vectors))
    writer.close()
  }

  override def writeStockData(fileName: String, stockData: Iterator[Price]): Unit = ???

  private def writeVectorsToFile(writer: Writer, vectors: Iterator[VectorStdev]): Unit =
    vectors.foreach(vector => writer.write(vector.toString + System.lineSeparator()))
}
