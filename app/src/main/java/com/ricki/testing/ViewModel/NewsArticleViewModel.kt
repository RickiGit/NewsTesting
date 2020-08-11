package com.ricki.testing.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ricki.testing.Interface.APIServiceInterface
import com.ricki.testing.Model.NewsArticleItemModel
import com.ricki.testing.Model.NewsArticleModel
import com.ricki.testing.Model.NewsSourcesItemModel
import kotlinx.coroutines.launch

class NewsArticleViewModel(application: Application) : BaseViewModel(application) {
    private val _listOfNewsArticle = MutableLiveData<ArrayList<NewsArticleItemModel>>()
    val listOfNewsArticle : LiveData<ArrayList<NewsArticleItemModel>>
        get() = _listOfNewsArticle

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean>
        get() = _isLoading

    private val _status = MutableLiveData<String>()
    val status : LiveData<String>
        get() = _status

    fun fetchNewsArticle(sources : String, search : String, pageSize : Int, page : Int) {
        launch {
            try{
                _isLoading.value = true
                val result = APIServiceInterface.APIAllServices.retrofitService.getArticle(sources, search, pageSize, page)
                if(result.status.equals("ok") && result.totalResults > 0){
                    _listOfNewsArticle.value = result.articles
                    _isLoading.value = false
                    _status.value = result.status
                }else{
                    _isLoading.value = false
                    _status.value = "failed"
                }
            }
            catch(t : Throwable){
                _isLoading.value = false
                _status.value = "failed"
            }
        }
    }
}