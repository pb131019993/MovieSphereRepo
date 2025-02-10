package com.example.moviesphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesphere.beans.response.Person
import com.example.moviesphere.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonViewModel : ViewModel() {
    private val repository = PersonRepository()

    private val _popularPeople = MutableLiveData<List<Person>>()
    val popularPeople: LiveData<List<Person>> get() = _popularPeople

    private val peopleList = mutableListOf<Person>()

    fun loadPopularPeople(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newPeople = repository.getPopularPeople(page)
            peopleList.addAll(newPeople)
            withContext(Dispatchers.Main) {
                _popularPeople.value = peopleList
            }
        }
    }
}