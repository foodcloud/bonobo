package persistence

import com.novus.salat.transformers.CustomTransformer
import org.joda.money._

import com.mongodb.casbah.commons.Imports._

object MoneyTransformer extends CustomTransformer[Money, DBObject] {
  override def deserialize(bson: DBObject) = {
    val currency = CurrencyUnit.of(bson.getAsOrElse[String]("currency", ""))
    val amount = new java.math.BigDecimal(bson.getAsOrElse[Double]("amount", 0.0))
    Money.of(currency, amount.setScale(currency.getDecimalPlaces))
  }

  override def serialize(money: Money) =
    MongoDBObject("amount" -> money.getAmount.doubleValue,
                  "currency" -> money.getCurrencyUnit.toString)
}