package com.example.androidmvvmsample

import android.database.Observable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvmsample.data.model.Post
import com.example.androidmvvmsample.ui.theme.AndroidMVVMSampleTheme
import com.example.androidmvvmsample.viewModel.PostViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidMVVMSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ObservePostsViewModel()
                }
            }
        }
    }


    @Composable
    private fun ObservePostsViewModel() {
        var postList by remember {
            mutableStateOf(emptyList<Post>())
        }


        Column {
            PostView(postList = postList)
        }

        LaunchedEffect(key1 = Unit) {
            val viewModel = ViewModelProvider(this@MainActivity).get(PostViewModel::class.java)
            viewModel.getAllPostsRequest()
            viewModel.postList.observe(this@MainActivity) { posts ->
                // show data
                postList = posts
                println()
            }

            viewModel.postListError.observe(this@MainActivity) { isError ->
                isError?.let {
                    println(isError)
                }
            }
            viewModel.loading.observe(this@MainActivity) { loading ->
                println("loading : $loading")
            }
        }
    }
}

@Composable
fun PostView(postList: List<Post>) {
    LazyColumn() {
        items(postList) { post ->
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .background(androidx.compose.ui.graphics.Color.Gray)
            ) {
                Text(
                    text = "${post.id} : ${post.title}",
                    color = androidx.compose.ui.graphics.Color.Red
                )
                Text(text = post.body, color = androidx.compose.ui.graphics.Color.White)
            }
        }
    }
}

