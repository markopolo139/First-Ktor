package mskb.first.app.entities

import mskb.first.app.entities.enums.TrainingType
import java.time.LocalDate

data class Training(val id: String, val type: TrainingType, val trainingDate: LocalDate, val expirationDate: LocalDate)