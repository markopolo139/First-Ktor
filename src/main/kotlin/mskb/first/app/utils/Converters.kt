package mskb.first.app.utils

import mskb.first.app.entities.*
import mskb.first.app.persistence.entities.*

fun FireTruckParametersEntity.toApp() = FireTruckParameter(key, value)
fun EquipmentParametersEntity.toApp() = EquipmentParameter(key, value)

fun TrainingEntity.toApp() = Training(id.value, type, trainingDate, expirationDate)
fun MemberEntity.toApp() = Member(
    id.value, firstname, lastname, birthdate, birthplace, idNumber, address, joiningDate, role, phoneNumber,
    periodicMedicalExaminationExpiryDate, isDriver, trainings.map { it.toApp() }.toMutableList()
)

fun EquipmentEntity.toApp() = Equipment(
    id.value, name, serialNumber, quantity, category, storageLocation, parameters.map { it.toApp() }.toMutableList()
)

fun FireTruckEntity.toApp() = FireTruck(
    id.value, name, image?.bytes ?: ByteArray(0), vin, productionYear, licensePlate, operationalNumber, type,
    totalWeight, horsepower, numberOfSeats, mileage, vehicleInspectionExpiryDate, insuranceExpiryDate,
    parameters.map { it.toApp() }.toMutableList(), equipment.map { it.toApp() }.toMutableList()
)

fun SectionEntity.toApp() = Section(
    fireTruck.toApp(), departureTime, returnTime, crew.map { it.toApp() }.toMutableList()
)

fun CalloutEntity.toApp() = Callout(
    id.value, alarmDate, type, location, details, sections.map { it.toApp() }.toMutableList()
)