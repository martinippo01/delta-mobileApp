package com.example.myapplication.viewmodel

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Exercise
import com.example.myapplication.data.Routines
import com.example.myapplication.ui.components.RoutineCard
import com.example.myapplication.ui.navigation.NavBarScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoutinesViewModel : ViewModel() {

    private val _routinesState = MutableStateFlow(RoutinesState())
    private val routinesState: StateFlow<RoutinesState> = _routinesState.asStateFlow()

    fun getRoutines(routineCard: RoutineCard): MutableList<Routines> {
        return if(routineCard == RoutineCard.ExploreRoutine) routinesState.value.exploreRoutines
        else routinesState.value.userRoutines
    }

    fun routine(id: Int): Routines? {
        return _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }
    }

    fun sortRoutinesDate(screen: NavBarScreen) {
        if(screen == NavBarScreen.Explore)
            routinesState.value.exploreRoutines.sortBy { routines: Routines -> routines.id }
        else
            routinesState.value.userRoutines.sortBy { routines: Routines -> routines.id }
    }

    fun sortRoutinesFavourite(screen: NavBarScreen) {
        if(screen == NavBarScreen.Explore)
            routinesState.value.exploreRoutines.sortBy { routines: Routines -> routines.favourite}
        else
            routinesState.value.userRoutines.sortBy { routines: Routines -> routines.favourite }
    }

    fun sortRoutinesPoints(screen: NavBarScreen) {
        if(screen == NavBarScreen.Explore)
            routinesState.value.exploreRoutines.sortBy { routines: Routines -> routines.points}
        else
            routinesState.value.userRoutines.sortBy { routines: Routines -> routines.points}
    }

    fun clickedIcon(id: Int, routineCard: RoutineCard) {
        if(RoutineCard.ExploreRoutine == routineCard) {
           val routine = _routinesState.value.exploreRoutines.
            find { routine: Routines -> routine.id == id }!!
            routine.added = true
            _routinesState.value.userRoutines.add(routine)
        }else
            _routinesState.value.userRoutines
                .find { routine: Routines -> routine.id == id }!!.favourite = true
    }

    fun isSelected(id: Int, routineCard: RoutineCard): Boolean {
        return if(RoutineCard.ExploreRoutine == routineCard)
            _routinesState.value.exploreRoutines.find { routine: Routines -> routine.id == id }!!.added
        else
            _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.favourite
    }


    //Should this go userViewModel??
    private var _loggedIn  = MutableStateFlow(false)

    fun login(username: String, password: String) {
        _loggedIn.update { true } ;
    }

    fun isLoggedIn(): Boolean {
        return _loggedIn.value
    }

    private var screenWidth: WindowWidthSizeClass = WindowWidthSizeClass.Compact

    fun cardsExpandable(): Boolean {
        return screenWidth != WindowWidthSizeClass.Expanded
    }

    fun setWidth(width: WindowWidthSizeClass) {
        screenWidth = width;
    }
    private fun setNewExercise(oldExercise: MutableState<Exercise>,newExercise: Exercise){
        oldExercise.value.id = newExercise.id
        oldExercise.value.order = newExercise.order
        oldExercise.value.name = newExercise.name
        oldExercise.value.weight = newExercise.weight
        oldExercise.value.repetitions = newExercise.repetitions
        oldExercise.value.duration = newExercise.duration
        oldExercise.value.detail = newExercise.detail
    }
    fun setNextExercise(id: Int,order : Int){
      var aux = _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.warmUpExercises.find { it.order == order }
        if(aux != null)
            setNewExercise(_routinesState.value.actualExercise,aux)
      aux =    _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.coolDownExercises.find { it.order == order }
        if(aux != null)
            setNewExercise(_routinesState.value.actualExercise,aux)

       aux = _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.mainSetExercises.find { it.order == order }!!
        setNewExercise(_routinesState.value.actualExercise,aux)
    }
    fun hasNext(id: Int,order : Int): Boolean{
        var aux = _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.warmUpExercises.find { it.order == order }
        if(aux != null)
            return true
        aux =    _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.coolDownExercises.find { it.order == order }
        if(aux != null)
            return true
        aux = _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.mainSetExercises.find { it.order == order }
       if (aux != null)
           return true
       return false
    }
    fun getCurrentExercise() : MutableState<Exercise>{
        return _routinesState.value.actualExercise
    }
    fun getRoutineWarmUpExercises(id:Int) : List<Exercise>{
        return   _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.warmUpExercises
    }
    fun getRoutineCoolDownExercises(id:Int) : List<Exercise>{
        return   _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.coolDownExercises
    }
    fun getRoutineMainSetExercises(id:Int) : List<Exercise>{
        return   _routinesState.value.userRoutines.find { routine: Routines -> routine.id == id }!!.exercises.mainSetExercises
    }
//    private var _exploreRoutines = mutableListOf<MutableStateFlow<RoutinesT>>()
//
//    fun getExploreRoutines(): List<RoutinesT> {
//        return _exploreRoutines.map{it.asStateFlow().value}
//    }
//
//    fun addedRoutineFromExplore(id: Int) {
//        _exploreRoutines[id].value.added = true;
//    }
//
//    fun isAddedRoutine(id: Int): Boolean {
//        return  _exploreRoutines[id].value.added;
//    }

}