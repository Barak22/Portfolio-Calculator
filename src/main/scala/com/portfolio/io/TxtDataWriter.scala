package com.portfolio.io

import java.io.{ File, PrintWriter, Writer }

import com.portfolio.domain.VectorStdev

import scala.util.Try

class TxtDataWriter extends DataWriter {
  val dir = "./src/main/resources/results"

  override def writeVectors(fileName: String, vectors: Seq[VectorStdev]): Unit = {
    new File(dir).mkdir()
    val writer = new PrintWriter(new File(s"$dir/$fileName"))

    Try(writeVectorsToFile(writer, vectors))
    writer.close()
  }

  @scala.annotation.tailrec
  private def writeVectorsToFile(writer: Writer, vectors: Seq[VectorStdev]): Unit = {
    if (vectors.isEmpty) ()
    else {
      writer.write(vectors.head.toString + System.lineSeparator())
      writeVectorsToFile(writer, vectors.tail)
    }
  }
}
