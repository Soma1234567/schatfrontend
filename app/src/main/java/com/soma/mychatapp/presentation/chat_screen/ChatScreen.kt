package com.soma.mychatapp.presentation.chat_screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.soma.mychatapp.data.local.BlockUser
import com.soma.mychatapp.presentation.home_screen.images
import com.soma.mychatapp.util.Status
import com.soma.schat.R
import org.koin.androidx.compose.getViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ChatScreen(navController: NavHostController,username:String,avatar:Int,viewModel: ChatViewModel = getViewModel()) {
    var showmenu = remember {
        mutableStateOf(false)
    }
    var selectedmessageindex by remember {
        mutableStateOf(-1)
    }
    var clipboardManager = LocalClipboardManager.current
    val state by viewModel.state.collectAsState()
    val blocklist by viewModel.blocklist.collectAsState()
    val messages = viewModel.messages.collectAsState()
    val allmessages = messages.value.reversed()
    val context = LocalContext.current
    val lifeCycleOwner = LocalLifecycleOwner.current
    var showdialog by remember {
        mutableStateOf(false)
    }
    var clearchatdialog by remember {
        mutableStateOf(false)
    }
    var liststate = rememberLazyListState()
    if(clearchatdialog){
        ClearChatDialog(onDismiss = { clearchatdialog = false }) {
            viewModel.clearChat(username)
            clearchatdialog = false
        }
    }

    if(showdialog){
        val selectedmessage = allmessages[selectedmessageindex]
        if(selectedmessage.from==username){
            DeleteTheirMessageDialog(onDismiss = {
                                  showdialog = false
            }, ondelete = {
                          viewModel.deleteMessageForMe(selectedmessage.millis)
                            showdialog = false
                         selectedmessageindex=-1
                Toast.makeText(context,"Message deleted",Toast.LENGTH_SHORT).show()
            }, username = username)
        }
        else{
            DeleteMyMessagesDialog ({
                showdialog = false
            },
                ondeleteforme = {
                    viewModel.deleteMessageForMe(selectedmessage.millis)
                    showdialog=false
                    selectedmessageindex=-1
                    Toast.makeText(context,"Message deleted",Toast.LENGTH_SHORT).show()

                },
                ondeleteforevryone = {
                    viewModel.deleteMessageForEveryOne(context,username,selectedmessage.millis)
                    showdialog = false
                    selectedmessageindex= -1
                },
                unsend = {
                    viewModel.unsendMessage(context,username,selectedmessage.millis)
                    showdialog = false
                    selectedmessageindex= -1
                })
        }
    }
    LaunchedEffect(Unit){
        viewModel.setAvatar(avatar)
        viewModel.setUnreadToZero(username)
    }
    LaunchedEffect(messages.value.size){
        viewModel.setUnreadToZero(username)
        viewModel.loadMessages(username)
        viewModel.readTheirMessages(username,context)
    }
    DisposableEffect(key1 = lifeCycleOwner){
        val observer = LifecycleEventObserver{_,event->
            if(event==Lifecycle.Event.ON_RESUME){
                viewModel.readTheirMessages(username,context)
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF2D2BD).copy(alpha = 0.2f))
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = images[avatar]),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = username, fontSize = 20.sp, fontWeight = FontWeight.Medium)
                }
            },
            actions = {
                if(selectedmessageindex!=-1){
                    Icon(imageVector = Icons.Filled.Delete, contentDescription =null, tint = Color.White, modifier = Modifier
                        .padding(end = 15.dp, top = 12.dp)
                        .clickable {
                            showdialog = true
                        })
                    Icon(imageVector = Icons.Filled.CopyAll, contentDescription =null, tint = Color.White, modifier = Modifier
                        .padding(end = 15.dp, top = 12.dp)
                        .clickable {
                            val selectedmessage = allmessages[selectedmessageindex]
                            clipboardManager.setText(AnnotatedString(selectedmessage.message))
                            selectedmessageindex = -1
                            Toast
                                .makeText(context, "Copied", Toast.LENGTH_SHORT)
                                .show()

                        })
                    Icon(imageVector = Icons.Filled.Close, contentDescription =null, tint = Color.White, modifier = Modifier
                        .padding(end = 15.dp, top = 12.dp)
                        .clickable {
                            selectedmessageindex = -1
                        })
                }
                else{
                    Icon(imageVector = Icons.Filled.MoreVert,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 15.dp, top = 12.dp)
                            .clickable {
                                showmenu.value = !showmenu.value
                            })
                }

                DropdownMenu(
                    expanded = showmenu.value, onDismissRequest = {
                        showmenu.value = false
                    },
                    modifier = Modifier.background(Color.White)
                ) {
                    DropdownMenuItem(
                        text = { Text(text = if(blocklist.contains(BlockUser( username))) "Unblock" else "Block", fontSize = 17.sp) },
                        onClick = {
                            viewModel.blockuser(username)
                        })
                    DropdownMenuItem(
                        text = { Text(text = "Clear Chat", fontSize = 17.sp) },
                        onClick = {
                            clearchatdialog = true
                        })
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xBA50C878)
            ),
            modifier = Modifier.height(50.dp)
        )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                reverseLayout = true,
                state = liststate
            ) {
                    items(allmessages.size,
                        key = {
                            it.toString()
                        }){
                        if(allmessages[it].from==state.username){
                            MyMessage(message = allmessages[it].message, time = allmessages[it].time,allmessages[it].status,selectedmessageindex==it,{selectedmessageindex=it})
                        }
                        else{
                        MessageBox(allmessages[it].message,allmessages[it].time,selectedmessageindex==it,
                            onlclick = {
                                selectedmessageindex = it
                            })
                        }


                    }
                
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .heightIn(min = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                OutlinedTextField(value = state.message, onValueChange ={
                    viewModel.changeMessage(it)
                },
                    placeholder = {
                        Text(text = "Message")
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .weight(8f)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(color = Color(0xBA50C878), shape = RoundedCornerShape(50.dp))
                        .clickable {
                            viewModel.sendMesaage(context, username)
                        },
                    contentAlignment = Alignment.Center
                ){
                    Icon(imageVector = Icons.Filled.Send, contentDescription = null)
                }

            }



    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageBox(message:String,time:String,isselected:Boolean,onlclick:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isselected) {
                    Modifier.background(color = Color(0xFFC5DCA0))
                } else {
                    Modifier
                }
            )
    ){
        Box(
            modifier = Modifier
                .padding(start = 12.dp, top = 3.dp, bottom = 3.dp, end = 80.dp)
                .widthIn(min = 150.dp)
                .heightIn(min = 40.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .combinedClickable(
                    onLongClick = { onlclick() },
                    onClick = {},
                )
        ){
            Text(text = message, style = TextStyle(lineHeight = 20.sp), fontSize = 16.sp, modifier = Modifier.align(
                Alignment.TopStart)
                .padding(bottom = 17.dp))
                Text(text = time, color = Color.DarkGray, fontSize = 13.sp, modifier = Modifier
                    .align(
                        Alignment.BottomEnd
                    )
                    .padding(top = 10.dp))

        }
    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyMessage(message:String,time:String,status:String,isselected: Boolean,onlclick: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isselected) {
                    Modifier.background(color = Color(0xFFC5DCA0))
                } else {
                    Modifier
                }
            )
    ){
        Box(
            modifier = Modifier
                .padding(end = 12.dp, top = 3.dp, bottom = 3.dp, start = 80.dp)
                .widthIn(min = 150.dp)
                .heightIn(min = 50.dp)
                .background(
                    color = Color(0xBA50C878).copy(alpha = 0.6f),
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.CenterEnd)
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .combinedClickable(
                    onLongClick = {
                        onlclick()
                    },
                    onClick = {}
                ),
        ){
            Text(text = message, fontSize = 16.sp, style = TextStyle(lineHeight = 20.sp), modifier = Modifier.align(
                Alignment.TopStart)
                .padding(bottom = 17.dp))
            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = time, color = Color.DarkGray, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(5.dp))
                if(status==Status.SENT_MY_MESSAGE.name){
                    Icon(painter = painterResource(id = R.drawable.check), contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.White)
                }
                else if(status==Status.RECIEVED_MY_MESSAGE.name){
                    Icon(painter = painterResource(id = R.drawable.readicon), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                }
                else if(status==Status.READ_MY_MESSAGE.name){
                    Icon(painter = painterResource(id = R.drawable.readicon), contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Blue)
                }
                else if(status==Status.NOT_SENT.name){
                    Icon(imageVector = Icons.Filled.Schedule, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.DarkGray)

                }
            }


        }
    }
}

@Composable
fun DeleteTheirMessageDialog(onDismiss:()->Unit,ondelete:()->Unit,username:String){
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
     Box(modifier = Modifier
         .fillMaxWidth()
         .height(120.dp)
         .background(color = Color.White, shape = RoundedCornerShape(15.dp))
         .padding(12.dp)){
         Text(text = "Delete message from $username?", fontSize = 18.sp, modifier = Modifier.align(
             Alignment.TopStart))
         Row(
             modifier = Modifier
                 .align(Alignment.BottomEnd)
                 .padding(end = 20.dp)
         ) {
             Text(text = "Cancel", modifier = Modifier.clickable {
                 onDismiss()
             })
             Spacer(modifier = Modifier.width(20.dp))
             Text(text = "Delete", modifier = Modifier.clickable {
                 ondelete()
             })
         }
     }
    }
}
@Composable
fun DeleteMyMessagesDialog(onDismiss: () -> Unit,ondeleteforme: () -> Unit,ondeleteforevryone: () -> Unit,unsend:()->Unit){
    Dialog(onDismissRequest = {
        onDismiss()
    }) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(15.dp))
            .padding(12.dp)){
            Text(text = "Delete message?", fontSize = 17.sp, modifier = Modifier)
            Spacer(modifier = Modifier.height(15.dp))
           Column(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(end = 20.dp),
               horizontalAlignment = Alignment.End
           ) {
               Text(text = "Delete for everyone", fontSize = 15.sp, color = Color(0xFF50C878), modifier = Modifier.clickable {
                   ondeleteforevryone()
               })
               Spacer(modifier = Modifier.height(15.dp))
               Text(text = "Delete for me", fontSize = 15.sp,color = Color(0xFF50C878), modifier = Modifier.clickable {
                   ondeleteforme()
               })
               Spacer(modifier = Modifier.height(15.dp))
               Text(text = "Unsend", fontSize = 15.sp,color = Color(0xFF50C878), modifier = Modifier.clickable {
                   unsend()
               })
               Spacer(modifier = Modifier.height(15.dp))
               Text(text = "Cancel", fontSize = 15.sp,color = Color(0xFF50C878), modifier = Modifier.clickable {
                   onDismiss()
               })
           }
        }
    }
}

@Composable
fun ClearChatDialog(onDismiss: () -> Unit,onclear:()->Unit){
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                .padding(horizontal = 20.dp, vertical = 15.dp)
        ){
            Text(text = "Clear All Messages?", modifier = Modifier.align(Alignment.TopStart), fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Row(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Text(text = "NO", modifier = Modifier.clickable { 
                    onDismiss()
                }, color = Color(0xFF50C878))
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = "YES", modifier = Modifier.clickable { 
                    onclear()
                }, color = Color(0xFF50C878))
            }
        }
    }
}

