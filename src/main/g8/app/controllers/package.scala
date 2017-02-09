import play.api.mvc._
import play.api.mvc.Results._
import play.api.libs.json._

import scala.concurrent._

package object controllers {

  implicit def handleValue[T](value: T)(implicit fmt: Format[T]): Result =
    Ok(Json.toJson(value))

  implicit def handleFuture[T](value: Future[T])(implicit handler: T => Result, ec: ExecutionContext): Future[Result] =
    value.map(handler).recover { case ex => InternalServerError(ex.getMessage) }

  implicit def handleOption[T](value: Option[T])(implicit handler: T => Result): Result =
    value.map(handler).getOrElse(NotFound)

}