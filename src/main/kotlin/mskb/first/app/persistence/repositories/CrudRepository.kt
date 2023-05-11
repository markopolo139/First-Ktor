package mskb.first.app.persistence.repositories

interface CrudRepository<in A, in ID, out E> {
    suspend fun getAll(): List<E>
    suspend fun getById(id: ID): E
    suspend fun save(entity: A): E
    suspend fun saveAll(entities: List<A>): List<E>
    suspend fun update(entity: A): Boolean
    suspend fun delete(id: ID): Boolean
}