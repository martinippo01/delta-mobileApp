package com.example.myapplication.viewmodel

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Exercise
import com.example.myapplication.data.Routines
import com.example.myapplication.ui.components.RoutineCard
import com.example.myapplication.ui.components.SortOption
import com.example.myapplication.ui.navigation.NavBarScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class RoutinesViewModel : ViewModel() {

    private val _sortState = MutableStateFlow(SortOption.POINTS)

    private val _routinesState = MutableStateFlow(RoutinesState())
    //private val routinesState: StateFlow<RoutinesState> = _routinesState.asStateFlow()

    fun getSortState(): MutableStateFlow<SortOption> {
        return _sortState
    }

    fun setSortState(option: SortOption) {
        _sortState.value = option
        if(option == SortOption.POINTS) sortRoutinesPoints(NavBarScreen.Routines)
        if(option == SortOption.FAVOURITE) sortRoutinesFavourite(NavBarScreen.Routines)
        if(option == SortOption.DATE) sortRoutinesDate(NavBarScreen.Routines)
    }

    fun getRoutines(routineCard: RoutineCard): List<MutableStateFlow<Routines>> {
        return if(routineCard == RoutineCard.ExploreRoutine) _routinesState.value.exploreRoutines
        else _routinesState.value.userRoutines
    }

    fun routine(id: Int): Routines {
        return _routinesState.value.userRoutines.find { routine ->routine.value.id == id }!!.value
    }

    fun sortRoutinesDate(screen: NavBarScreen) {
        if(screen == NavBarScreen.Explore)
            _routinesState.value.exploreRoutines.sortBy { routine -> routine.value.id }
        else
            _routinesState.value.userRoutines = _routinesState.value.userRoutines.sortedBy { routine -> routine.value.id }
    }

    fun sortRoutinesFavourite(screen: NavBarScreen) {
        if(screen == NavBarScreen.Explore)
            _routinesState.value.exploreRoutines.sortBy { routine -> routine.value.favourite}
        else
            _routinesState.value.userRoutines = _routinesState.value.userRoutines.sortedBy { routine -> routine.value.favourite }
    }

    fun sortRoutinesPoints(screen: NavBarScreen) {
        if(screen == NavBarScreen.Explore)
            _routinesState.value.exploreRoutines.sortBy { routine -> routine.value.points}
        else
            _routinesState.value.userRoutines =  _routinesState.value.userRoutines.sortedBy { routine -> routine.value.points}
    }

    fun clickedIcon(id: Int, routineCard: RoutineCard) {
        if(RoutineCard.ExploreRoutine == routineCard) {
           val routine = _routinesState.value.exploreRoutines.find { routine ->routine.value.id == id }!!
           routine.update { it.copy(added = !it.added) }
            _routinesState.value.userRoutines =  _routinesState.value.userRoutines + routine
        }else {
            val routine = _routinesState.value.userRoutines.find { routine ->routine.value.id == id }!!
            routine.update { it.copy(favourite = !it.favourite) }
        }

    }

    fun isSelected(id: Int, routineCard: RoutineCard): Boolean {
        return if(RoutineCard.ExploreRoutine == routineCard)
            _routinesState.value.exploreRoutines.find { routine ->routine.value.id == id }!!.value.added
        else
            _routinesState.value.userRoutines.find { routine ->routine.value.id == id }!!.value.favourite
    }


    private var screenWidth: WindowWidthSizeClass = WindowWidthSizeClass.Compact

    fun cardsExpandable(): Boolean {
        return screenWidth != WindowWidthSizeClass.Expanded
    }

    fun setWidth(width: WindowWidthSizeClass) {
        screenWidth = width;
    }


    fun getRoutineWarmUpExercises(id:Int) : List<Exercise>{
        return   _routinesState.value.userRoutines.find { routine ->routine.value.id == id }!!.value.exercises.warmUpExercises
    }
    fun getRoutineCoolDownExercises(id:Int) : List<Exercise>{
        return   _routinesState.value.userRoutines.find { routine ->routine.value.id == id }!!.value.exercises.coolDownExercises
    }
    fun getRoutineMainSetExercises(id:Int) : List<Exercise>{
        return   _routinesState.value.userRoutines.find { routine ->routine.value.id == id }!!.value.exercises.mainSetExercises
    }
}