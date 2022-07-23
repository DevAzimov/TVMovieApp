package com.magicapp.android_imperative.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magicapp.android_imperative.models.TVShow
import com.magicapp.android_imperative.models.TVShowDetails
import com.magicapp.android_imperative.models.TVShowPopular
import com.magicapp.android_imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val tvShowRepository: TVShowRepository) :
    ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>?>()
    val tvShowsFromDb = MutableLiveData<ArrayList<TVShow>>()
    val tvShowPopular = MutableLiveData<TVShowPopular?>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

    /**
     * Retrofit Related
     */

    fun apiTVShowPopular(page: Int) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = tvShowRepository.apiTVShowPopular(page)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful){
                    val resp = response.body()
                    tvShowPopular.postValue(resp)
                    tvShowsFromApi.postValue(resp!!.tv_shows)
                    isLoading.value = false
                }else {
                    onError("Error : ${response.message()}")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    /**
     * Room Related
     */

    fun insertTVShowToDB(tvShow: TVShow) {
        viewModelScope.launch {
            tvShowRepository.insertTVShowToDB(tvShow)
        }
    }

    fun getTvShowsFromDB(){
        viewModelScope.launch {
            tvShowsFromDb.postValue(tvShowRepository.getTVShowFromDB() as ArrayList<TVShow>)
        }
    }

}