package com.example.minexample2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                    MainScreen2()
                    MainBoxScreen()
                    MainCustomScreen()
                    MainCascadeScreen()
                    MainIntrinsicScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Row {
        TextCell("1", Modifier.weight(weight = 0.2f, fill = true))
        TextCell("2", Modifier.weight(weight = 0.4f, fill = true))
        TextCell("3", Modifier.weight(weight = 0.3f, fill = true))
    }
}

@Composable
fun MainScreen2() {
    Column {
        Row {
            Column {
                TextCell("1")
                TextCell("2")
                TextCell("3")
            }

            Column {
                TextCell("7")
                TextCell("8")
            }

            Column {
                TextCell("9")
                TextCell("10")
                TextCell("11")
            }
        }

        Row {
            TextCell("9")
            TextCell("10")
            TextCell("11")
        }
    }
}

@Composable
fun MainBoxScreen() {

    Box(contentAlignment = Alignment.CenterEnd,
        modifier = Modifier.size(height = 90.dp, width = 290.dp)) {
        Text("TopStart", Modifier.align(Alignment.TopStart))
        Text("TopCenter", Modifier.align(Alignment.TopCenter))
        Text("TopEnd", Modifier.align(Alignment.TopEnd))

        Text("CenterStart", Modifier.align(Alignment.CenterStart))
        Text("Center", Modifier.align(Alignment.Center))
        Text(text = "CenterEnd", Modifier.align(Alignment.CenterEnd))

        Text("BottomStart", Modifier.align(Alignment.BottomStart))
        Text("BottomCenter", Modifier.align(Alignment.BottomCenter))
        Text("BottomEnd", Modifier.align(Alignment.BottomEnd))
    }

    // Box(Modifier.size(200.dp).clip(RoundedCornerShape(30.dp)).background(Color.Blue))
}

@Composable
fun TextCell2(text: String, modifier: Modifier = Modifier,  fontSize: Int = 150 ) {

    val cellModifier = Modifier
        .padding(4.dp)
        .border(width = 5.dp, color = Color.Black)

    Surface {
        Text(
            text = text, cellModifier.then(modifier),
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TextCell(text: String, modifier: Modifier = Modifier) {

    val cellModifier = Modifier
        .padding(4.dp)
        .size(100.dp, 100.dp)
        .border(width = 4.dp, color = Color.Black)

    Text(text = text, cellModifier.then(modifier),
        fontSize = 70.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center)
}

@Composable
fun MainCustomScreen() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(120.dp, 80.dp)) {
        Column {
            ColorBox(
                Modifier.exampleLayout(0f).background(Color.Blue)
            )
            ColorBox(
                Modifier.exampleLayout(0.25f).background(Color.Green)
            )
            ColorBox(
                Modifier.exampleLayout(0.5f).background(Color.Yellow)
            )
            ColorBox(
                Modifier.exampleLayout(0.25f).background(Color.Red)
            )
            ColorBox(
                Modifier.exampleLayout(0.0f).background(Color.Magenta)
            )
        }
    }
}

fun Modifier.exampleLayout(
    fraction: Float
) = layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    val x = -(placeable.width * fraction).roundToInt()

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(x = x, y = 0)
    }

}

// Cascade Layout
@Composable
fun MainCascadeScreen() {
    Box {
        CascadeLayout(spacing = 20) {
            Box(modifier = Modifier.size(60.dp).background(Color.Blue))
            Box(modifier = Modifier.size(80.dp, 40.dp).background(Color.Red))
            Box(modifier = Modifier.size(90.dp, 100.dp).background(Color.Cyan))
            Box(modifier = Modifier.size(50.dp).background(Color.Magenta))
            Box(modifier = Modifier.size(70.dp).background(Color.Green))
        }
    }
}


// Cascade Layout
@Composable
fun CascadeLayout(
    modifier: Modifier = Modifier,
    spacing: Int = 0,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        var indent = 0

        layout(constraints.maxWidth, constraints.maxHeight) {
            var yCoord = 0

            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            placeables.forEach { placeable ->
                placeable.placeRelative(x = indent, y = yCoord)
                indent += placeable.width + spacing
                yCoord += placeable.height + spacing
            }
        }
    }
}

@Composable
fun ColorBox(modifier: Modifier) {
    Box(Modifier.padding(1.dp).size(width = 50.dp, height = 10.dp).then(modifier))
}

@Composable
fun MainIntrinsicScreen() {

    var textState by remember { mutableStateOf("") }

    val onTextChange = { text: String ->
        textState = text
    }

    Column(Modifier.width(200.dp).padding(5.dp)) {
        Column(Modifier.width(IntrinsicSize.Min)) {
            Text(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = textState
            )
            Box(Modifier.height(10.dp).fillMaxWidth().background(Color.Blue))
        }
        MyTextField(text = textState, onTextChange = onTextChange)

    }

}

@Composable
fun MyTextField(text: String, onTextChange : (String) -> Unit) {

    TextField(
        value = text,
        onValueChange = onTextChange
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    MaterialTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MaterialTheme {
        MainScreen2()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MaterialTheme {
        MainBoxScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    MaterialTheme {
        MainCustomScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    MaterialTheme {
        MainCascadeScreen()
    }
}
