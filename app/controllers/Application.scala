package controllers

import model.{Donor, DonationStatus, Donation}
import play.Logger
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json.{JsObject, JsValue, Json, Writes}
import org.joda.time.{Interval, DateTime}
import persistence.MongoRepositoryComponent
import play.api.mvc._

case class DonationHolder(donation: String, location: String, collectDate: String, collectTimeStart: String, collectTimeEnd: String, weight: String) {}

trait Donations extends Controller {
  this: RepositoryComponent =>

  val donationForm = Form[DonationHolder] (
    mapping(
      "donation" -> nonEmptyText,
      "location" -> nonEmptyText,
      "collectDate" -> nonEmptyText,
      "collectTimeStart" -> nonEmptyText,
      "collectTimeEnd" -> nonEmptyText,
      "weight" -> nonEmptyText
    )(DonationHolder.apply)(DonationHolder.unapply)
  )
  val donorForm = Form[Donor] (
    mapping(
      "name" -> nonEmptyText,
      "location" -> nonEmptyText,
      "typeOfBusiness" -> nonEmptyText,
      "frequency" -> nonEmptyText,
      "collectionTime" -> nonEmptyText,
      "contactName" -> nonEmptyText,
      "contactPhone" -> nonEmptyText,
      "companyId" -> nonEmptyText
    )(Donor.apply)(Donor.unapply)
  )

  def listDonations = Action { implicit request =>
    val donations = donationsRepository.listAll
    Ok(donationsList(donations))
  }

  def listDonors = Action { implicit request =>
    val donors = donorRepository.listAll
    Ok(donorsList(donors))
  }

  def index = Action { Ok(views.html.donors.home())}

  def showDonors = Action { Ok(views.html.donorList())}

  def registerDonor = Action { Ok(views.html.donors.register("Register as Donor",donorForm))}

  def addDonor = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    val formData = request.body
    Logger.debug(s"Add Donor: $formData")
    donorRepository.save(Donor(
      formData.get("name").map(_.head).getOrElse(""),
      formData.get("location").map(_.head).getOrElse(""),
      formData.get("typeOfBusiness").map(_.head).getOrElse(""),
      formData.get("frequency").map(_.head).getOrElse(""),
      formData.get("collectionTime").map(_.head).getOrElse(""),
      formData.get("contactName").map(_.head).getOrElse(""),
      formData.get("contactPhone").map(_.head).getOrElse(""),
      formData.get("companyId").map(_.head).getOrElse("")))
    Redirect(routes.Application.index())
  }

  def addDonation = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    val formData = request.body
    donationsRepository.save(newDonation(
      formData.get("donation").map(_.head).getOrElse(""),
      formData.get("location").map(_.head).getOrElse(""),
      formData.get("collectDate").map(_.head).getOrElse(""),
      formData.get("startTime").map(_.head).getOrElse(""),
      formData.get("endTime").map(_.head).getOrElse(""),
      formData.get("weight").map(_.head).getOrElse("0.0").toDouble))
    Redirect(routes.Application.index())
  }

  def create = Action { Ok(views.html.donors.donation("New Donation", donationForm))}

  private def newDonation(name: String, location: String, collectDate: String, startTime: String, endTime: String, weight: Double) = Donation(
    posted = DateTime.now(),
    donation = name,
    location = location,
    collectDate = DateTime.parse(collectDate).toLocalDate,
    collectTime = Interval.parse(s"${collectDate}T${startTime}/${collectDate}T${endTime}"),
    weight = weight,
    status = DonationStatus.Offered,
    charity = ""
  )

  private def donationsList(donations: Seq[Donation]): JsValue = {
    val data = Json.toJson(Map("aaData" -> donations.map(Json.toJson(_))))
    Json.toJson(Map("iTotalRecords" -> donations.size, "iTotalDisplayRecords" -> donations.size))
      .as[JsObject].deepMerge(data.as[JsObject])
  }

  private def donorsList(donors: Seq[Donor]): JsValue = {
    val data = Json.toJson(Map("aaData" -> donors.map(Json.toJson(_))))
    Json.toJson(Map("iTotalRecords" -> donors.size, "iTotalDisplayRecords" -> donors.size))
      .as[JsObject].deepMerge(data.as[JsObject])
  }

  implicit val donationMessage = new Writes[Donation] {
    def writes(donation: Donation) = Json.obj(
      "0" -> donation.posted.toString("dd-MMM-yyyy"),
      "1" -> donation.donation,
      "2" -> donation.location,
      "3" -> s"""${donation.collectDate.toString("dd-MMM-yyyy")} (${donation.collectTime.getStart.toString("HH:mm")} to ${donation.collectTime.getEnd.toString("HH:mm")})""",
      "4" -> donation.weight,
      "5" -> donation.status.toString,
      "6" -> donation.charity)
  }

  implicit val donorMessage = new Writes[Donor] {
    def writes(donor: Donor) = Json.obj(
      "0" -> donor.name,
      "1" -> donor.location,
      "2" -> donor.typeOfBusiness,
      "3" -> donor.frequency,
      "4" -> donor.collectionTime,
      "5" -> donor.contactName,
      "6" -> donor.contactPhone,
      "7" -> donor.companyId)
  }
}

object Application
  extends Donations with MongoRepositoryComponent {

}