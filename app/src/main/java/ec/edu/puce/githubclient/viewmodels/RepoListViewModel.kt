package ec.edu.puce.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel(){
    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isloading = MutableStateFlow(value = false)
    val isloading : StateFlow<Boolean> = _isloading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg : StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos (){
        viewModelScope.launch {
            _isloading.value = true
            _errorMsg.value = null
            try{
                _repos.value = RetrofitClient.apiService.getRepositories()
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
            } finally {
                _isloading.value = false
            }
        }
    }

    fun deleteRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            _isloading.value = true
            _errorMsg.value = null
            try {
                // 1. Guardamos la respuesta del servidor para verificar el estado de GitHub
                val response = RetrofitClient.apiService.deleteRepository(owner, repoName)

                // 2. Si el borrado fue exitoso en el servidor, actualizamos reactivamente la interfaz
                if (response.isSuccessful) {
                    _repos.value = _repos.value.filter { it.name != repoName }
                } else {
                    // Si falla (ej. error 403 o 404), te avisará en pantalla que el problema es por falta de permisos del Token
                    _errorMsg.value = "GitHub rechazó el borrado (Código: ${response.code()}). ¡Revisa los permisos de tu Token!"
                }
            } catch (e: Exception) {
                _errorMsg.value = "Error al eliminar el repositorio: ${e.localizedMessage}"
            } finally {
                _isloading.value = false
            }
        }
    }
}