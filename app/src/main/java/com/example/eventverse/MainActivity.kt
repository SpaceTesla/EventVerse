package com.example.eventverse

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventverse.ui.theme.EventVerseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EventVerseTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(PaddingValues(16.dp))
        ) {
            Greeting(name = "Android")
            Spacer(modifier = Modifier.height(16.dp))
            StartQRScannerButton()
            Spacer(modifier = Modifier.height(16.dp))
            StartImageUploadButton()
        }
    }
}

@Composable
fun StartQRScannerButton() {
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(context, QRCodeScannerActivity::class.java)
        context.startActivity(intent)
    }) {
        Text(text = "Start QR Code Scanner")
    }
}

@Composable
fun StartImageUploadButton() {
    val context = LocalContext.current
    Button(onClick = {
        val intent = Intent(context, ImageUploadActivity::class.java)
        context.startActivity(intent)
    }) {
        Text(text = "Upload Image")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EventVerseTheme {
        Greeting("Android")
    }
}