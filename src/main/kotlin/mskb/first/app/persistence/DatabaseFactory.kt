package mskb.first.app.persistence

import kotlinx.coroutines.Dispatchers
import mskb.first.app.persistence.schema.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val driverClassName = "org.mariadb.jdbc.Driver"
        val jdbcUrl = "jdbc:mariadb://localhost:3306/First_Ktor"
        val user = "spring"
        val password = "123"
        val db = Database.connect(jdbcUrl, driverClassName, user, password)

        TransactionManager.defaultDatabase = db

        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                MemberTable, TrainingTable, EquipmentTable, EquipmentParameterTable, FireTruckTable, FireTruckParameterTable,
                FireTruckEquipmentTable, CalloutTable, SectionTable, SectionMemberTable
            )
            commit()
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}