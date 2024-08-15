package com.soma.mychatapp.presentation.serachuser_screen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.soma.mychatapp.data.remote.requests.SearchUser
import com.soma.mychatapp.navigation.Screen
import com.soma.mychatapp.presentation.home_screen.images
import com.soma.schat.R
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController,viewModel: SearchViewModel = getViewModel()){
    val query = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    if(state.loadingState){
        Dialog(onDismissRequest = {}) {
            Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center){
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(value = query.value, onValueChange = {
            query.value = it
        },
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 8.dp)
                .fillMaxWidth(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            placeholder = {
                Text(text = "Search Here..")
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    viewModel.find(context, query.value)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
        if (query.value.isEmpty()){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.friends), contentDescription = null, modifier = Modifier.size(80.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Find friends by username", fontSize = 20.sp, fontFamily = FontFamily(
                    Font(R.font.poetsen)
                ))
            }
        }
        else if(state.size==0){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.notfound), contentDescription = null, modifier = Modifier.size(80.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "No such users found", fontSize = 20.sp, fontFamily = FontFamily(
                    Font(R.font.poetsen)
                ))
            }
        }
        else{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                for (i in 0 until state.size){
                    SearchItem(user = state.users[i]) {
                        if(state.users[i].username==viewModel.username.value){
                            navController.navigate(Screen.ProfileScreen.route)
                        }
                        else{
                            navController.navigate(Screen.ChatScreen.passusername(state.users.get(i).username,state.users.get(i).avatar))

                        }
                    }
                }
            }
        }

    }
}

@Composable
fun SearchItem(user:SearchUser,onclick:()->Unit){
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                       onclick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = images[user.avatar]), contentDescription = null, modifier = Modifier
            .size(45.dp)
            .clip(RoundedCornerShape(50.dp)))
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = user.username, fontSize = 19.sp, color = Color.Black)
    }
}