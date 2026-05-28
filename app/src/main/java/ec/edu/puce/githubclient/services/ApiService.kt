package ec.edu.puce.githubclient.services

import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET(value = "/user/repos")
    suspend fun getRepositories (
        @Query("sort") created : String = "created",
        @Query("sort") direction : String = "desc",
        @Query("affiliation") affiliation : String = "owner",
        @Query("per_page") perPage: Int = 100,
        @Query("t") t: String = "${System.currentTimeMillis()}",
    ): List<Repository>

    @POST("/user/repos")
    suspend fun createRepository (
        @Body repository: RepositoryPayload
    ) : Repository


}


