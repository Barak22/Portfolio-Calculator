package com.portfolio.io

import java.io.File

import com.portfolio.domain.{ Path, StockWeight, VectorStdev }
import com.portfolio.helper.TestRandomizer.{ randomDouble, randomStr }
import org.specs2.mutable.Specification
import org.specs2.specification.{ AfterAll, Scope }

import scala.reflect.io.Directory

class DefaultPortfoliosReaderTest extends Specification with AfterAll {
  private val writer = new CsvDataWriter(Path.testResultsFilesPath)
  private val reader = new DefaultPortfoliosReader(Path.testResultsFilesPath)

  "DefaultPortfoliosReader" should {
    "read portfolio results with one vector" in new Context {
      val stockName1 = randomStr
      val stockName2 = randomStr
      val vector = randomVectorStd(stockName1, stockName2)

      givenVectorsInFile(Seq(vector))

      reader.readVectorsResultFile(Seq(stockName1, stockName2), fileName).toSeq must beEqualTo(Seq(vector))
    }

    "read portfolio results with two vectors" in new Context {
      val stockName1 = randomStr
      val stockName2 = randomStr
      val vector1 = randomVectorStd(stockName1, stockName2)
      val vector2 = randomVectorStd(stockName1, stockName2)

      givenVectorsInFile(Seq(vector1, vector2))

      reader.readVectorsResultFile(Seq(stockName1, stockName2), fileName).toSeq must
        containTheSameElementsAs(Seq(vector1, vector2))
    }

    "read portfolio results with two vectors by using the iterator capabilities" in new Context {
      val stockName1 = randomStr
      val stockName2 = randomStr
      val vector1 = randomVectorStd(stockName1, stockName2)
      val vector2 = randomVectorStd(stockName1, stockName2)

      givenVectorsInFile(Seq(vector1, vector2))


      val iterator = reader.readVectorsResultFile(Seq(stockName1, stockName2), fileName)
      iterator.next() must beEqualTo(vector1)

      iterator.next() must beEqualTo(vector2)

      iterator.hasNext must beFalse

    }

  }

  trait Context extends Scope {
    val fileName = s"$randomStr.csv"

    def givenVectorsInFile = givenVectorsFor(fileName) _
  }

  def givenVectorsFor(fileName: String)(vectors: Seq[VectorStdev]) =
    writer.writeVectors(fileName, vectors.iterator)

  def randomStockWeight(stockName: String) = StockWeight(stockName, randomDouble)

  def randomVectorStd(stocksNames: String*) = {
    val stocksWeights = stocksNames.map(randomStockWeight)
    val Er = randomDouble
    val stdev = randomDouble
    VectorStdev(stocksWeights, Er, stdev)
  }

  override def afterAll() {
    new Directory(new File(Path.testResultsFilesPath)).deleteRecursively()
  }

}
