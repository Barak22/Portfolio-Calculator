package com.portfolio.http

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.{ DeserializationFeature, ObjectMapper }
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.joda.time.DateTime
import scalaj.http.Http

class YahooFinanceHttpClient {
  def getHistoricData(symbol: String, frequency: String, from: DateTime, to: DateTime) = {
    implicit val mapper = createObjectMapper()

    val yahooFinanceResponse = Http("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-historical-data")
      .param(YahooFinanceHttpClient.symbol, symbol)
      .param(YahooFinanceHttpClient.frequency, frequency)
      .param(YahooFinanceHttpClient.from, millisInSeconds(from))
      .param(YahooFinanceHttpClient.to, millisInSeconds(to))
      .param(YahooFinanceHttpClient.filterHistoryData._1, YahooFinanceHttpClient.filterHistoryData._2)
      .header(YahooFinanceHttpClient.hostHeader._1, YahooFinanceHttpClient.hostHeader._2)
      .header(YahooFinanceHttpClient.keyHeader._1, YahooFinanceHttpClient.keyHeader._2)
      .method(YahooFinanceHttpClient.get)
      .timeout(6000, 6000)
      .asString
      .body

    val nonDividendData: PriceWithType => Boolean =
      _.`type` != YahooFinanceHttpClient.dividend

    val toPrice: PriceWithType => Price =
      p => Price(new DateTime(p.date * 1000), p.adjclose)

    val prices = fromJson[RawResponse](yahooFinanceResponse).prices
      .filter(nonDividendData)
      .map(toPrice)

    Response(prices)
  }


  private def createObjectMapper() = {
    val mapper = new ObjectMapper() with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    mapper

  }

  private def fromJson[T](json: String)(implicit mapper: ObjectMapper with ScalaObjectMapper, m: Manifest[T]): T =
    mapper.readValue[T](json)

  private def millisInSeconds(from: DateTime) =
    (from.getMillis / 1000).toString
}

object YahooFinanceHttpClient {
  val get = "GET"
  val dividend = "DIVIDEND"
  val symbol = "symbol"
  val frequency = "frequency"
  val from = "period1"
  val to = "period2"
  val filterHistoryData = ("filter", "history")
  val hostHeader = ("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
  val keyHeader = ("x-rapidapi-key", "78ce9280b6mshc900fa7ce09dab8p113ccbjsn286ee4888437")

}

object F extends App {
  val y = new YahooFinanceHttpClient
  y.getHistoricData("SPY", "1mo", DateTime.parse("2015-08-01"), DateTime.parse("2020-08-01"))
}

case class RawResponse(prices: Seq[PriceWithType])

case class Response(prices: Seq[Price])

case class PriceWithType(
                          date: Long,
                          adjclose: Double,
                          @JsonProperty("type") `type`: String
                        )

case class Price(
                  date: DateTime,
                  adjclose: Double,
                )

