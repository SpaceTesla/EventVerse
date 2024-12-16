package com.example.eventverse

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.eventverse.ui.theme.EventVerseTheme
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ImageUploadActivity : ComponentActivity() {
    private var selectedImageUri by mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventVerseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        UploadImageButton()
                        Spacer(modifier = Modifier.height(16.dp))
                        selectedImageUri?.let { uri ->
                            val context = LocalContext.current
                            val bitmap = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                                android.graphics.BitmapFactory.decodeStream(inputStream)
                            }
                            bitmap?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxWidth().height(200.dp)
                                )
                                SaveImageButton(uri)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun UploadImageButton() {
        val context = LocalContext.current
        val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedImageUri = uri
        }

        Button(onClick = {
            getImage.launch("image/*")
        }) {
            Text(text = "Upload Image")
        }
    }

    @Composable
    fun SaveImageButton(uri: Uri) {
        val context = LocalContext.current

        Button(onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this@ImageUploadActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            } else {
                saveImageToFile(uri)
            }
        }) {
            Text(text = "Save Image")
        }
    }

    private fun saveImageToFile(uri: Uri) {
        val context = this
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val desktopPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath + "/Desktop"
        val desktopDir = File(desktopPath)
        if (!desktopDir.exists()) {
            desktopDir.mkdirs()
        }
        val file = File(desktopDir, "uploaded_image.jpg")
        val outputStream = FileOutputStream(file)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        Toast.makeText(context, "Image saved to ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }
}