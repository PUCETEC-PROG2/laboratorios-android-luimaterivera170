package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoForm(
    onBackClick: () -> Unit = {},
    onSaveSuccess: () -> Unit = {},
    viewModel: RepoFormViewModel = viewModel()
) {
    // Escuchamos de forma reactiva el estado del ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()

    // 🔥 Recolectamos los textos en tiempo real desde el flujo del ViewModel
    val name by viewModel.nameInput.collectAsState()
    val description by viewModel.descriptionInput.collectAsState()
    val isEditMode = viewModel.isEditing

    LaunchedEffect(isSuccess) {
        if (isSuccess == true) {
            onSaveSuccess()
            viewModel.resetSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Editar Repositorio" else "Crear Repositorio") },
                navigationIcon = {
                    IconButton(onClick = onBackClick){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (isLoading){
                CircularProgressIndicator(
                    Modifier.align(Alignment.CenterHorizontally)
                )
            } else if (!errorMsg.isNullOrEmpty()){
                Text(
                    text = errorMsg!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                OutlinedTextField(
                    value = name,
                    // Actualizamos el flujo directamente en el ViewModel
                    onValueChange = { viewModel.nameInput.value = it },
                    label = { Text("Nombre del repositorio") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = description,
                    // Actualizamos el flujo directamente en el ViewModel
                    onValueChange = { viewModel.descriptionInput.value = it },
                    label = { Text("Descripción del repositorio") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isEditMode) {
                            // Ejecuta la actualización usando las referencias guardadas en el setRepository
                            viewModel.updateRepo(
                                originalOwner = viewModel.originalOwner,
                                originalName = viewModel.originalName,
                                newName = name,
                                newDescription = description
                            )
                        } else {
                            viewModel.createRepo(name, description)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Guardar"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(if (isEditMode) "Actualizar" else "Guardar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoFormPreview() {
    GithubClientTheme {
        RepoForm()
    }
}