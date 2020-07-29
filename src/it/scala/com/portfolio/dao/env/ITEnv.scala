package com.portfolio.dao.env

import java.sql.DriverManager

import com.portfolio.helper.TestRandomizer
import io.airlift.testing.mysql.TestingMySqlServer

object ITEnv {
  val irrelevantUser = TestRandomizer.randomStr
  val irrelevantPass = TestRandomizer.randomStr
  val dbName = "testDB"

  private val server = new TestingMySqlServer(irrelevantUser, irrelevantPass, dbName)
  private val connection = DriverManager.getConnection(server.getJdbcUrl(dbName))
  val statement = connection.createStatement()

  def start() = {

  }

  def stop() = {
    server.close()
  }
}
