package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.mapsapp.data.MapsApp
import com.example.mapsapp.viewmodels.MainViewModel
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext



@Composable
fun ListScreen(navigateToDetail: (String) -> Unit){

    val myViewModel = viewModel<MainViewModel>()
    val MapsList by myViewModel.MapsList.observeAsState(emptyList<MapsApp>())
    myViewModel.getAllMaps()
    val context = LocalContext.current
    val studentName: String by myViewModel.MapsName.observeAsState("")
    val studentMark: String by myViewModel.MapsMark.observeAsState("")


    LaunchedEffect(Unit) {
        myViewModel.getAllMaps()
    }

    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            "Maps List",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            textAlign = TextAlign.Left
        )


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .padding(top = 20.dp)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (MapsList.isEmpty()) {
                // Animación de carga cuando no hay datos
                items(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    )
                }
            } else {
                items(MapsList, key = {it.id?:it.hashCode()}) { Maps ->
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            if (it == SwipeToDismissBoxValue.EndToStart) {
                                myViewModel.deleteMaps(Maps.id.toString())
                                Toast.makeText(
                                    context,
                                    "Ubicacion eliminada: ${Maps.name}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                true
                            } else false
                        }
                    )
                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Red, shape = RoundedCornerShape(16.dp))
                                    .padding(end = 20.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    ) {
                        MapsItem(Maps = Maps, navigateToDetail = { navigateToDetail(Maps.id.toString()) })
                    }
                }
            }
        }
    }

}


@Composable
fun MapsItem(Maps: MapsApp, navigateToDetail: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { navigateToDetail(Maps.id.toString()) }
            .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = Maps.image,
                contentDescription = Maps.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = Maps.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(10.dp))
            }
        }
    }
}