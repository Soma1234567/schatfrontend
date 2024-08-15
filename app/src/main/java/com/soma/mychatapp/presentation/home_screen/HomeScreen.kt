package com.soma.mychatapp.presentation.home_screen

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.soma.mychatapp.navigation.Screen
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun HomeScreen(navController: NavHostController,viewModel: HomeViewModel = getViewModel()){
    // it is for more than one screen when i implemented groups i use this

    //    var selectedindex = remember {
//        mutableStateOf(0)
//    }
//    var pagerstate = rememberPagerState {
//        4
//    }
//    LaunchedEffect(key1 = selectedindex.value){
//        pagerstate.animateScrollToPage(selectedindex.value)
//    }
//    LaunchedEffect(key1 = pagerstate.currentPage){
//        selectedindex.value = pagerstate.currentPage
//    }
    // A surface container using the 'background' color from the theme
    val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val lifecycleowner = LocalLifecycleOwner.current
    val avatar by viewModel.avatar.collectAsState()
    DisposableEffect(key1 = lifecycleowner){
        val observer = LifecycleEventObserver{_,event->
        if(event==Lifecycle.Event.ON_RESUME){
                permissionState.launchPermissionRequest()
        }
        }
        lifecycleowner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleowner.lifecycle.removeObserver(observer)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),

        ) {
        TopAppBar(title = {
            Text(
                text = "SChat",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xBA50C878)
            ),
            actions = {
                Image(painter = painterResource(id = images[avatar]), contentDescription =null, modifier = Modifier
                    .padding(end = 10.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(Screen.ProfileScreen.route)
                    })
            })


        ChatsScreen(navController = navController)
//        TabRow(selectedTabIndex = selectedindex.value, containerColor = Color(0xBA50C878)) {
//            Tab(selected = selectedindex.value == 0, onClick = {
//                selectedindex.value = 0
//            },
//                text = {
//                    Row(
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(text = "Chats", fontSize = 17.sp,fontWeight = if(selectedindex.value==0) FontWeight.Bold else FontWeight.Medium, color =  if(selectedindex.value==0) Color.White else Color.Black)
//                        Spacer(modifier = Modifier.width(3.dp))
//
//                    }
//                    
//                })
//            Tab(selected = selectedindex.value == 1, onClick = {
//                selectedindex.value = 1
//            },
//                text = {
//                    Text(text = "Groups", fontSize = 17.sp,fontWeight = if(selectedindex.value==1) FontWeight.Bold else FontWeight.Medium, color =  if(selectedindex.value==1) Color.White else Color.Black)
//                })
//            Tab(selected = selectedindex.value == 2, onClick = {
//                selectedindex.value = 2
//            },){
//                Text(text = "Channels",maxLines = 1,fontWeight = if(selectedindex.value==2) FontWeight.Bold else FontWeight.Medium, fontSize = 17.sp, color =  if(selectedindex.value==2) Color.White else Color.Black)
//            }
//            Tab(selected = selectedindex.value == 1, onClick = {
//                selectedindex.value = 3
//            },
//                text = {
//                    Text(text = "Updates", fontSize = 17.sp,fontWeight = if(selectedindex.value==3) FontWeight.Bold else FontWeight.Medium, color =  if(selectedindex.value==3) Color.White else Color.Black)
//                })
//
//        }
//        HorizontalPager(
//            state = pagerstate,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ) {
//            if(it==0){
//                ChatsScreen(navController)
//            }
//            else if (it==1){
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//                    Text(text = "Groups")
//                }
//            }
//            else if(it==2){
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//                    Text(text = "Channels")
//                }
//            }
//            else{
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//                    Text(text = "Updates")
//                }
//            }
//
//        }
    }
}

