package mskb.first.app.persistence.repositories

import kotlinx.coroutines.runBlocking
import mskb.first.app.entities.Member
import mskb.first.app.entities.Training
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.MemberEntity
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with

class MemberRepository: CrudRepository<Member, Int, MemberEntity> {

    private val trainingRepository = TrainingRepository()

    override suspend fun getAll(): List<MemberEntity> = dbQuery {
        MemberEntity.all().with(MemberEntity::trainings).toList()
    }

    override suspend fun getById(id: Int): MemberEntity? = dbQuery {
        MemberEntity.findById(id)?.load(MemberEntity::trainings)
    }

    override suspend fun save(entity: Member): MemberEntity = dbQuery {
        val member = MemberEntity.new(entity.id) {
            firstname = entity.firstname
            lastname = entity.lastname
            birthdate = entity.birthdate
            birthplace = entity.birthplace
            idNumber = entity.idNumber
            address = entity.address
            joiningDate = entity.joiningDate
            role = entity.role
            phoneNumber = entity.phoneNumber
            periodicMedicalExaminationExpiryDate = entity.periodicMedicalExaminationExpiryDate
            isDriver = entity.isDriver
        }

        runBlocking {
            trainingRepository.saveAll(entity.trainings, member)
        }

        MemberEntity[member.id]
    }

    override suspend fun saveAll(entities: List<Member>): List<MemberEntity> = dbQuery { entities.map { save(it) } }

    suspend fun saveNewTraining(id: Int, training: Training): MemberEntity = dbQuery {
        runBlocking {
            trainingRepository.save(training, MemberEntity[id])
        }

        MemberEntity[id]
    }

    override suspend fun update(entity: Member): Boolean = dbQuery {
        val member = MemberEntity[entity.id!!].apply {
            firstname = entity.firstname
            lastname = entity.lastname
            birthdate = entity.birthdate
            birthplace = entity.birthplace
            idNumber = entity.idNumber
            address = entity.address
            joiningDate = entity.joiningDate
            role = entity.role
            phoneNumber = entity.phoneNumber
            periodicMedicalExaminationExpiryDate = entity.periodicMedicalExaminationExpiryDate
            isDriver = entity.isDriver
        }

        true
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        MemberEntity[id].delete()
        true
    }
}