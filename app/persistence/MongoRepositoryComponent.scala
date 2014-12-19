package persistence

import controllers.RepositoryComponent
import com.novus.salat.dao.{SalatDAO, ModelCompanion}
import model.{Donor, Donation}
import com.mongodb.casbah.Imports._
import se.radley.plugin.salat._
import play.api.Play.current
import com.mongodb.casbah.WriteConcern

trait MongoRepositoryComponent extends RepositoryComponent {
  override val donationsRepository = new DonationsRepositoryMongo
  override val donorRepository = new DonorRepositoryMongo

  class DonationsRepositoryMongo
    extends DonationsRepository with ModelCompanion[Donation, ObjectId] {

    val dao = new SalatDAO[Donation, ObjectId](collection = mongoCollection("donations")) {}

    override def listAll: List[Donation] = findAll().toList
    override def save(donation: Donation) = dao.save(donation, WriteConcern.Safe)

  }

  class DonorRepositoryMongo
    extends DonorRepository with ModelCompanion[Donor, ObjectId] {

    val dao = new SalatDAO[Donor, ObjectId](collection = mongoCollection("donors")) {}

    override def listAll: List[Donor] = findAll().toList
    override def save(donor: Donor) = dao.save(donor, WriteConcern.Safe)

  }
}
