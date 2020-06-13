package com.portfolio.data_reader

import java.io.File

import com.github.tototoshi.csv._
import org.joda.time.DateTime

class CsvDataReader(path: String) extends DataReader {

  // TODO: Need to refactor this method.
  override def readFiles(): Seq[IndexRawData] = {
    val filesToRead = new File(path).listFiles().toList.map(_.getCanonicalPath)
    filesToRead.map {
      filePath =>
        val stockData =
          CSVReader.open(new File(filePath))
            .allWithHeaders()
            .map {
              line =>
                val date = line("Date")
                val adjClose = line("Adj Close")
                RawLine(DateTime.parse(date), adjClose.toDouble)
            }

        IndexRawData(filePath.split("/").last, stockData)
    }
  }
}

case class IndexRawData(stockFileName: String, stockData: Seq[RawLine])

case class RawLine(
                    date: DateTime,
                    adjClose: Double
                  )