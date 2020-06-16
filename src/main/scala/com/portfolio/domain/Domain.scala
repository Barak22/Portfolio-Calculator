package com.portfolio.domain

import org.joda.time.DateTime

case class IndexRawData(stockFileName: String, stockData: Seq[RawLine])

case class RawLine(
                    date: DateTime,
                    adjClose: Double
                  )

case class ReturnData(
                       date: DateTime,
                       r: Double
                     )

case class IndexReturnData(stockFileName: String, stockData: Seq[ReturnData])
