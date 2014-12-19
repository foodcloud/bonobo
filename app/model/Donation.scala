package model

import org.bson.types.ObjectId
import org.joda.time.{Interval, LocalDate, DateTime}

case class Donation(
                     id: ObjectId = new ObjectId,
                     posted: DateTime,
                     donation: String,
                     location: String,
                     collectDate: LocalDate,
                     collectTime: Interval,
                     weight: Double,
                     status: DonationStatus.Value,
                     charity: String)  {

}
