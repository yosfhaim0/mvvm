package com.example.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ParcelViewModelFactory(
    private val repository: ParcelRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ParcelViewModel::class.java)) {
            return ParcelViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
