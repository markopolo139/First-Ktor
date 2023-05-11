package mskb.first.app.persistence.schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object SectionMemberTable: Table("member_section") {
    val member = reference("member_id", MemberTable, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val section = reference("section_id", SectionTable, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
}