package com.example.exampleM

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exampleM.ui.theme.ExampleMTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val allProducts by viewModel.allProducts.observeAsState(emptyList())
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var searching by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val currentList = if (searching) searchResults else allProducts

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        CustomTextField(
            title = "Product Name",
            textState = productName,
            onTextChange = { productName = it },
            keyboardType = KeyboardType.Text
        )

        CustomTextField(
            title = "Quantity",
            textState = productQuantity,
            onTextChange = { productQuantity = it },
            keyboardType = KeyboardType.Number
        )

        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Button(onClick = {
                val qty = productQuantity.toIntOrNull()
                if (qty != null && productName.isNotBlank()) {
                    viewModel.insertProduct(Product(productName = productName.trim(), quantity = qty))
                    searching = false
                    errorMessage = ""
                } else {
                    errorMessage = "상품명과 수량을 올바르게 입력하세요."
                }
            }) {
                Text("Add")
            }

            Button(onClick = {
                if (productName.isNotBlank()) {
                    viewModel.findProduct(productName.trim())
                    searching = true
                    errorMessage = ""
                } else {
                    errorMessage = "검색어를 입력하세요."
                }
            }) {
                Text("Search")
            }

            Button(onClick = {
                if (productName.isNotBlank()) {
                    viewModel.deleteProduct(productName.trim())
                    searching = false
                    errorMessage = ""
                } else {
                    errorMessage = "삭제할 상품명을 입력하세요."
                }
            }) {
                Text("Delete")
            }

            Button(onClick = {
                productName = ""
                productQuantity = ""
                searching = false
                errorMessage = ""
            }) {
                Text("Clear")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            item {
                TitleRow("ID", "Product", "Qty")
            }

            items(currentList) { product ->
                ProductRow(product.id, product.productName, product.quantity)
            }
        }
    }
}

@Composable
fun TitleRow(head1: String, head2: String, head3: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(head1, color = Color.White, modifier = Modifier.weight(0.1f))
        Text(head2, color = Color.White, modifier = Modifier.weight(0.5f))
        Text(head3, color = Color.White, modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun ProductRow(id: Int, name: String, quantity: Int) {
    Row(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        Text(id.toString(), modifier = Modifier.weight(0.1f))
        Text(name, modifier = Modifier.weight(0.5f))
        Text(quantity.toString(), modifier = Modifier.weight(0.2f))
    }
}

@Composable
fun CustomTextField(
    title: String,
    textState: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = textState,
        onValueChange = onTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        label = { Text(title) },
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
    )
}
