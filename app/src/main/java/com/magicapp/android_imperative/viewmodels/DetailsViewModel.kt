package com.magicapp.android_imperative.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magicapp.android_imperative.models.TVShow
import com.magicapp.android_imperative.models.TVShowDetails
import com.magicapp.android_imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) :
    ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

    fun apiTVShowDetails(show_id: Int) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = tvShowRepository.apiTVShowDetails(show_id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    tvShowDetails.postValue(response.body())
                    isLoading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }
}