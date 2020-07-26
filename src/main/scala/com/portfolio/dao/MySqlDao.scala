package com.portfolio.dao

class MySqlDao() extends Dao {
  override def getAll(): Iterator[VectorDto] = ???

  override def insert(vectors: Iterator[VectorDto]): Unit = ???
}
