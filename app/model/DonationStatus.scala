package model

object DonationStatus extends Enumeration {
  type StatusCode = Value
  val Offered, Accepted, Withdrawn, Expired = Value

}