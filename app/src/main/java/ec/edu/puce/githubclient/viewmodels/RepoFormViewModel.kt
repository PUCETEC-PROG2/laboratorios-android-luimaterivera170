package ec.edu.puce.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoFormViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(value = false)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg : StateFlow<String?> = _errorMsg.asStateFlow()

    private val _isSuccess = MutableStateFlow(false)
    val isSuccess : StateFlow<Boolean> = _isSuccess.asStateFlow()

    // Variables para saber qué repositorio estamos editando
    var isEditing = false
    var originalOwner = ""
    var originalName = ""

    // Estados para los campos de texto que leerá tu RepoForm
    val nameInput = MutableStateFlow("")
    val descriptionInput = MutableStateFlow("")

    // 🔥 ESTA ES LA FUNCIÓN QUE BORRARÁ EL ROJO DE MAINACTIVITY:
    fun setRepository(repository: Repository?) {
        if (repository != null) {
            // Modo Edición: Llenamos los datos con el repo seleccionado
            isEditing = true
            originalOwner = repository.owner.login
            originalName = repository.name
            nameInput.value = repository.name
            descriptionInput.value = repository.description ?: ""
        } else {
            // Modo Creación: Limpiamos todo para que aparezca vacío
            isEditing = false
            originalOwner = ""
            originalName = ""
            nameInput.value = ""
            descriptionInput.value = ""
        }
    }

    fun createRepo(name: String, description: String){
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val repoBody = RepositoryPayload(name, description)
                RetrofitClient.apiService.createRepository(repoBody)
                _isSuccess.value = true
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar el repositorio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateRepo(originalOwner: String, originalName: String, newName: String, newDescription: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val repoBody = RepositoryPayload(newName, newDescription)
                RetrofitClient.apiService.updateRepository(originalOwner, originalName, repoBody)
                _isSuccess.value = true
            } catch (e: Exception) {
                _errorMsg.value = "Error al actualizar el repositorio: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetSuccess() {
        _isSuccess.value = false
    }

    fun resetError() {
        _errorMsg.value = null
    }
}