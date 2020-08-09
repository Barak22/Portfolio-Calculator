package com.portfolio.http

import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.DateTime

trait DataProvider {
  def getHistoricData(symbol: String, frequency: String, from: DateTime, to: DateTime): Response
}

case class RawResponse(prices: Seq[PriceWithType])

case class PriceWithType(
                          date: Long,
                          adjclose: Double,
                          @JsonProperty("type") `type`: String
                        )

case class Response(symbol: String, prices: Seq[Price])

case class Price(
                  date: DateTime,
                  adjclose: Double,
                )
