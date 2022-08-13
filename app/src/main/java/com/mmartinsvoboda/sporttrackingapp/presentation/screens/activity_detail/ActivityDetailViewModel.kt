package com.mmartinsvoboda.sporttrackingapp.presentation.screens.activity_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mmartinsvoboda.sporttrackingapp.domain.model.SportActivity
import com.mmartinsvoboda.sporttrackingapp.domain.repository.SportActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ActivityDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sportActivityRepository: SportActivityRepository
) : ViewModel() {

    private val _listOfActivities = MutableStateFlow<SportActivity?>(null)
    val listOfActivities: StateFlow<SportActivity?> = _listOfActivities

    init {
    }
}