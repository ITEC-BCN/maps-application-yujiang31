package com.example.mapsapp.ui.screens

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color



@Composable
fun CreateMarkerScreen(onClick: () -> Unit){

    val imagen: Painter = painterResource(id = R.drawable.camera_icon)
    var texto by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxSize()){

        Text(
            "Title",
            fontSize = 24.sp

        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = texto,
            onValueChange = {texto = it},
            label = { Text("") }
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "Descripcion",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = texto,
            onValueChange = {texto = it},
            label = { Text("") }
        )

        Image(
            painter = imagen,
            contentDescription = "Camera Icon",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    onClick()

                }

        )

        Button(
            onClick ={

            },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Blue
            )
        ){

            Text(
                "ADD",
                fontSize = 20.sp,
                color = Color.White,
            )



        }






    }


}