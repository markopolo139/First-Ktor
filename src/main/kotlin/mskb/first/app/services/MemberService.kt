package mskb.first.app.services

import mskb.first.app.entities.Member
import mskb.first.app.entities.Training
import mskb.first.app.persistence.repositories.MemberRepository

class MemberService {
    private val memberRepository = MemberRepository()

    suspend fun getAll() = memberRepository.getAll()

    suspend fun getAllWithArchived() = memberRepository.getAllWithArchived()

    suspend fun getById(id: Int) = memberRepository.getById(id)

    suspend fun save(member: Member) = memberRepository.save(member)

    suspend fun saveAll(members: List<Member>) = memberRepository.saveAll(members)

    suspend fun saveNewTraining(id: Int, training: Training) = memberRepository.saveNewTraining(id, training)

    suspend fun updateTraining(training: Training) = memberRepository.updateTraining(training)

    suspend fun removeTraining(id: Int, training: Training) = memberRepository.removeTraining(id, training)

    suspend fun update(member: Member) = memberRepository.update(member)

    suspend fun delete(id: Int) = memberRepository.delete(id)

    suspend fun archive(id: Int) = memberRepository.archive(id)
}