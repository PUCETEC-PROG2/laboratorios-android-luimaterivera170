package ec.edu.puce.githubclient.viewmodels
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel(){
    private val _repos= MutableStateFlow<List<Repository>>(emptyList())
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
                RetrofitClient.apiService.deleteRepository(owner, repoName)
                fetchRepos()
            } catch (e: Exception) {
                _errorMsg.value = "Error al eliminar el repositorio: ${e.localizedMessage}"
                _isloading.value = false
            }
        }
    }

}