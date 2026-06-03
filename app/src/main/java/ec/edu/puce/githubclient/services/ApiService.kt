package ec.edu.puce.githubclient.services
import ec.edu.puce.githubclient.models.Repository
import ec.edu.puce.githubclient.models.RepositoryPayload
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/user/repos")
    suspend fun getRepositories (
        @Query("sort") created: String = "created",
        @Query("direction") direction: String = "desc",
        @Query("affiliation") affiliation: String = "owner",
        @Query("per_page") perPage: Int = 100,
        @Query("t") t: String = "${System.currentTimeMillis()}",

        ) : List<Repository>

    @POST("/user/repos")
    suspend fun createRepository(
        @Body respository: RepositoryPayload
    ) : Repository

    @PATCH("/repos/{owner}/{repo}")
    suspend fun updateRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body repository: RepositoryPayload
    ): Repository

    @DELETE("/repos/{owner}/{repo}")
    suspend fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): retrofit2.Response<Unit>
}

