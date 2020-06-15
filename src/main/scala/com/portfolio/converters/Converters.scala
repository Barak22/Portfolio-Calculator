package com.portfolio.converters

import org.joda.time.DateTime

object Converters {

  implicit class `String -> DateTime`(dateFormat: String) {
    def toDateTime: DateTime =
      DateTime.parse(dateFormat)
  }

}
