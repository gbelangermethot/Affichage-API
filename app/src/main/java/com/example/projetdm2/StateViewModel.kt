package com.example.projetdm2

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StateViewModel: ViewModel() {
    private val apiService = RetrofitInstance.api
    val characters: MutableState<List<Character>> = mutableStateOf(emptyList())
    var persoCourant: MutableState<Character?> = mutableStateOf(null)
//    val character: MutableState<Character?> = mutableStateOf(null)
//    var id = mutableStateOf("")
    fun getCharacters(){
        viewModelScope.launch{
            try{
                val reponse = apiService.getCharacters()
                if(reponse != null){
                    characters.value = reponse
                    Log.d("testretrofit", characters.value?.toString()?:"")
                }
            }catch(e: Exception){
                Log.d("testretrofit", "Probleme de connexion Retrofit ${e.message}", e)
            }
        }
    }

//    fun getOneCharacter(no: String){
//        viewModelScope.launch{
//            try{
//                val response = apiService.getOneCharacter(no)
//                if(response != null){
//                    character.value = response.body()
//                    Log.d("testretrofit", character.value?.name?:"")
//                }
//            }catch (e: Exception){
//                Log.d("testretrofit", "probleme de connexion retrofit")
//            }
//        }
//    }
}