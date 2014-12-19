package persistence

import org.joda.money.{Money, CurrencyUnit}
import org.joda.time.{Interval, LocalDate}

import com.mongodb.casbah.commons.Imports._
import com.novus.salat.transformers.CustomTransformer

object DateTimeTransformer extends CustomTransformer[LocalDate, DBObject] {
  override def deserialize(bson: DBObject) = LocalDate.parse(bson.getAsOrElse[String]("date", ""))
  override def serialize(date: LocalDate) = MongoDBObject("date" -> date.toString)
}

object IntervalTransformer extends CustomTransformer[Interval, DBObject] {
  override def deserialize(bson: DBObject) = Interval.parse(bson.getAsOrElse[String]("interval",""))
  override def serialize(interval: Interval) = MongoDBObject("interval" -> interval.toString)
}