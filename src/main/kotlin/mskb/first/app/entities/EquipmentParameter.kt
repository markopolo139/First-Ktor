package mskb.first.app.entities

import mskb.first.app.entities.abstracts.Parameter

class EquipmentParameter(
    override val key: String, override val value: String
): Parameter(key, value)