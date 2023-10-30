package com.zezzi.eventzezziapp.ui.meals.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.zezzi.eventzezziapp.navigation.AppBar
import com.zezzi.eventzezziapp.data.networking.response.MealResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealsCategoriesScreen(
    navController: NavController,
    viewModel: MealsCategoriesViewModel = viewModel()
) {
    val rememberedMeals: MutableState<List<MealResponse>> =
        remember { mutableStateOf(emptyList<MealResponse>()) }

    viewModel.getMeals { response ->
        val mealsFromTheApi = response?.categories
        rememberedMeals.value = mealsFromTheApi.orEmpty()
    }

    Scaffold(
        topBar = {
            AppBar(title = "Recepies", navController = navController)
        }
    ) {

        LazyColumn(contentPadding = it) {
            items(rememberedMeals.value) { meal ->
                Row(
                    modifier = Modifier.padding(8.dp), // Agrega padding o cualquier otro modificador que necesites
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = rememberImagePainter(data = meal.imageUrl),
                        contentDescription = "Imagen de ${meal.name}",
                        modifier = Modifier.size(60.dp) // Puedes ajustar el tamaño de la imagen según lo necesites
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre la imagen y el texto
                    Column {
                        Text(text = meal.name)
                        Divider(
                            color = Color.Gray, // Puedes cambiar el color si lo deseas
                            modifier = Modifier.padding(vertical = 8.dp) // Agrega padding si lo necesitas
                        )
                        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                            Date()
                        )
                        Text(text = currentDate)
                    }
                }
            }
        }

    }
}