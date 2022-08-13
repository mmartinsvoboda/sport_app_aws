package com.mmartinsvoboda.sporttrackingapp.presentation.screens.activity_list_overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.mmartinsvoboda.sporttrackingapp.common.toGsonString
import com.mmartinsvoboda.sporttrackingapp.presentation.components.CardSportApp
import com.mmartinsvoboda.sporttrackingapp.presentation.components.ScaffoldSportApp
import com.mmartinsvoboda.sporttrackingapp.presentation.screens.destinations.LoginScreenDestination
import com.mmartinsvoboda.sporttrackingapp.presentation.ui.SportTrackingAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ActivityListOverviewScreen(
    navigator: DestinationsNavigator, model: ActivityListOverviewViewModel = hiltViewModel()
) {
    val state by model.state.collectAsState()

    ScaffoldSportApp(topBarTitle = "Overview",
        topBarDisplayNavigationIcon = false,
        navigator = navigator,
        topBarActions = {
            val expanded = rememberSaveable { mutableStateOf(false) }

            Box(
                Modifier.wrapContentSize(Alignment.TopEnd)
            ) {
                IconButton(onClick = { expanded.value = true }) {
                    Icon(
                        Icons.Default.MoreVert, contentDescription = null
                    )
                }

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier.clip(RoundedCornerShape(12.dp))
                ) {
                    DropdownMenuItem(onClick = {
                        expanded.value = false
                    }) {
                        Text("Settings")
                    }

                    Divider()

                    DropdownMenuItem(onClick = {
                        expanded.value = false
                        model.onEvent(ActivityListEvent.UserLogOut {
                            navigator.popBackStack()
                            navigator.navigate(LoginScreenDestination)
                        })
                    }) {
                        Text("Log out")
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Outlined.Add, "Add")
            }
        }) {
        SwipeRefresh(state = SwipeRefreshState(state.isLoading), onRefresh = {
            model.onEvent(ActivityListEvent.LoadActivityList(true))
        }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(SportTrackingAppTheme.paddings.defaultPadding)
            ) {
                item {}

                items(state.activities) {
                    CardSportApp(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SportTrackingAppTheme.paddings.defaultPadding)
                        .clickable {
                            if (it.isBackedUp) model.onEvent(
                                ActivityListEvent.ActivitySyncOff(
                                    it
                                )
                            )
                            else model.onEvent(ActivityListEvent.ActivitySyncOn(it))
                        }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(SportTrackingAppTheme.paddings.defaultPadding)
                        ) {
                            Text(text = it.toGsonString(true))
                        }
                    }
                }
            }
        }
    }


    if (state.isActionInProgress) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color(0x59000000)
                )
                .clickable() { }, contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}