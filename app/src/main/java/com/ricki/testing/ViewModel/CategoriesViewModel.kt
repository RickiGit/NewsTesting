package com.ricki.testing.ViewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ricki.testing.Model.CategoriesModel

class CategoriesViewModel(application: Application) : BaseViewModel(application) {

    private val _listOfCategories = MutableLiveData<ArrayList<CategoriesModel>>()
    val listOfCategories : LiveData<ArrayList<CategoriesModel>>
        get() = _listOfCategories

    init {
        var listOfItem : ArrayList<CategoriesModel> = ArrayList()
        listOfItem.add(CategoriesModel(1, "business"))
        listOfItem.add(CategoriesModel(2, "entertainment"))
        listOfItem.add(CategoriesModel(3, "general"))
        listOfItem.add(CategoriesModel(4, "health"))
        listOfItem.add(CategoriesModel(5, "science"))
        listOfItem.add(CategoriesModel(6, "sports"))
        listOfItem.add(CategoriesModel(7, "technology"))
        _listOfCategories.value = listOfItem
    }
}