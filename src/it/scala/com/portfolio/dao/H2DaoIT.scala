package com.portfolio.dao

import java.io.File
import java.sql.{ Connection, DriverManager, Statement }

import com.portfolio.domain.{ StockWeight, VectorStdev }
import com.portfolio.helper.TestRandomizer.{ randomDouble, randomStr }
import org.specs2.mutable.Specification
import org.specs2.specification.{ AfterAll, BeforeAll }

import scala.reflect.io.Directory

class H2DaoIT extends Specification with BeforeAll with AfterAll {
  val PARENT_DIR: String = "./data-dir"
  val DATABASE_NAME: String = "my-h2-db" // it's better if you write db name in small letters
  val DATABASE_DIR: String = s"$PARENT_DIR/$DATABASE_NAME" // FYI, this is string interpolation
  val DATABASE_URL: String = s"jdbc:h2:$DATABASE_DIR"
  val con: Connection = DriverManager.getConnection(DATABASE_URL)
  val stm: Statement = con.createStatement

  override def beforeAll(): Unit = {
    stm.executeQuery("create table test_table1(ID INT PRIMARY KEY,NAME VARCHAR(500))")
  }


  "H2Dao" should {
    "insert and read simple data" in {
      //      val stockWeight1 = randomStockWeight
      //      val stockWeight2 = randomStockWeight
      //      val Er = randomDouble
      //      val stdev = randomDouble
      //      val vector = VectorStdev(Seq(stockWeight1, stockWeight2), Er, stdev)
      //
      //
      //      givenVectorsInFile(vector)
      //
      //      var row1InsertionCheck = false
      //      val sql: String =
      //        """
      //          |create table test_table1(ID INT PRIMARY KEY,NAME VARCHAR(500));
      //          |insert into test_table1 values (1,'A');""".stripMargin
      //
      //      stm.execute(sql)
      //      val rs = stm.executeQuery("select * from test_table1")
      //
      //      rs.next
      //      row1InsertionCheck = (1 == rs.getInt("ID")) && ("A" == rs.getString("NAME"))
      //
      //      row1InsertionCheck must beTrue
      ok
    }
  }

  def givenVectorsInFile(vectors: VectorStdev*) = ???

  def randomStockWeight = StockWeight(randomStr, randomDouble)

  override def afterAll() {
    new Directory(new File(PARENT_DIR)).deleteRecursively()
  }

}
