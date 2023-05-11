package mskb.first.app.entities

import mskb.first.app.entities.abstracts.Parameter

class FireTruckParameter(
    override val key: String, override var value: String
): Parameter(key, value)