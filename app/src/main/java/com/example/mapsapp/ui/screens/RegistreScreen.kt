package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModelFactory
import com.example.mapsapp.viewmodels.MainViewModel

@Composable
fun RegistreScreen(navigateToHome: () -> Unit, navigateToLogin: () -> Unit) {

    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel(factory = AuthViewModelFactory(SharedPreferencesHelper(context)))

    val authState by viewModel.authState.observeAsState()
    val showError by viewModel.showError.observeAsState()
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    if (authState == AuthState.Authenticated) {
        LaunchedEffect(Unit) {
            navigateToHome()
        }
    }

    if (showError == true) {
        val errorMessage = (authState as? AuthState.Error)?.message
        val messageToShow = if (errorMessage?.contains("weak_password") == true) {
            "La contraseña debe tener al menos 6 caracteres"
        } else {
            "Error al registrar"
        }
        Toast.makeText(context, messageToShow, Toast.LENGTH_LONG).show()
        viewModel.errorMessageShowed()
    }

    // UI estilizada para registro
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Regístrate para comenzar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.editEmail(it) },
                label = { Text("Correo electrónico") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.editPassword(it) },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )

            Button(
                onClick = { viewModel.signUp() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Registrarse")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = navigateToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}
