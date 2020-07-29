package com.portfolio.helper

import scala.util.Random

object TestRandomizer {
  val stringRandomizer = Random.alphanumeric

  def randomStr = stringRandomizer.take(10).toList.mkString

  def randomDouble = Random.nextDouble()
}
