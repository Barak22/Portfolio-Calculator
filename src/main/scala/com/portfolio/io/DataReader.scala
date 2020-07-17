package com.portfolio.io

import com.portfolio.domain.IndexRawData

trait DataReader {
  def readFiles(): Seq[IndexRawData]
}
