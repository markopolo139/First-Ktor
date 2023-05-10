package mskb.first.app.persistence.schema

import mskb.first.app.entities.enums.TrainingType
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date

object TrainingTable: IdTable<String>("trainings") {
    override val id: Column<EntityID<String>> = varchar("id", 256).entityId()
    override val primaryKey = PrimaryKey(id)

    val type = enumerationByName("training_type", 128, TrainingType::class)
    val trainingDate = date("training_date")
    val expirationDate = date("expiration_date")
    val memberId = reference("member_id", MemberTable, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
}