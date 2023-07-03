package mskb.first.app.services

import mskb.first.app.entities.Member
import mskb.first.app.entities.Training
import mskb.first.app.entities.enums.TrainingType
import mskb.first.app.persistence.repositories.MemberRepository
import java.time.LocalDate

class MemberService {
    private val memberRepository = MemberRepository()

    suspend fun getAll() = memberRepository.getAll()

    suspend fun getAllWithArchived() = memberRepository.getAllWithArchived()

    suspend fun getById(id: Int) = memberRepository.getById(id)

    suspend fun filterQuery(
        idStart: Int?, idEnd: Int?, firstname: String?, lastname: String?, birthdateStart: LocalDate?, birthdateEnd: LocalDate?,
        birthplace: String?, idNumber: String?, address: String?, joiningDateStart: LocalDate?, joiningDateEnd: LocalDate?,
        role: String?, phoneNumber: String?, periodicMedicalExaminationExpiryDateStart: LocalDate?,
        periodicMedicalExaminationExpiryDateEnd: LocalDate?, isDriver: Boolean?, trainingType: TrainingType?,
        trainingDateStart: LocalDate?, trainingDateEnd: LocalDate?, trainingExpirationDateStart: LocalDate?,
        trainingExpirationDateEnd: LocalDate?
    ) = memberRepository.filterQuery(
        idStart, idEnd, firstname, lastname, birthdateStart, birthdateEnd, birthplace, idNumber, address,
        joiningDateStart, joiningDateEnd, role, phoneNumber, periodicMedicalExaminationExpiryDateStart,
        periodicMedicalExaminationExpiryDateEnd, isDriver, trainingType, trainingDateStart, trainingDateEnd,
        trainingExpirationDateStart, trainingExpirationDateEnd
    )

    suspend fun save(member: Member) = memberRepository.save(member)

    suspend fun saveAll(members: List<Member>) = memberRepository.saveAll(members)

    suspend fun saveNewTraining(id: Int, training: Training) = memberRepository.saveNewTraining(id, training)

    suspend fun updateTraining(training: Training) = memberRepository.updateTraining(training)

    suspend fun removeTraining(id: Int, training: Training) = memberRepository.removeTraining(id, training)

    suspend fun update(member: Member) = memberRepository.update(member)

    suspend fun delete(id: Int) = memberRepository.delete(id)

    suspend fun archive(id: Int) = memberRepository.archive(id)
}