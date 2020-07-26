package com.portfolio.dao

import com.portfolio.dao.env.ITEnv
import org.specs2.mutable.Specification
import org.specs2.specification.{ AfterAll, BeforeAll, Scope }

class MySqlDaoIT extends Specification with AfterAll with BeforeAll {

  override def beforeAll() = {
    ITEnv.start()
  }

  "MySqlDao" should {
    "insert and get dtos" in new Context {
      statement.execute("CREATE TABLE test_table (id bigint PRIMARY KEY)")
      statement.execute("INSERT INTO test_table (id) VALUES (123)")
      val result = statement.executeQuery("SELECT * FROM test_table")
      result.next() must beTrue
      val s = result.getLong(1)

      println(s"s = ${s}")
      s must beEqualTo(123)
    }

  }

  trait Context extends Scope {
    val statement = ITEnv.statement
  }

  override def afterAll() = {
    ITEnv.stop()
  }
}
