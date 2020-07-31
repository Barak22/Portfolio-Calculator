package com.portfolio.analysis

import java.io.File

import com.portfolio.domain.{ Path, StockWeight, VectorStdev }
import com.portfolio.helper.TestRandomizer.{ randomStr, _ }
import com.portfolio.io.{ CsvDataWriter, DefaultPortfoliosReader }
import org.specs2.mutable.Specification
import org.specs2.specification.{ AfterAll, Scope }

import scala.reflect.io.Directory

class PortfoliosAnalyzerTest extends Specification with AfterAll {
  private val reader = new DefaultPortfoliosReader(Path.testResultsFilesPath)
  private val writer = new CsvDataWriter(Path.testResultsFilesPath)

  "PortfoliosAnalyzer" should {
    "get the minimum stdev portfolio for each return level" >> {
      "for one stock" in new Context {
        val stockName1 = randomStr
        val stockName2 = randomStr
        val vector = randomVectorStd(stockName1, stockName2)
        givenVectorsInFile(Seq(vector))

        portfoliosAnalyzer.analyzePortfolios(Seq(stockName1, stockName2), fromFileName, toFileName)

        reader.readVectorsResultFile(Seq(stockName1, stockName2), toFileName).toSeq must
          beEqualTo(Seq(vector))
      }

      "for two stocks with the same Er" in new Context {
        val stockName1 = randomStr
        val stockName2 = randomStr
        val vector1 = randomVectorStd(stockName1, stockName2)
        val vector2 = theSameVectorWithSmallerStdev(vector1)

        givenVectorsInFile(Seq(vector1, vector2))

        portfoliosAnalyzer.analyzePortfolios(Seq(stockName1, stockName2), fromFileName, toFileName)

        reader.readVectorsResultFile(Seq(stockName1, stockName2), toFileName).toSeq must
          beEqualTo(Seq(vector2))
      }

      "for two stocks with the same Er" in new Context {
        val stockName1 = randomStr
        val stockName2 = randomStr
        val vector1 = randomVectorStd(stockName1, stockName2)
        val vector2 = theSameVectorWithSmallerStdev(vector1)
        val vector3 = randomVectorStd(stockName1, stockName2)

        givenVectorsInFile(Seq(vector1, vector2, vector3))

        portfoliosAnalyzer.analyzePortfolios(Seq(stockName1, stockName2), fromFileName, toFileName)

        reader.readVectorsResultFile(Seq(stockName1, stockName2), toFileName).toSeq must
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


    def givenVectorsInFile = givenVectorsFor(fromFileName) _

    def givenVectorsFor(fileName: String)(vectors: Seq[VectorStdev]) =
      writer.writeVectors(fileName, vectors.iterator)

    def randomVectorStd(stocksNames: String*) = {
      val stocksWeights = stocksNames.map(randomStockWeight)
      val Er = randomDouble
      val stdev = randomDouble
      VectorStdev(stocksWeights, Er, stdev)
    }

    def randomStockWeight(stockName: String) = StockWeight(stockName, randomDouble)

    def theSameVectorWithSmallerStdev(vectorStdev: VectorStdev) =
      vectorStdev.copy(stdev = vectorStdev.stdev - randomDouble)
  }

  override def afterAll() {
    new Directory(new File(Path.testResultsFilesPath)).deleteRecursively()
  }

}
