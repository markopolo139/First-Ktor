package mskb.first.app.persistence.repositories

import kotlinx.coroutines.runBlocking
import mskb.first.app.entities.Member
import mskb.first.app.entities.Training
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.MemberEntity
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with

class MemberRepository: CrudRepository<Member, Int, MemberEntity> {

    private val trainingRepository = TrainingRepository()

    override suspend fun getAll(): List<MemberEntity> = dbQuery {
        MemberEntity.all().filter { !it.archived }.toList()
    }

    suspend fun getAllWithArchived(): List<MemberEntity> = dbQuery {
        MemberEntity.all().toList()
    }

    override suspend fun getById(id: Int): MemberEntity = dbQuery {
        MemberEntity.findById(id) ?: throw EntityNotFound()
    }

    override suspend fun save(entity: Member): MemberEntity {
        val member = dbQuery {
            MemberEntity.new(entity.id) {
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
                archived = false
            }
        }

        trainingRepository.saveAll(entity.trainings, member)

        return dbQuery { MemberEntity[member.id] }
    }

    override suspend fun saveAll(entities: List<Member>): List<MemberEntity> = dbQuery { entities.map { save(it) } }

    suspend fun saveNewTraining(id: Int, training: Training): MemberEntity = dbQuery {
        val member = MemberEntity.findById(id) ?: throw EntityNotFound()
        trainingRepository.save(training, member)
        member
    }

    override suspend fun update(entity: Member): Boolean = dbQuery {
        MemberEntity.findById(entity.id ?: -1)?.apply {
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
        } ?: throw EntityNotFound()

        true
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        MemberEntity.findById(id)?.apply { archived = true }
        true
    }
}