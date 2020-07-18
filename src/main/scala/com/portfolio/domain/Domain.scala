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
                          weights: Iterator[StockWeight]
                        )

case class VectorReturn(
                         weights: Iterator[StockWeight],
                         Er: Double
                       )

case class VectorVariance(
                           weights: Iterator[StockWeight],
                           Er: Double,
                           variance: Double
                         )

case class VectorStdev(
                        weights: Iterator[StockWeight],
                        Er: Double,
                        stdev: Double
                      )