package com.portfolio.io

import com.portfolio.domain.VectorStdev
import com.portfolio.http.Price

trait DataWriter {
  def writeVectors(fileName: String, vectors: Iterator[VectorStdev]): Unit

  def writeStockData(fileName: String, stockData: Iterator[Price]): Unit
}
