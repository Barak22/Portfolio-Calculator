package com.portfolio.analysis

import com.portfolio.domain.{ StockWeight, VectorStdev }
import com.portfolio.helper.TestRandomizer._
import com.portfolio.io.{ CsvDataWriter, DefaultPortfoliosReader }
import org.specs2.mutable.Specification

class PortfoliosAnalyzerIT extends Specification {
  private val reader = new DefaultPortfoliosReader()
  private val writer = new CsvDataWriter()
  private val portfoliosAnalyzer = new PortfoliosAnalyzer(reader, writer)

  "PortfoliosAnalyzer" should {
    "get the minimum stdev portfolio for each return level" in {
      val stockWeight1 = randomStockWeight
      val stockWeight2 = randomStockWeight
      val Er = randomDouble
      val stdev = randomDouble
      val vector = VectorStdev(Seq(stockWeight1, stockWeight2), Er, stdev)
      givenVectorsInFile(vector)

      portfoliosAnalyzer.analyzePortfolios("test-results.csv", "test-efficient-frontier.csv")

      reader.readVectorsResultFile().toSeq must beEqualTo(Seq(vector))
    }
  }

  def givenVectorsInFile(vectors: VectorStdev*) =
    writer.writeVectors("test-results.csv", vectors.iterator)

  def randomStockWeight = StockWeight(randomStr, randomDouble)
}
