package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.MainViewModel


@Composable
fun DetailMarkerScreen(id:String, navigateBack:() -> Unit, navigateCMarker:(String)-> Unit){

    val myViewModel = viewModel<MainViewModel>()
    myViewModel.getMaps(id)
    val studentName: String by myViewModel.MapsName.observeAsState("")
    val studentMark: String by myViewModel.MapsMark.observeAsState("")

    Column(

        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {


        TextField(value = studentName, onValueChange = { myViewModel.editMapsName(it) })
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = studentMark, onValueChange = { myViewModel.editMapsMark(it) })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            myViewModel.updateMaps(id, studentName, studentMark, null)
        }) {
            Text("Update")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                navigateCMarker("coordenadas")},

            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Blue
            ),
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
        ) {

            Text(
                "Create New Marker",
                color = Color.White

            )

        }


        Spacer(modifier = Modifier.height(16.dp))

        TextButton(

            onClick = {
                navigateBack()},

            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Blue
            ),
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)

        ) {

            Text(
                "Go Back",
                color = Color.White

            )

        }

    }


}