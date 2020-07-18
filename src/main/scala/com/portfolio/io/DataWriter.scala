package com.portfolio.io

import com.portfolio.domain.VectorStdev

trait DataWriter {
  def writeVectors(fileName: String, vectors: Iterator[VectorStdev]): Unit
}
