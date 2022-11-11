package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.viewmodel.RoutinesViewModel

enum class SortOption {
    FAVOURITE, DATE, POINTS;

    fun color(selected: SortOption): Color {
        return if(selected.name == this.name) Black else Gray
    }
}

@Composable
fun RoutineCardSortButton(viewModel: RoutinesViewModel) {

    val selected by viewModel.getSortState().collectAsState()

    Row (
        modifier = Modifier
            .clip(RoundedCornerShape(17.dp))
            .background(Gray)
            .padding(5.dp),
        ){
        SortButton(onClick = {viewModel.setSortState(SortOption.FAVOURITE)}, txt = SortOption.FAVOURITE.name, color = SortOption.FAVOURITE.color(selected))
        Delimiter()
        SortButton(onClick = {viewModel.setSortState(SortOption.DATE)}, txt = SortOption.DATE.name, color = SortOption.DATE.color(selected))
        Delimiter()
        SortButton(onClick = {viewModel.setSortState(SortOption.POINTS)}, txt = SortOption.POINTS.name, color = SortOption.POINTS.color(selected))
    }
}

@Composable
fun Delimiter() {
    Text(
        text = " | ",
        fontSize = 25.sp,
        color = White,
        modifier = Modifier.padding(6.dp),
    )
}

@Composable
fun SortButton(onClick: () -> Unit, txt: String, color: Color) {
    Box(
        Modifier
            .clickable(onClick = onClick)
            .clip(RoundedCornerShape(17.dp))
            .background(color)
            .padding(10.dp)
    ) {
        Text(
            text = txt,
            fontSize = 25.sp,
            color = White,
            modifier = Modifier
                .background(color),
        )
    }
}
