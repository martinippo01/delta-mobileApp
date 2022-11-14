package com.example.myapplication.ui.activities.secondactivity

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.navigation.BottomBar
import com.example.myapplication.ui.navigation.NavGraph
import com.example.myapplication.ui.activities.secondactivity.RoutinesViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.activities.mainactivity.UserViewModel
import com.example.myapplication.ui.components.DrawerContent
import com.example.myapplication.ui.navigation.SideBar
import com.example.myapplication.util.getViewModelFactory


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DeltaApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: RoutinesViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    executeRedirect: (Int) -> Unit,
    logoutRedirect: () -> Unit,
    userViewModel: UserViewModel = viewModel(factory = getViewModelFactory())
) {
    viewModel.setWidth(windowSize)

    // Variables for drawer
    val scaffoldState = rememberScaffoldState()

    if (WindowWidthSizeClass.Compact == windowSize) {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                BottomBar(navController = navController)
            },
            drawerContent = {
                DrawerContent(
                    userViewModel,
                    logoutRedirect = { logoutRedirect(); userViewModel.logout() })
            },
            scaffoldState = scaffoldState
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = it.calculateBottomPadding(),
                    )
            ) {
                NavGraph(
                    navController = navController,
                    viewModel = viewModel,
                    executeRedirect = executeRedirect,
                    scaffoldState = scaffoldState
                )
            }
        }
    } else {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                SideBar(navController = navController)
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = it.calculateLeftPadding(LayoutDirection.Rtl),
                    )
            ) {
                NavGraph(
                    navController = navController,
                    viewModel = viewModel,
                    scaffoldState = scaffoldState,
                    executeRedirect = executeRedirect
                )
            }
        }
    }
}

