package ec.edu.puce.githubclient.services

import ec.edu.puce.githubclient.models.Repository
import retrofit2.http.GET

interface ApiService {
    @GET(value = "/user/repos")
    suspend fun getRepositories () : List<Repository>
}


/**
 * asyc funection (ab, ne) {
 * axios.get("/user/repos")
 *
 * }
 */