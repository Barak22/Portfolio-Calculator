package com.portfolio.io

import com.portfolio.domain.VectorStdev

trait PortfoliosReader {
  def readVectorsResultFile(fileName: String): Iterator[VectorStdev]
}
