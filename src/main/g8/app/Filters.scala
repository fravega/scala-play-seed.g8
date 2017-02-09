import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

import javax.inject.Inject

import scala.concurrent.Future
import scala.util._

import play.api._
import play.api.http._
import play.api.http.DefaultHttpFilters
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.filters.gzip.GzipFilter

import akka.stream.Materializer

import com.google.common.base.Stopwatch

class LoggingFilter(val mat: Materializer) extends Filter {

  val logger = Logger(this.getClass)

  def apply(next: (RequestHeader) => Future[Result])(request: RequestHeader): Future[Result] = {
    val stopwatch = Stopwatch.createStarted();

    def logginMessage(): String =
      s"method=${request.method} uri=${request.uri} remote-address=${request.remoteAddress} took=${stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)}ms"

    next(request).andThen {
      case Success(result) if result.header.status >= 500 =>
        getBody(result).foreach { body =>
          logger.error(s"${logginMessage()} returned=${result.header.status} body=$body")
        }
      case Success(result) =>
        logger.trace(s"${logginMessage()} returned=${result.header.status}")
      case Failure(ex) =>
        logger.error(logginMessage(), ex)
    }
  }

  def getBody(result: Result): Future[String] = result.body match {
    case HttpEntity.Strict(data, _) =>
      Future.successful(data.decodeString(Charset.defaultCharset()))

    case entity: HttpEntity.Streamed =>
      entity.consumeData(mat).map { data =>
        data.decodeString(Charset.defaultCharset())
      }

    case other =>
      Future.successful(s"Cannot read body of $other")
  }

}

class Filters @Inject() (
  gzip: GzipFilter,
  logging: LoggingFilter) extends DefaultHttpFilters(gzip, logging)