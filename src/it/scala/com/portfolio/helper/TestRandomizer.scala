package com.portfolio.helper

import scala.util.Random

object TestRandomizer {
  def randomStr = Random.alphanumeric.take(10).toList.mkString

  def randomDouble = Random.nextDouble()
}
