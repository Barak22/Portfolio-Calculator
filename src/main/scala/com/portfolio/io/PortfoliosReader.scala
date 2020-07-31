package com.portfolio.io

import com.portfolio.domain.VectorStdev

trait PortfoliosReader {
  def readVectorsResultFile(stocksNames: Seq[String], fileName: String): Iterator[VectorStdev]
}
