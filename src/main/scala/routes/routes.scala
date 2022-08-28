package routes

import cats._
import cats.effect._
import cats.implicits._
import org.http4s.circe._
import org.http4s.{server, _}
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.dsl._
import org.http4s.dsl.impl._
import org.http4s.headers._
import org.http4s.implicits._
import org.http4s.server._
import cats.effect.syntax._
import org.http4s.server.middleware.Logger
import org.slf4j.Logger

import scala.util.{Failure, Success}
//import io.circe.generic.auto._
import org.http4s.circe._
import cats._
import cats.effect._
import cats.implicits._
import db.DatabaseConf
import domain.ProductSample
import org.http4s._
//import io.circe.syntax._
import org.http4s.dsl.impl._
import org.http4s.implicits._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeServerBuilder

object Routes extends IOApp {

  import db.DatabaseConf._

  object IdParamQueryMatcher extends QueryParamDecoderMatcher[Long]("id")


  // GET /products  butun productlari qaytaracaq
  // GET /product/id  bit product qaytaracaq
  // POST /product  her hansi bir productu bazaya yazacaq
  // DELETE /product/id   bir productu silecek

  def routes[F[_]: Monad]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl._

    HttpRoutes.of[F] {
      case GET -> Root / "products" => Ok{
       val all = DatabaseConf.getAll.asJson
        all
    }
      case GET -> Root / "product" / id => Ok{
       val res = DatabaseConf.findById(id.toLong).asJson
        res
      }
    }
  }

  def routes1[F[_]: Monad: Concurrent]: HttpRoutes[F] = {
    val dsl = Http4sDsl[F]
    import dsl._

    implicit val entityDecoderProduct: EntityDecoder[F, ProductSample] = jsonOf[F, ProductSample]

    HttpRoutes.of[F] {
      case req @ POST -> Root / "product" =>
        req.decodeJson[ProductSample].flatMap { (reqS: ProductSample) =>
          val res = DatabaseConf.insertProduct(reqS)
          res match {
            case Some(value) =>
              value match {
                case Failure(exception) => Ok("No result")
                case Success(value) => Ok(value.asJson)
              }
            case None => Ok("No result")
          }
       }
      case DELETE -> Root / id => Ok{
       DatabaseConf.removeById(id.toLong).asJson
      }
    }
  }

  def allRoutes[F[_]: Monad: Concurrent]: HttpRoutes[F] =
    routes[F] <+> (routes1[F])

  def allRoutesCompletes[F[_]: Monad: Concurrent]: HttpApp[F] =
    allRoutes[F].orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](runtime.compute).
      withHttpApp(allRoutesCompletes).
      bindHttp(8080, "localhost").
      resource.
      use(_ => IO.never).
      as(ExitCode.Success)
}
