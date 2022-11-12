package com.example.myapplication.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.Routines
import com.example.myapplication.ui.theme.Green
import com.example.myapplication.viewmodel.RoutinesViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


const val ROUTINE_CARD_WIDTH = 370;

sealed class RoutineCard(val iconClicked: Int, val iconUnClicked: Int, val description: String) {
    object MyRoutine: RoutineCard ( R.drawable.star_rate_white_24dp, R.drawable.star_border_white_24dp, "Go")
    object Progress: RoutineCard ( R.drawable.star_rate_white_24dp, R.drawable.star_border_white_24dp, "See Progress")
    object ExploreRoutine: RoutineCard ( R.drawable.check_circle_white_24dp, R.drawable.add_white_24dp, "Share")
}

@Composable
fun RoutineCardDetails(description: String) {
    Text(
        text = description,
        fontSize = 25.sp,
        color = Green,
        modifier = Modifier
    )
}


/**
 * Creates an intent to share order details
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            "Share routine"
        )
    )
}

@Composable
fun RoutineCardTitle(title: String, iconId: Int, clickedIcon: () -> Unit = {}, id: Int) {

    val context = LocalContext.current

    Row ( horizontalArrangement  =  Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = title,
            fontSize = 50.sp,
            color = Color.White,
            modifier = Modifier.padding((id % 2).dp),
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier
                .scale(1.5F)
                .clickable(onClick = clickedIcon)
        )
        IconButton(onClick = { shareOrder(context, "Routine", title ) }) {
            Icon(Icons.Default.Share, contentDescription = "share icon", tint = Color.White)
        }

    }
}


@Composable
fun RoutineCard(routine: Routines, iconId: Int, clickedIcon: () -> Unit = {}, actionHandler: () -> Unit = {}, routineCard: RoutineCard, viewModel: RoutinesViewModel) {

    var expanded by remember { mutableStateOf(!viewModel.cardsExpandable()) }
    var imageHeight by remember { mutableStateOf(if(!viewModel.cardsExpandable()) 200.dp else 70.dp ) }


    Box (
        Modifier
            .width(ROUTINE_CARD_WIDTH.dp)
            .clickable {
                expanded = if (viewModel.cardsExpandable()) !expanded else true
                imageHeight = if (expanded) 200.dp else 70.dp
            },
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(routine.img),
            contentDescription = null,
            modifier = Modifier
                .width(ROUTINE_CARD_WIDTH.dp)
                .clip(RoundedCornerShape(20.dp))
                .height(imageHeight),
            contentScale = ContentScale.Crop,
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RoutineCardTitle(
                title = routine.title,
                iconId = iconId,
                clickedIcon = {clickedIcon()},
                id = routine.id
            )
            if (expanded) {
                    RoutineCardDetails(description = routine.description)
                    Button1(fontSize = 16, text = routineCard.description, handler = actionHandler)
            }
        }

    }

    Spacer(modifier = Modifier.height(30.dp))


}
