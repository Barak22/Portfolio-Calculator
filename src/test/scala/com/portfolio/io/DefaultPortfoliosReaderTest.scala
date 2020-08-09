package com.portfolio.io

import java.io.File

import com.portfolio.domain.{ Path, StockWeight, VectorStdev }
import com.portfolio.helper.TestRandomizer.{ randomDouble, randomStr }
import org.specs2.mutable.Specification
import org.specs2.specification.{ AfterAll, Scope }

import scala.reflect.io.Directory

class DefaultPortfoliosReaderTest extends Specification with AfterAll {
  private val writer = new CsvDataWriter(randomStr, Path.testResultsDirPath)
  private val reader = new DefaultPortfoliosReader(Path.testResultsDirPath)

  "DefaultPortfoliosReader" should {
    "read portfolio results with one vector" in new Context {
      val vector = randomVectorStd()

      givenVectorsInFile(Seq(vector))

      reader.readVectorsResultFile(fileName).toSeq must beEqualTo(Seq(vector))
    }

    "read portfolio results with two vectors" in new Context {
      val vector1 = randomVectorStd()
      val vector2 = randomVectorStd()

      givenVectorsInFile(Seq(vector1, vector2))

      reader.readVectorsResultFile(fileName).toSeq must
        containTheSameElementsAs(Seq(vector1, vector2))
    }

    "read portfolio results with two vectors by using the iterator capabilities" in new Context {
      val vector1 = randomVectorStd()
      val vector2 = randomVectorStd()

      givenVectorsInFile(Seq(vector1, vector2))


      val iterator = reader.readVectorsResultFile(fileName)
      iterator.next() must beEqualTo(vector1)

      iterator.next() must beEqualTo(vector2)

      iterator.hasNext must beFalse

    }

  }

  trait Context extends Scope {
    val fileName = s"$randomStr.csv"
    val stockName1 = randomStr
    val stockName2 = randomStr

    def givenVectorsInFile = givenVectorsFor(fileName) _

    def givenVectorsFor(fileName: String)(vectors: Seq[VectorStdev]) =
      writer.writeVectors(fileName, vectors.iterator)

    def randomStockWeight(stockName: String) = StockWeight(stockName, randomDouble)

    def randomVectorStd() = {
      val stocksWeights1 = randomStockWeight(stockName1)
      val stocksWeights2 = randomStockWeight(stockName2)
      val Er = randomDouble
      val stdev = randomDouble
      VectorStdev(Seq(stocksWeights1, stocksWeights2), Er, stdev)
    }
  }


  override def afterAll() {
    new Directory(new File(Path.testResultsDirPath)).deleteRecursively()
  }

}
