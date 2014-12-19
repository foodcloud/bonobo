package model

object BusinessType extends Enumeration {
  type TypeCode = Value
  val Restaurant, Cafe, Bakery, Caterer, Hotel, FruitVegShop,
      ConvenienceShop, Supermarket, NonRetail = Value
}