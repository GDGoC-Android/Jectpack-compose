package com.example.exampleM

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.horizontalScroll

import kotlinx.coroutines.launch

import com.example.exampleM.ui.theme.ExampleMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    ColumnList()
    // RowList()
}

@Composable
fun RowList() {

    val scrollState = rememberScrollState()

    Row(Modifier.horizontalScroll(scrollState)) {
        repeat(50) {
            Text(" $it ",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(5.dp))
        }
    }
}

@Composable
fun ColumnList() {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    Column {

        Row {
            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollTo(0)
                }
            },
                modifier = Modifier.weight(0.5f)
                    .padding(2.dp)) {
                Text("Top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
            },
                modifier = Modifier.weight(0.5f)
                    .padding(2.dp)) {
                Text("End")
            }
        }

        Column(Modifier.verticalScroll(scrollState)) {
            repeat(500) {
                Text(
                    "List Item $it",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ExampleMTheme {
        MainScreen()
    }
}