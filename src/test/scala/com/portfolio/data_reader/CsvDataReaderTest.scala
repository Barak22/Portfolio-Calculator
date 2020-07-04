package com.portfolio.data_reader

import com.portfolio.domain.{ IndexRawData, RawLine }
import org.joda.time.DateTime
import org.specs2.mutable.Specification

class CsvDataReaderTest extends Specification {
  private val reader = new CsvDataReader("src/test/resources/market-portfolio")

  "CsvDataReader" should {
    "read file with one line" in {
      reader.readFiles() must containTheSameElementsAs(
        Seq(
          IndexRawData("csv-with-one-line.csv", Seq(RawLine(DateTime.parse("2015-06-01"), 186.277267))),
          IndexRawData("csv-with-two-lines.csv",
            Seq(
              RawLine(DateTime.parse("2015-07-01"), 191.411682),
              RawLine(DateTime.parse("2015-06-01"), 186.277267)))
        ))
    }
  }
}
