package db

import domain.ProductSample
import slick.ast.ColumnOption.{AutoInc, PrimaryKey}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.util.Try

object DatabaseConf {

  val db = Database.forConfig("somedb")

  class ProductSampleTable(tag: Tag) extends Table[ProductSample](tag, "productsample"){
    def id = column[Long]("id", PrimaryKey, AutoInc)
    def name = column[String]("name")
    def price = column[Double]("price")
    override def * = (id, name, price).mapTo[ProductSample]
  }

  val productTable = TableQuery[ProductSampleTable]

  val createTable = productTable.schema.create

  val doCreateTable = db.run(createTable)

  def exec[T](action: DBIO[T]): T =
    Await.result(db.run(action), 4.seconds)

  def execHelper[T](action: DBIO[T]): T =
    Await.result(db.run(action), 2.second)

  def findById(id: Long) = execHelper{
    productTable.filter(_.id === id).result
  }

  def removeById(id: Long) = execHelper {
    val del = productTable filter { prod =>
      prod.id === id
    }
    val action = del.delete
    action
  }

  def getAll = execHelper{
    productTable.result
   }

  def insertProduct(product: ProductSample): Option[Try[Int]] = {
    val insAction = productTable += product
    Future(exec(insAction)).value
  }


}
