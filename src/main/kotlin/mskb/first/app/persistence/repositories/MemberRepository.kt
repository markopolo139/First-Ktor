package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Member
import mskb.first.app.entities.Training
import mskb.first.app.entities.enums.TrainingType
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.exceptions.FeatureNotImplemented
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.MemberEntity
import mskb.first.app.persistence.schema.MemberTable
import mskb.first.app.persistence.schema.TrainingTable
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.time.LocalDate

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
    
    suspend fun filterQuery(
        idStart: Int?, idEnd: Int?, firstname: String?, lastname: String?, birthdateStart: LocalDate?, birthdateEnd: LocalDate?,
        birthplace: String?, idNumber: String?, address: String?, joiningDateStart: LocalDate?, joiningDateEnd: LocalDate?,
        role: String?, phoneNumber: String?, periodicMedicalExaminationExpiryDateStart: LocalDate?,
        periodicMedicalExaminationExpiryDateEnd: LocalDate?, isDriver: Boolean?, trainingType: TrainingType?,
        trainingDateStart: LocalDate?, trainingDateEnd: LocalDate?, trainingExpirationDateStart: LocalDate?, 
        trainingExpirationDateEnd: LocalDate?,
    ): List<MemberEntity> = dbQuery {
        val query = Op.build {
            (if (idStart != null) MemberTable.id greaterEq idStart else MemberTable.id greater 0) and
            (if (idEnd != null) MemberTable.id lessEq idEnd else MemberTable.id greater 0) and
            (if (firstname != null) MemberTable.firstname eq firstname else MemberTable.id greater 0) and
            (if (lastname != null) MemberTable.lastname eq lastname else MemberTable.id greater 0) and
            (if (birthdateStart != null) MemberTable.birthdate greaterEq birthdateStart else MemberTable.id greater 0) and
            (if (birthdateEnd != null) MemberTable.birthdate lessEq birthdateEnd else MemberTable.id greater 0) and
            (if (birthplace != null) MemberTable.birthplace eq birthplace else MemberTable.id greater 0) and
            (if (idNumber != null) MemberTable.idNumber eq idNumber else MemberTable.id greater 0) and
            (if (address != null) MemberTable.address eq address else MemberTable.id greater 0) and
            (if (joiningDateStart != null) MemberTable.joiningDate greaterEq joiningDateStart else MemberTable.id greater 0) and
            (if (joiningDateEnd != null) MemberTable.joiningDate lessEq joiningDateEnd else MemberTable.id greater 0) and
            (if (role != null) MemberTable.role eq role else MemberTable.id greater 0) and
            (if (phoneNumber != null) MemberTable.phoneNumber eq phoneNumber else MemberTable.id greater 0) and
            (if (periodicMedicalExaminationExpiryDateStart != null) MemberTable.periodicMedicalExaminationExpiryDate greaterEq periodicMedicalExaminationExpiryDateStart else MemberTable.id greater 0) and
            (if (periodicMedicalExaminationExpiryDateEnd != null) MemberTable.periodicMedicalExaminationExpiryDate lessEq periodicMedicalExaminationExpiryDateEnd else MemberTable.id greater 0) and
            (if (isDriver != null) MemberTable.isDriver eq isDriver else MemberTable.id greater 0) and
            (if (trainingType != null) TrainingTable.type eq trainingType else MemberTable.id greater 0) and
            (if (trainingDateStart != null) TrainingTable.trainingDate greaterEq trainingDateStart else MemberTable.id greater 0) and
            (if (trainingDateEnd != null) TrainingTable.trainingDate lessEq trainingDateEnd else MemberTable.id greater 0) and
            (if (trainingExpirationDateStart != null) TrainingTable.expirationDate greaterEq trainingExpirationDateStart else MemberTable.id greater 0) and
            (if (trainingExpirationDateEnd != null) TrainingTable.expirationDate lessEq trainingExpirationDateEnd else MemberTable.id greater 0)
        }

        MemberEntity.wrapRows(
            MemberTable.innerJoin(TrainingTable).select(query)
        ).toList()
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

    suspend fun updateTraining(training: Training): Boolean = dbQuery {
        trainingRepository.update(training)
    }

    suspend fun removeTraining(id: Int, training: Training): MemberEntity = dbQuery {
        trainingRepository.delete(training.id ?: throw EntityNotFound())
        val member = MemberEntity.findById(id)?.load(MemberEntity::trainings) ?: throw EntityNotFound()
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

    override suspend fun delete(id: Int): Boolean = throw FeatureNotImplemented()

    suspend fun archive(id: Int): Boolean = dbQuery {
        MemberEntity.findById(id)?.apply { archived = true }
        true
    }
}