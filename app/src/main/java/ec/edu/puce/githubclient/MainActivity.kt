package ec.edu.puce.githubclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.puce.githubclient.ui.screens.RepoForm
import ec.edu.puce.githubclient.ui.screens.RepoList
import ec.edu.puce.githubclient.ui.theme.GithubClientTheme
import ec.edu.puce.githubclient.viewmodels.RepoFormViewModel
import ec.edu.puce.githubclient.viewmodels.RepoListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubClientTheme {
                var currentScreen by remember { mutableStateOf("repoList") }
                val listViewModel: RepoListViewModel = viewModel()
                val formViewModel: RepoFormViewModel = viewModel()

                when (currentScreen) {
                    "repoList" -> RepoList(
                        viewModel = listViewModel, // Usamos la misma instancia compartida
                        onNavigateToForm = { repoSelected ->
                            formViewModel.resetError()

                            // 🔥 LA CLAVE AQUÍ:
                            // Pasamos el repositorio seleccionado al ViewModel del Formulario.
                            // Si 'repoSelected' es null, el formulario se limpia para CREAR.
                            // Si tiene datos, el formulario se rellena para EDITAR.
                            formViewModel.setRepository(repoSelected)

                            currentScreen = "repoForm"
                        }
                    )
                    "repoForm" -> RepoForm(
                        viewModel = formViewModel, // Nos aseguramos de pasarle el ViewModel
                        onBackClick = { currentScreen = "repoList" },
                        onSaveSuccess = {
                            listViewModel.fetchRepos() // Recarga la lista para ver los cambios
                            currentScreen = "repoList"
                        }
                    )
                }
            }
        }
    }
}