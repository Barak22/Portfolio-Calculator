package com.portfolio.measure

import org.joda.time.Instant


class DurationMeasurer(/*logger: Logger*/) {
  def measure[T](methodName: String, f: => T): T = {
    val start = Instant.now()
    val res = f
    val end = Instant.now()

    val duration = end.getMillis - start.getMillis
    //    logger.log(Level.DEBUG, s"$methodName: $duration")
    println(s"$methodName: $duration")
    res
  }
}
