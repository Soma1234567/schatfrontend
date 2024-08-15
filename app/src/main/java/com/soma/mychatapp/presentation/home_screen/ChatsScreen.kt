package com.soma.mychatapp.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.soma.mychatapp.data.local.AlreadyUsers
import com.soma.mychatapp.data.local.SingleMessage
import com.soma.mychatapp.navigation.Screen
import com.soma.mychatapp.util.Status
import com.soma.schat.R
import org.koin.androidx.compose.getViewModel

var images = listOf(
    R.drawable.female1,
    R.drawable.male1,
    R.drawable.female2,
    R.drawable.male2,
    R.drawable.female4,
    R.drawable.male3,
    R.drawable.female3,
    R.drawable.male4
)
@Composable
fun ChatsScreen(navController:NavHostController,chatsViewModel: ChatsViewModel = getViewModel()){
    var query = remember {
        mutableStateOf("")
    }
    var users:List<SingleMessage>
    val allstate by  chatsViewModel.alreadyusersstate.collectAsState()
    if(query.value.isBlank()){
        users = allstate.reversed()
    }
    else{
        users = chatsViewModel.newalreadystate.collectAsState().value
    }
    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            FloatingButton{
                navController.navigate(Screen.SearchScreen.route)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = Color.White)
        ) {
            OutlinedTextField(value = query.value, onValueChange = {
               query.value = it
                chatsViewModel.search(context,query.value)
            },
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .imePadding(),
                placeholder = {
                    Text(text = "Search")
                },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                singleLine = true,
                maxLines = 1,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Black)
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp)
            ){
                for (i in 0 until users.size){
                    ChatUser(users[i], i,chatsViewModel){
                        navController.navigate(Screen.ChatScreen.passusername(users[i].whom,users[i].))
                    }
                }
            }
        }

    it.calculateBottomPadding()
    }
}

@Composable
fun ChatUser(user:SingleMessage,i:Int,viewModel: ChatsViewModel,onClick: () -> Unit){
    val username by viewModel.username.collectAsState()
    val users by viewModel.unread.collectAsState()
    Row(
        modifier = Modifier

            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Image(painter = painterResource(id = images[user.avatar.toInt()]), contentDescription = null, modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(50.dp)))
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = user.whom, fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight(600))
                Text(text = user.time, fontSize = 14.sp)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(user.status==Status.SENT_MY_MESSAGE.name){

                        Icon(painter = painterResource(id = R.drawable.check), contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Gray)


                    }
                    else if(user.status==Status.RECIEVED_MY_MESSAGE.name){
                        Icon(painter = painterResource(id = R.drawable.readicon), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Gray)

                    }
                    else if(user.status==Status.READ_MY_MESSAGE.name){
                        Icon(painter = painterResource(id = R.drawable.readicon), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Blue)

                    }
                    else if(user.status==Status.NOT_SENT.name){
                        Icon(imageVector = Icons.Filled.Schedule, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.Gray)

                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = user.message, color = Color.DarkGray, overflow = TextOverflow.Ellipsis)


                }

                if(AlreadyUsers(user.whom) in users){
                    Box(
                        modifier = Modifier
                            .size(13.dp)
                            .background(Color.Green, shape = RoundedCornerShape(50.dp)),
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingButton(onClick:()->Unit){
    Box(
        modifier = Modifier
            .size(55.dp)
            .background(color = Color(0xFF50C878), shape = RoundedCornerShape(20.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ){
        Icon(imageVector = Icons.Filled.AddBox, contentDescription = null, tint = Color.White)
    }
}