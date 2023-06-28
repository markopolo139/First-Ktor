package mskb.first.web.models

import kotlinx.serialization.Serializable

@Serializable
class FireTruckParameterModel(
    val key: String, val value: String
)