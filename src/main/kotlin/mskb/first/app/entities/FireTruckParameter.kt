package mskb.first.app.entities

import mskb.first.app.entities.abstracts.Parameter

class FireTruckParameter(
    override val parentId: Int, override val key: String, override val value: String
): Parameter(parentId, key, value)