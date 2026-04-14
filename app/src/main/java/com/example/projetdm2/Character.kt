package com.example.projetdm2

data class Character(
    val bio: Bio,
    val chronologicalInformation: ChronologicalInformation,
    val id: Int,
    val image: String,
    val name: String,
    val personalInformation: PersonalInformation,
    val physicalDescription: PhysicalDescription,
    val politicalInformation: PoliticalInformation
)