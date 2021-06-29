package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import akka.compat.Future
import scala.concurrent.Future
import java.util.concurrent.Executors
import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */



object BlockPool {
  lazy val pool = ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())
}

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  lazy val repo: Repository = new MockRepo()

  def response[T](executeAt: Long, x: T) = {
    s"""{"execute_at": $executeAt,"response_at": ${System.currentTimeMillis}, "data": "${x.toString()}"}"""
  }

  def blocking() = Action { implicit request: Request[AnyContent] =>
    val current = System.currentTimeMillis()
    repo.all().fold(exc => InternalServerError(response(current, exc)), xs => Ok(response(current, xs)))
  }


  def liftBlocking() = Action.async { implicit request: Request[AnyContent] =>
    val current = System.currentTimeMillis()
    Future {
      repo.all().fold(exc => InternalServerError(response(current, exc)), xs => Ok(response(current, xs)))
    }(BlockPool.pool)
  }

  def normal() = Action { implicit request: Request[AnyContent] =>
    val current = System.currentTimeMillis()
    Ok(response(current, "dummy"))
  }
}
