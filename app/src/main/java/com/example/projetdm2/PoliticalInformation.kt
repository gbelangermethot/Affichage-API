package com.example.projetdm2

data class PoliticalInformation(
    val affiliations: List<String>,
    val position: List<String>,
    val predecessor: String,
    val profession: List<String>,
    val successor: String
)