package com.example.mapsapp.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapsapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.CameraViewModel
import com.example.mapsapp.viewmodels.MainViewModel
import java.io.File

import android.widget.Toast

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateMarkerScreen(lat: Double, lng: Double, navigateBack: () -> Unit) {

    val myViewModel = viewModel<MainViewModel>()
    val cameraViewModel = viewModel<CameraViewModel>()
    val context = LocalContext.current

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val imagen: Painter = painterResource(id = R.drawable.camera_icon)
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                val stream = context.contentResolver.openInputStream(it)
                val bmp = BitmapFactory.decodeStream(stream)
                bitmap.value = bmp
                cameraViewModel.setImage(bmp)
            }
        }

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && imageUri.value != null) {
                val stream = context.contentResolver.openInputStream(imageUri.value!!)
                val bmp = BitmapFactory.decodeStream(stream)
                bitmap.value = bmp
                cameraViewModel.setImage(bmp)
            }
        }

    fun createImageUri(): Uri? {
        val file = File.createTempFile("temp_image_", ".jpg", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    // UI
    Column(

        modifier = Modifier.fillMaxSize().padding(top = 85.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Text("Title", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(15.dp))

        TextField(value = title, onValueChange = { title = it }, label = { Text("") })
        Spacer(modifier = Modifier.height(34.dp))

        Text("Descripcion", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(15.dp))

        TextField(value = description, onValueChange = { description = it }, label = { Text("") })
        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar solo si no hay imagen aún
        if (bitmap.value == null) {
            Image(
                painter = imagen,
                contentDescription = "Camera Icon",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        showDialog = true
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { pickImageLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color.Blue
                ),
                modifier = Modifier.width(100.dp).height(40.dp)
            ) {
                Text("ADD", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        bitmap.value?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(150.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }


        Button(
            onClick = {
                myViewModel.insertNewMaps(title, description, cameraViewModel.capturedImage.value, lat, lng)

                // Limpiar campos
                title = ""
                description = ""
                bitmap.value = null
                // Set a null la imagen
                cameraViewModel.setImage(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))

                // Mostrar Toast
                Toast.makeText(context, "¡Marcador añadido correctamente!", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Cyan
            ),
            modifier = Modifier.width(100.dp).height(40.dp)
        ) {
            Text("Create")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = navigateBack,
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Blue
            ),
            modifier = Modifier.width(100.dp).height(40.dp)
        ) {
            Text("Go Back")
        }
    }

    // Dialog para tomar foto
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Abrir Camara") },
            text = { Text("¿Quieres abrir la camara para tomar una foto?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    val uri = createImageUri()
                    imageUri.value = uri
                    takePictureLauncher.launch(uri!!)
                }) {
                    Text("Tomar Foto")
                }
            }
        )
    }
}
