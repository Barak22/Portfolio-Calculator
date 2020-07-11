package com.portfolio.domain

import org.joda.time.DateTime

case class IndexRawData(
                         stockFileName: String,
                         stockData: Seq[RawLine]
                       )

case class RawLine(
                    date: DateTime,
                    adjClose: Double
                  )

case class ReturnData(
                       date: DateTime,
                       r: Double
                     )

case class StockReturnData(
                            stockFileName: String,
                            stockData: Seq[ReturnData]
                          )

case class CovData(
                    s1: String,
                    s2: String,
                    cov: Double
                  )

case class StockWeight(
                        stockName: String,
                        weight: Double
                      )

case class VectorWeights(
                          weights: Seq[StockWeight]
                        )

case class VectorReturn(
                         weights: Seq[StockWeight],
                         Er: Double
                       )

case class VectorVariance(
                           weights: Seq[StockWeight],
                           Er: Double,
                           variance: Double
                         )
