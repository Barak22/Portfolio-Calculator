package com.portfolio.service

import akka.http.scaladsl.testkit.Specs2RouteTest
import com.portfolio.app.MyServer
import org.specs2.mutable.Specification
import org.specs2.specification.BeforeAll

class FetchDataServiceIT extends Specification with Specs2RouteTest with BeforeAll {

  def beforeAll() = {
    MyServer.server.start()
  }

  val baseUrl = "http://localhost:3000"
  "FetchDataService" should {
    "ok" in { // TODO: Maybe need to write an IT test here.
      //      val req = Post(s"$baseUrl/download-stocks")
      //
      //      val res = Await.result(Http().singleRequest(req), 1.second)
      //
      //      val body = Await.result(Unmarshal(res.entity.httpEntity).to[String], 1.second)
      //      body must beEqualTo("Hello World!")

      ok
    }
  }

}
