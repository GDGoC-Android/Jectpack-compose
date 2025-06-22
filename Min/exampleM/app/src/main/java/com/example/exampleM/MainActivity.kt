package com.example.exampleM

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.exampleM.ui.theme.ExampleMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemArray = try {
            resources.getStringArray(R.array.car_array)
        } catch (e: Exception) {
            arrayOf("Cadillac Eldorado", "Ford Fairlane", "Plymouth Fury") // fallback
        }

        setContent {
            ExampleMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(itemArray = itemArray)
                }
            }
        }
    }
}

@Composable
fun MainScreen(itemArray: Array<String>) {
    val context = LocalContext.current
    LazyColumn {
        items(itemArray) { model ->
            MyListItem(item = model) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun MyListItem(item: String, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            ImageLoader(item)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ImageLoader(item: String) {
    val context = LocalContext.current
    val modelName = item.substringBefore(" ").trim().ifEmpty { "default" }
    val url = "https://www.ebookfrenzy.com/book_examples/car_logos/${modelName}_logo.png"

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .error(android.R.drawable.ic_menu_report_image) // 오류 시 기본 이미지
            .placeholder(android.R.drawable.ic_menu_gallery) // 로딩 중 기본 이미지
            .build(),
        contentDescription = "Car image",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(75.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExampleMTheme {
        MainScreen(itemArray = arrayOf("Cadillac Eldorado", "Ford Fairlane", "Plymouth Fury"))
    }
}
