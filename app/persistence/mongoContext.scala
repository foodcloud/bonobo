import com.novus.salat.{TypeHintFrequency, StringTypeHintStrategy, Context}
import play.api.Play
import play.api.Play.current
import com.novus.salat.conversions.RegisterJodaTimeZoneConversionHelpers

package object persistence {
  implicit val context = {
    val context = new Context {
      val name = "global"
      override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.WhenNecessary, typeHint = "_t")
    }
    context.registerClassLoader(Play.classloader)
    context.registerCustomTransformer(MoneyTransformer)
    context.registerCustomTransformer(DateTimeTransformer)
    context.registerCustomTransformer(IntervalTransformer)
    com.mongodb.casbah.commons.conversions.scala.RegisterConversionHelpers()
    com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()
    RegisterJodaTimeZoneConversionHelpers
    context
  }
}