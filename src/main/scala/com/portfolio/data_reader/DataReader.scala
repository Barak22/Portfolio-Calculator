package com.portfolio.data_reader

import com.portfolio.domain.IndexRawData

trait DataReader {
  def readFiles(): Seq[IndexRawData]
}
