package com.portfolio.analysis

import java.io.File

import com.portfolio.domain.{ Path, StockWeight, VectorStdev }
import com.portfolio.helper.TestRandomizer.{ randomStr, _ }
import com.portfolio.io.{ CsvDataWriter, DefaultPortfoliosReader }
import org.specs2.mutable.Specification
import org.specs2.specification.{ AfterAll, Scope }

import scala.reflect.io.Directory

class PortfoliosAnalyzerTest extends Specification with AfterAll {
  private val reader = new DefaultPortfoliosReader(Path.testResultsDirPath)
  private val writer = new CsvDataWriter(Path.testResultsDirPath)

  "PortfoliosAnalyzer" should {
    "get the minimum stdev portfolio for each return level" >> {
      "for one stock" in new Context {
        val vector = randomVectorStd
        givenVectorsInFile(Seq(vector))

        portfoliosAnalyzer.analyzePortfolios(fromFileName, toFileName)

        reader.readVectorsResultFile(toFileName).toSeq must
          beEqualTo(Seq(vector))
      }

      "for two stocks with the same Er" in new Context {
        val vector1 = randomVectorStd()
        val vector2 = theSameVectorWithSmallerStdev(vector1)

        givenVectorsInFile(Seq(vector1, vector2))

        portfoliosAnalyzer.analyzePortfolios(fromFileName, toFileName)

        reader.readVectorsResultFile(toFileName).toSeq must
          beEqualTo(Seq(vector2))
      }

      "for two stocks with the same Er" in new Context {
        val vector1 = randomVectorStd()
        val vector2 = theSameVectorWithSmallerStdev(vector1)
        val vector3 = randomVectorStd()

        givenVectorsInFile(Seq(vector1, vector2, vector3))

        portfoliosAnalyzer.analyzePortfolios(fromFileName, toFileName)

        reader.readVectorsResultFile(toFileName).toSeq must
          containTheSameElementsAs(Seq(vector2, vector3))
      }
    }
  }

  trait Context extends Scope {
    val portfoliosAnalyzer = new PortfoliosAnalyzer(reader, writer)
    val irrelevantStocksNames = Seq.empty
    val irrelevantFileName = randomStr
    val fromFileName = s"$randomStr.csv"
    val toFileName = s"$randomStr.csv"
    val stockName1 = randomStr
    val stockName2 = randomStr


    def givenVectorsInFile = givenVectorsFor(fromFileName) _

    def givenVectorsFor(fileName: String)(vectors: Seq[VectorStdev]) =
      writer.writeVectors(fileName, vectors.iterator)

    def randomVectorStd() = {
      val stocksWeights1 = randomStockWeight(stockName1)
      val stocksWeights2 = randomStockWeight(stockName2)
      val Er = randomDouble
      val stdev = randomDouble
      VectorStdev(Seq(stocksWeights1, stocksWeights2), Er, stdev)
    }

    def randomStockWeight(stockName: String) = StockWeight(stockName, randomDouble)

    def theSameVectorWithSmallerStdev(vectorStdev: VectorStdev) =
      vectorStdev.copy(stdev = vectorStdev.stdev - randomDouble)
  }

  override def afterAll() {
    new Directory(new File(Path.testResultsDirPath)).deleteRecursively()
  }

}
