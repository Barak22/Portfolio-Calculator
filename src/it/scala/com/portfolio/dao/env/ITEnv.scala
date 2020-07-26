package com.portfolio.dao.env

import java.sql.DriverManager

import io.airlift.testing.mysql.TestingMySqlServer

import scala.util.Random

object ITEnv {
  val stringRandomizer = Random.alphanumeric
  val irrelevantUser = stringRandomizer.take(10).toList.mkString
  val irrelevantPass = stringRandomizer.take(10).toList.mkString
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
