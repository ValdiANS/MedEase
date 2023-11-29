package com.myapplication.medease.data.local.entity

data class MedicineEntity(
    val name: String,
)

//data dummy
data class MedicineItems(
    val name: String,
    val types: String,
    val doses: String,
    val description: String
)

val dummyMedicineItems = listOf(
    MedicineItems(
        name = "Cefixime Tryhydrate",
        types = "Antibiotics",
        doses = "100 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
    ),
    MedicineItems(
        name = "Paracetamol",
        types = "Pain reliever",
        doses = "500 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Ibuprofen",
        types = "Pain reliever",
        doses = "200 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Loratadine",
        types = "Antihistamine",
        doses = "10 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Omeprazole",
        types = "Proton pump inhibitor",
        doses = "20 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Diazepam",
        types = "Anxiolytic",
        doses = "5 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Aspirin",
        types = "Antiplatelet",
        doses = "81 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Cetirizine",
        types = "Antihistamine",
        doses = "10 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Amoxicillin",
        types = "Antibiotics",
        doses = "250 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Simvastatin",
        types = "Statins",
        doses = "20 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    ),
    MedicineItems(
        name = "Metformin",
        types = "Antidiabetic",
        doses = "500 mg",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    )
)

