package com.portfolio.service

import com.portfolio.http.{ DataProvider, Response, YahooFinanceHttpClient }
import com.portfolio.io.DataWriter
import org.joda.time.DateTime

class FetchDataService(dataProvider: DataProvider, dataWriter: DataWriter) {

  def downloadStockData() =
    FetchDataService.listOfSymbols.map {
      symbol =>
        dataProvider.getHistoricData(
          symbol,
          YahooFinanceHttpClient.monthly,
          FetchDataService.from,
          FetchDataService.to
        )
    } foreach {
      case Response(symbol, prices) =>
        dataWriter.writeStockData(symbol, prices.iterator)
    }

}

object FetchDataService {
  val to = DateTime.now().minusDays(2)
  val from = to.minusYears(15)

  val listOfSymbols =
    "SPY" ::
      "QQQ" ::
      "BGNE" ::
      "MSCI" ::
      "TA35.TA" ::
      "VYM" ::
      "XBI" ::
      "XLE" ::
      "DAX" :: Nil
}
