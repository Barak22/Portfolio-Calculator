package com.portfolio.io

import com.portfolio.domain.VectorStdev

trait DataWriter {
  def writeVectors(fileName: String, vectors: Seq[VectorStdev]): Unit
}
