package com.example.projetdm2

import com.google.gson.*
import java.lang.reflect.Type

class Deserializer : JsonDeserializer<Character> {

    // Fonction utilitaire pour gérer les champs chaîne OU tableau
    private fun parseStringArrayOrSingle(jsonObject: JsonObject, key: String): List<String> {
        val element = jsonObject[key]
        return when {
            element == null || element.isJsonNull -> emptyList()
            element.isJsonArray -> element.asJsonArray.map { it.asString }
            element.isJsonPrimitive -> {
                val value = element.asString.trim()
                listOf(value)
            }
            else -> emptyList()
        }
    }

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Character {
        val jsonObject = json.asJsonObject

        // 🔹 Champs simples
        val id = jsonObject["id"]?.asInt ?: -1
        val image = jsonObject["image"]?.asString ?: ""
        val name = if (id == 8){ "Suki" }
            else if (id == 11){ "Azula" }
            else {jsonObject["name"]?.asString ?: ""}

        // 🔹 Désérialisation manuelle de bio (avec tolérance tableau/chaîne)
        val bio = if (jsonObject.has("bio") && !jsonObject["bio"].isJsonNull) {
            val bioObject = jsonObject["bio"].asJsonObject
            Bio(
                ages = parseStringArrayOrSingle(bioObject, "ages"),
                alternativeNames = parseStringArrayOrSingle(bioObject, "alternative_names"),
                born = if(id == 6){ "87 AG" }
                else if(id == 11){ "85 AG" }
                else {
                    bioObject["born"]?.asString ?: ""
                },
                died = parseStringArrayOrSingle(bioObject, "died"),
                ethnicity = if(id == 10 || id == 11) { "Fire Nation" }
                else{bioObject["ethnicity"]?.asString ?: ""},
                nationality = bioObject["nationality"]?.asString ?: ""
            )
        } else {
            // Valeurs par défaut si bio est absent
            Bio(
                ages = emptyList(),
                alternativeNames = emptyList(),
                born = "",
                died = emptyList(),
                ethnicity = "",
                nationality = ""
            )
        }

        val chrono = if (jsonObject.has("chronologicalInformation") && !jsonObject["chronologicalInformation"].isJsonNull) {
            val chronoObject = jsonObject["chronologicalInformation"].asJsonObject
            ChronologicalInformation(
                firstAppearance = chronoObject["firstAppearance"]?. asString?:"",
                lastAppearance = parseStringArrayOrSingle(chronoObject, "lastAppearance"),
                voicedBy = parseStringArrayOrSingle(chronoObject, "voicedBy"),
            )
        } else {
            ChronologicalInformation(
                firstAppearance = "",
                lastAppearance = emptyList(),
                voicedBy = emptyList(),
            )
        }

        val personal = if (jsonObject.has("personalInformation") && !jsonObject["personalInformation"].isJsonNull) {
            val persoObject = jsonObject["personalInformation"].asJsonObject
            PersonalInformation(
                allies = parseStringArrayOrSingle(persoObject, "allies"),
                enemies = parseStringArrayOrSingle(persoObject, "enemies"),
                fightingStyles = parseStringArrayOrSingle(persoObject, "fightingStyles"),
                loveInterest = persoObject["loveInterest"]?.asString?:persoObject["loveInterst"]?.asString?:"",
//                loveInterst = persoObject["loveInterst"]?.asString?:"",
                weaponsOfChoice = parseStringArrayOrSingle(persoObject, "weaponsOfChoice")
            )
        } else {
            PersonalInformation(
                allies = emptyList(),
                enemies = emptyList(),
                fightingStyles = emptyList(),
                loveInterest = "",
//                loveInterst = "",
                weaponsOfChoice = emptyList()
            )
        }

        val physical = if (jsonObject.has("physicalDescription") && !jsonObject["physicalDescription"].isJsonNull) {
            val physicalObject = jsonObject["physicalDescription"].asJsonObject
            PhysicalDescription(
                eyeColor = physicalObject["eyeColor"]?.asString?:"",
                gender = physicalObject["gender"]?.asString?:"",
                hairColor = physicalObject["hairColor"]?.asString?:"",
                skinColor = physicalObject["skinColor"]?.asString?:"",
            )
        } else {
            // Valeur par défaut si le champ est absent
            PhysicalDescription(
                eyeColor = "",
                hairColor = "",
                skinColor = "",
                gender = ""
            )
        }

        val political = if (jsonObject.has("politicalInformation") && !jsonObject["politicalInformation"].isJsonNull) {
            val politicalObject = jsonObject["politicalInformation"].asJsonObject
            PoliticalInformation(
                affiliations = parseStringArrayOrSingle(politicalObject, "affiliations"),
                position = parseStringArrayOrSingle(politicalObject, "position"),
                predecessor = politicalObject["predecessor"]?.asString?:"",
                profession = parseStringArrayOrSingle(politicalObject, "profession"),
                successor = politicalObject["successor"]?.asString?:""
            )
        } else {
            PoliticalInformation(
                affiliations = emptyList(),
                position = emptyList(),
                predecessor = "",
                profession = emptyList(),
                successor = ""
            )
        }

        // 🔹 Retour final du Character
        return Character(
            id = id,
            name = name,
            bio = bio,
            chronologicalInformation = chrono,
            image = image,
            personalInformation = personal,
            physicalDescription = physical,
            politicalInformation = political
        )
    }
}
