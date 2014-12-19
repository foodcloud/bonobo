package controllers

import model.{Donor, Donation}
import com.mongodb.WriteResult

trait RepositoryComponent {
  val donationsRepository: DonationsRepository
  val donorRepository: DonorRepository

  trait DonationsRepository {
    def save(item: Donation): WriteResult
    def listAll: List[Donation]
  }

  trait DonorRepository {
    def save(item: Donor): WriteResult
    def listAll: List[Donor]
  }
}
