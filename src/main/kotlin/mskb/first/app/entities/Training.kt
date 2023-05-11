package mskb.first.app.entities

import mskb.first.app.entities.enums.TrainingType
import java.time.LocalDate

data class Training(val id: String?, var type: TrainingType, var trainingDate: LocalDate, var expirationDate: LocalDate)