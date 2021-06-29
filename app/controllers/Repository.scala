package controllers

import scala.util.Try
import scala.util.Success

trait Repository {
  def all(): Try[Seq[Int]]
}


final class MockRepo extends Repository {
  def all(): Try[Seq[Int]] = {
    // simulate blocking
    Thread.sleep(2000)
    Success(List.fill(100)(1))
  }
}


