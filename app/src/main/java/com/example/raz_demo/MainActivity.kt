package com.example.raz_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.raz_demo.model.User
import com.example.raz_demo.ui.theme.Raz_DemoTheme
import com.valentinilk.shimmer.shimmer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Raz_DemoTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    MyApp1()
                }
            }
        }
    }
    @Composable
    fun MyApp1(
        viewModel: UserViewModel = hiltViewModel()
    ){
        val users by viewModel.users.observeAsState(arrayListOf())
        val isLoading by viewModel.isLoading.observeAsState(false)
        MyApp(
            onAddClick = {
                viewModel.addUser()
            },
            onDeleteClick = {
                viewModel.deleteUser(it)
            },
            users = users,
            isLoading = isLoading
        )
    }
    @Composable
    fun MyApp(
        onAddClick : (() -> Unit)? = null,
        onDeleteClick : ((toDelete:User) -> Unit)? = null,
        users: List<User>,
        isLoading : Boolean
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {Text("Simple Rest + Room")},
                    actions = {
                        IconButton(onClick = {
                            onAddClick?.invoke()
                        }) {
                            Icon(Icons.Filled.Add,"Add")
                        }
                    }
                )
            }
        ) {
            LazyColumn{
                var itemCount = users.size
                if(isLoading) itemCount++

                items(count = itemCount){ index ->  
                    var auxIndex = index;
                    if(isLoading){
                        if(auxIndex == 0){
                            return@items LoadingCard()
                        }
                        auxIndex--
                    }
                    val user = users[auxIndex]
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        elevation = 1.dp,
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxSize()
                    ) {
                        Row(modifier = Modifier.padding(8.dp)) {
                            Image(
                                modifier = Modifier.size(50.dp),
                                painter = rememberImagePainter(
                                    data = user.thumbnail
                                ),
                                contentDescription = "Thumbnail",
                                contentScale = ContentScale.FillHeight
                            )
                            Spacer(modifier = Modifier.padding(10.dp))
                            Column(Modifier.weight(1f)) {
                                Text("${user.name} ${user.lastName}")
                                Text(user.city)
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            IconButton(onClick = { onDeleteClick?.invoke(user)}) {
                                Icon(Icons.Filled.Delete,"remove")
                            }
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun LoadingCard(){
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = 1.dp,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxSize()
                .testTag("loadingCard")
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                ImageLoading()
                Spacer(modifier = Modifier.padding(10.dp))
                Column {
                    Box(modifier = Modifier
                        .height(15.dp)
                        .fillMaxSize()
                        .background(Color.Gray)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Box(modifier = Modifier
                        .height(15.dp)
                        .fillMaxSize()
                        .background(Color.Gray)
                    )
                }
            }
        }
    }
    @Composable
    fun ImageLoading(){
        Box(modifier = Modifier.shimmer()){
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
            )
        }
    }
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview(){
        Raz_DemoTheme {
            MyApp(onAddClick = null, users = listOf(), isLoading = true
            )
        }
    }
}