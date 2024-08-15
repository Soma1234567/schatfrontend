package com.soma.mychatapp.presentation.profile_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.soma.mychatapp.presentation.home_screen.images
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController,profileViewModel: ProfileViewModel = getViewModel()){
    var showsheet = remember {
        mutableStateOf(false)
    }
    val avatar by profileViewModel.avatar.collectAsState()
    var selectedprofile = remember {
        mutableStateOf(avatar)
    }
    val context = LocalContext.current
    if(profileViewModel.updateState.value){
        Dialog(onDismissRequest = {}) {
            Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }

    val state by profileViewModel.username.collectAsState()
    if(showsheet.value){
        BottomSheet( selectedprofile.value,onDismiss = {
            showsheet.value = false
        },
            onchange = {
                profileViewModel.updateAvatar(context,selectedprofile.value)
            },
            onclick = {
                selectedprofile.value = it
            })
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(title = {
            Text(
                text = "Profile",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xBA50C878)
            ))


        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .width(100.dp)
                    .align(Alignment.TopCenter)
                    .clickable {
                        showsheet.value = true
                    }
            ) {
                Image(
                    painter = painterResource(id = images[avatar]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50.dp))
                )
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .align(
                            Alignment.BottomEnd
                        )
                        .size(32.dp)
                )
            }
        }
        Text(text = state, fontSize = 22.sp, modifier = Modifier.padding(top = 15.dp), fontWeight = FontWeight.Bold)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(selectedprofile:Int,onDismiss: () -> Unit,onchange:()->Unit,onclick:(ind:Int)->Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for (i in 0..3){
                SingleImage(i,selectedprofile==i){
                    onclick(i)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for (i in 4..7){
                SingleImage(i,selectedprofile==i){
                    onclick(i)
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 55.dp)
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color(0xFF7CB9E8), shape = RoundedCornerShape(10.dp))
                .clickable {
                    onDismiss()
                    onchange()

                }
            , contentAlignment = Alignment.Center
        ){
            Text(text = "Change Avatar", color = Color.White, fontSize = 20.sp)
        }
    }
}

@Composable
fun SingleImage(index:Int,isselected:Boolean,onclick:()->Unit){
    Box(
        modifier = Modifier
            .width(53.dp)
            .height(53.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = if (isselected) Color.Black else Color.White.copy(alpha = 0.6f))
            .clickable {
                onclick()
            },
        contentAlignment = Alignment.Center
    ){
        Image(painter = painterResource(images.get(index)), contentDescription = null, modifier = Modifier
            .width(48.dp)
            .height(48.dp)
            .clip(
                RoundedCornerShape(10.dp)
            ))
    }
}