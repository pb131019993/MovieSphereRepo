package com.example.moviesphere.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesphere.beans.response.Person
import com.example.moviesphere.beans.response.PersonDetails
import com.example.moviesphere.repository.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonViewModel : ViewModel() {
    private val repository = PersonRepository()

    private val _popularPeople = MutableLiveData<List<Person>>()
    val popularPeople: LiveData<List<Person>> get() = _popularPeople

    private val _searchResults = MutableLiveData<List<Person>>()
    val searchResults: LiveData<List<Person>> get() = _searchResults

    private val peopleList = mutableListOf<Person>()
    private val searchList = mutableListOf<Person>()

    private val _personDetails = MutableLiveData<PersonDetails>()
    val personDetails: LiveData<PersonDetails> get() = _personDetails

    fun loadPopularPeople(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newPeople = repository.getPopularPeople(page)
            peopleList.addAll(newPeople)
            withContext(Dispatchers.Main) {
                _popularPeople.value = peopleList
            }
        }
    }

    fun searchPeople(query: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newResults = repository.searchPeople(query, page)
            if (page == 1) searchList.clear()
            searchList.addAll(newResults)
            withContext(Dispatchers.Main) {
                _searchResults.value = searchList
            }
        }
    }

    fun loadPersonDetails(personId: Int) {
        viewModelScope.launch {
            val details = repository.getPersonDetails(personId)
            _personDetails.value = details
        }
    }
}