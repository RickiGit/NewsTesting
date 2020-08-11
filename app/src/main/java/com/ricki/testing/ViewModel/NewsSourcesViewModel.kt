package com.ricki.testing.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricki.testing.Interface.APIServiceInterface
import com.ricki.testing.Model.NewsSourcesItemModel
import com.ricki.testing.Model.NewsSourcesModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class NewsSourcesViewModel(application: Application) : BaseViewModel(application) {

    private val _listOfNewsSource = MutableLiveData<ArrayList<NewsSourcesItemModel>>()
    val listOfNewsSource : LiveData<ArrayList<NewsSourcesItemModel>>
        get() = _listOfNewsSource

    // For Check Request
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    fun fetchNewsSources(category: String, language: String, country: String) {
        launch {
            try{
                _isLoading.value = true
                val result = APIServiceInterface.APIAllServices.retrofitService.getNewsSources(category, language, country)
                if(result.status.equals("ok")){
                    _listOfNewsSource.value = result.sources
                    _isLoading.value = false
                }
            }
            catch(t : Throwable){
                _isLoading.value = false
            }
        }
    }
}