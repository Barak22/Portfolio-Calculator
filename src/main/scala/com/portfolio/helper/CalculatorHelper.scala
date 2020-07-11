package com.portfolio.helper

import scala.annotation.tailrec

object CalculatorHelper {
  def calculateNumberOfVectors(n: Int): Long = {
    val k = 100
    choose(n + k - 1, k)
  }

  def choose(n: Long, k: Long): Long =
    (factorial(n) / (factorial(k) * factorial(n - k))).toLong

  @tailrec
  private def factorial(n: BigDecimal, acc: BigDecimal = 1): BigDecimal =
    if (n <= 1) acc
    else factorial(n - 1, acc * n)
}
