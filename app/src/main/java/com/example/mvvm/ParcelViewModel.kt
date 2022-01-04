package com.example.mvvm

import android.util.Patterns
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ParcelViewModel(private val repository: ParcelRepository) : ViewModel() {

    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<Event<String>>()

    private var isUpdateOrDelete = false
    private lateinit var parcelToUpdateOrDelete: Parcel
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate() {

        if (inputName.value == null) {
            statusMessage.value = Event("Please enter subscriber's name")
        } else if (inputEmail.value == null) {
            statusMessage.value = Event("Please enter subscriber's email")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()) {
            statusMessage.value = Event("Please enter a correct email address")
        } else {

            if (isUpdateOrDelete) {
                parcelToUpdateOrDelete.owner = inputName.value!!
                parcelToUpdateOrDelete.owner_address = inputEmail.value!!
                updateSubscriber(parcelToUpdateOrDelete)
            } else {
                val name = inputName.value!!
                val email = inputEmail.value!!
                insertParcel(Parcel(0, name, email))
                inputName.value = ""
                inputEmail.value = ""
            }
        }
    }
    private fun updateSubscriber(parcel: Parcel) = viewModelScope.launch {
        val noOfRows = repository.update(parcel)
        if (noOfRows > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }

    }

    private fun insertParcel(parcel: Parcel) = viewModelScope.launch {
        val newRowId  = repository.insert(parcel)
        if (newRowId > -1) {
            statusMessage.value = Event("Parcel Inserted Successfully $newRowId")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
    fun getSavedParcel() = liveData {
        repository.parcels.collect {
            emit(it)
        }
    }
    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Parcels Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }
    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteParcel(parcelToUpdateOrDelete)
        } else {
            clearAll()
        }
    }
    private fun deleteParcel(parcel: Parcel) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(parcel)
        if (noOfRowsDeleted > 0) {
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        } else {
            statusMessage.value = Event("Error Occurred")
        }
    }

    fun initUpdateAndDelete(parcel: Parcel) {
        inputName.value = parcel.owner
        inputEmail.value = parcel.owner_address
        isUpdateOrDelete = true
        parcelToUpdateOrDelete = parcel
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

}
