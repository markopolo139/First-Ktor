package mskb.first.app.utils

import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import mskb.first.app.entities.*
import mskb.first.app.persistence.entities.*
import mskb.first.web.models.*

fun FireTruckParametersEntity.toApp() = FireTruckParameter(key, value)

fun EquipmentParametersEntity.toApp() = EquipmentParameter(key, value)

fun TrainingEntity.toApp() = Training(id.value, type, trainingDate, expirationDate)

fun MemberEntity.toApp() = Member(
    id.value, firstname, lastname, birthdate, birthplace, idNumber, address, joiningDate, role, phoneNumber,
    periodicMedicalExaminationExpiryDate, isDriver, trainings?.map { it.toApp() }?.toMutableList() ?: mutableListOf()
)

fun EquipmentEntity.toApp() = Equipment(
    id.value, name, serialNumber, quantity, category, storageLocation.name,
    parameters?.map { it.toApp() }?.toMutableList() ?: mutableListOf()
)

fun StorageLocationEntity.toApp() = StorageLocation(
    id.value, name, equipment.map { it.toApp() }, default
)

fun FireTruckEntity.toApp() = FireTruck(
    id.value, name, image?.bytes ?: ByteArray(0), vin, productionYear, licensePlate, operationalNumber, type,
    totalWeight, horsepower, numberOfSeats, mileage, vehicleInspectionExpiryDate, insuranceExpiryDate,
    parameters?.map { it.toApp() }?.toMutableList() ?: mutableListOf()
)

fun SectionEntity.toApp() = Section(
    fireTruck.toApp(), departureTime, returnTime, crew.map { it.toApp() }.toMutableList()
)

fun CalloutEntity.toApp() = Callout(
    id.value, alarmDate, type, location, details, sections?.map { it.toApp() }?.toMutableList() ?: mutableListOf()
)

fun EquipmentParameter.toModel() = EquipmentParameterModel(key, value)

fun FireTruckParameter.toModel() = FireTruckParameterModel(key, value)

fun Training.toModel() = TrainingModel(id, type, trainingDate.toKotlinLocalDate(), expirationDate.toKotlinLocalDate())

fun Member.toModel() = MemberModel(
    id, firstname, lastname, birthdate.toKotlinLocalDate(), birthplace, idNumber, address, joiningDate.toKotlinLocalDate(),
    role, phoneNumber, periodicMedicalExaminationExpiryDate.toKotlinLocalDate(), isDriver,
    trainings.map { it.toModel() }.toMutableList()
)

fun Equipment.toModel() = EquipmentModel(
    id, name, serialNumber, quantity, category, storageLocation, parameters.map { it.toModel() }.toList()
)

fun StorageLocation.toModel() = StorageLocationModel(
    id, name, assignedEquipment.map { it.toModel() }.toList(), default
)

fun FireTruck.toModel() = FireTruckModel(
    id, name, image, vin, productionYear, licensePlate, operationNumber, type, totalWeight, horsepower, numberOfSeats,
    mileage, vehicleInspectionExpiryDate.toKotlinLocalDate(), insuranceExpiryDate.toKotlinLocalDate(),
    parameters.map { it.toModel() }.toList()
)

fun Section.toModel() = SectionModel(
    fireTruck?.toModel(), departureDate.toKotlinLocalDateTime(), returnDate.toKotlinLocalDateTime(),
    crew.map { it.toModel() }.toList()
)

fun Callout.toModel() = CalloutModel(
    id, alarmDate.toKotlinLocalDateTime(), type, location, details, sections.map { it.toModel() }.toList()
)

fun EquipmentParameterModel.toApp() = EquipmentParameter(key, value)

fun FireTruckParameterModel.toApp() = FireTruckParameter(key, value)

fun TrainingModel.toApp() = Training(id, type, trainingDate.toJavaLocalDate(), expirationDate.toJavaLocalDate())

fun MemberModel.toApp() = Member(
    id, firstname, lastname, birthdate.toJavaLocalDate(), birthplace, idNumber, address, joiningDate.toJavaLocalDate(),
    role, phoneNumber, periodicMedicalExaminationExpiryDate.toJavaLocalDate(), isDriver, trainings.map { it.toApp() }.toMutableList()
)

fun EquipmentModel.toApp() = Equipment(
    id, name, serialNumber, quantity, category, storageLocation, parameters.map { it.toApp() }.toMutableList()
)

fun StorageLocationModel.toApp() = StorageLocation(
    id, name, assignedEquipment.map { it.toApp() }.toMutableList(), default
)

fun FireTruckModel.toApp() = FireTruck(
    id, name, image, vin, productionYear, licensePlate, operationNumber, type, totalWeight, horsepower, numberOfSeats,
    mileage, vehicleInspectionExpiryDate.toJavaLocalDate(), insuranceExpiryDate.toJavaLocalDate(),
    parameters.map { it.toApp() }.toMutableList()
)

fun SectionModel.toApp() = Section(
    fireTruck?.toApp(), departureDate.toJavaLocalDateTime(), returnDate.toJavaLocalDateTime(), crew.map { it.toApp() }.toMutableList()
)

fun CalloutModel.toApp() = Callout(
    id, alarmDate.toJavaLocalDateTime(), type, location, details, sections.map { it.toApp() }.toMutableList()
)