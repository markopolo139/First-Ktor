package mskb.first.web.models

import kotlinx.serialization.Serializable

@Serializable
class EquipmentParameterModel(
    val key: String, val value: String
)