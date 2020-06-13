package com.portfolio.data_reader

trait DataReader {
  def readFiles(): Seq[IndexRawData]
}
