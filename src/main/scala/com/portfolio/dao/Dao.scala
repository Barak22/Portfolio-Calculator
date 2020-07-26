package com.portfolio.dao

trait Dao {

  def getAll(): Iterator[VectorDto]

  def insert(vectors: Iterator[VectorDto]): Unit
}

case class VectorDto()
