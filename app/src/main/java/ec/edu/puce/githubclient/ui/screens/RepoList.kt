package ec.edu.puce.githubclient.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import ec.edu.puce.githubclient.ui.components.RepoItem

@Composable
fun RepoList (
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ){
        RepoItem(
            name = "Repositorio de Android",
            description = "Repositorio creado en Kotlin para Desarrollo Mobil",
            avatarUrl = "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fes.wikipedia.org%2Fwiki%2FArchivo%3ABarcelona_Sporting_Club_Logo.png&ved=0CBYQjRxqFwoTCLDRvKiSt5QDFQAAAAAdAAAAABAF&opi=89978449",
            language = "Kotlin"
        )
        RepoItem(
            name = "Repositorio de Android",
            description = "Repositorio creado en Python para Desarrollo Mobil",
            avatarUrl = "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fes.wikipedia.org%2Fwiki%2FArchivo%3ABarcelona_Sporting_Club_Logo.png&ved=0CBYQjRxqFwoTCLDRvKiSt5QDFQAAAAAdAAAAABAF&opi=89978449",
            language = "Python"
        )
        RepoItem(
            name = "Repositorio de Android",
            description = "Repositorio creado en React para Desarrollo Mobil",
            avatarUrl = "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fes.wikipedia.org%2Fwiki%2FArchivo%3ABarcelona_Sporting_Club_Logo.png&ved=0CBYQjRxqFwoTCLDRvKiSt5QDFQAAAAAdAAAAABAF&opi=89978449",
            language = "React"
        )
        RepoItem(
            name = "Repositorio de Android",
            description = "Repositorio creado en Javascri´t para Desarrollo Mobil",
            avatarUrl = "https://www.google.com/url?sa=t&source=web&rct=j&url=https%3A%2F%2Fes.wikipedia.org%2Fwiki%2FArchivo%3ABarcelona_Sporting_Club_Logo.png&ved=0CBYQjRxqFwoTCLDRvKiSt5QDFQAAAAAdAAAAABAF&opi=89978449",
            language = "Java"
        )
    }
}