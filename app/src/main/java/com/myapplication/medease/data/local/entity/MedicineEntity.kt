package com.myapplication.medease.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicine")
data class MedicineEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "doses")
    val capacity: String,

    @ColumnInfo(name = "description")
    val description: String
)

data class MedicineItems(
    val id: String,
    val name: String,
    val types: String,
    val doses: String,
    val description: String
)

val dummyMedicineItems = listOf(
    MedicineItems(
        id = "Cefixime Tryhydrate",
        name = "Cefixime Tryhydrate",
        types = "Antibiotics",
        doses = "100 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    ),
    MedicineItems(
        id = "Paracetamol",
        name = "Paracetamol",
        types = "Pain reliever",
        doses = "500 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Ibuprofen",
        name = "Ibuprofen",
        types = "Pain reliever",
        doses = "200 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Loratadine",
        name = "Loratadine",
        types = "Antihistamine",
        doses = "10 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Omeprazole",
        name = "Omeprazole",
        types = "Proton pump inhibitor",
        doses = "20 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Diazepam",
        name = "Diazepam",
        types = "Anxiolytic",
        doses = "5 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Aspirin",
        name = "Aspirin",
        types = "Antiplatelet",
        doses = "81 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Cetirizine",
        name = "Cetirizine",
        types = "Antihistamine",
        doses = "10 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Amoxicillin",
        name = "Amoxicillin",
        types = "Antibiotics",
        doses = "250 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Simvastatin",
        name = "Simvastatin",
        types = "Statins",
        doses = "20 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        id = "Metformin",
        name = "Metformin",
        types = "Antidiabetic",
        doses = "500 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
)

