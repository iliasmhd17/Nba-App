package com.example.myesiapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AboutPageViewModel: ViewModel() {
    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex: StateFlow<Int> = _selectedIndex

    fun selectItem(index: Int) {
        viewModelScope.launch {
            _selectedIndex.value = index
        }
    }
}