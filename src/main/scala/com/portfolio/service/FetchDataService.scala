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
  val to = DateTime.now().minusDays(3)
  val from = to.minusYears(15)

  val listOfSymbols =
    "SPY" :: // S&P 500
      "QQQ" :: // Nasdaq
      "MSCI" :: // global emerging markets (Argentina, Brazil, Chile, China, Colombia, Czech Republic, Egypt, Greece, Hungary, India, Indonesia, Korea, Malaysia, Mexico, Pakistan, Peru, Philippines, Poland, Qatar, Russia, Saudi Arabia, South Africa, Taiwan, Thailand, Turkey, and the United Arab Emirates)
      "TA35.TA" :: // Israel
      "FT5=F" :: // China
      "DAX" :: // Germany
      // Commodity
      "GLD" :: // Gold
      "SLV" :: // Silver
      // Companies
      "AAPL" :: // Apple
      "MSFT" :: // Microsoft
      Nil
}

//      "BGNE" ::   // BeiGene
//      "^FTSE" ::  // London
