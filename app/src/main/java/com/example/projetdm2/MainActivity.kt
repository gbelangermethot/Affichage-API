package com.example.projetdm2

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.projetdm2.ui.theme.ProjetDM2Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {
    private val viewModel: StateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetDM2Theme {
                    AppNavigation(
                        viewModel = viewModel
                    )
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: StateViewModel){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "ecran1"){
        composable("ecran1"){
            EcranCharacters(viewModel = viewModel,
                navController = navController,
                )
        }
        composable("ecran2")
        {
            AfficherDetails(viewModel = viewModel, navController = navController)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AfficherDetails(navController: NavController, viewModel: StateViewModel) {

    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val likeNotificationService = LikeNotificationService(LocalContext.current)
    LaunchedEffect(key1 = true) {
        if(!postNotificationPermission.status.isGranted){
            postNotificationPermission.launchPermissionRequest()
        }
    }

    Box(
        modifier = with (Modifier){
            fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds)

        })
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logotop),
            contentDescription = "Logo",
            modifier = Modifier.padding(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black)
        ) {
            AsyncImage(
                model = viewModel.persoCourant.value?.image ?: "",
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.error),
                contentDescription = viewModel.persoCourant.value?.name,
                modifier = Modifier.fillMaxWidth()
            )
        }
        HorizontalDivider(
            thickness = 16.dp,
            color = Color.Transparent
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black)
                .background(Color.LightGray)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()

            ) {
                Row {
                    Text(
                        text = "Nom: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = viewModel.persoCourant.value?.name ?: "")
                }
                Row {
                    Text(
                        text = "Date de naissance: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = viewModel.persoCourant.value?.bio?.born ?: "")
                }
                Row {
                    Text(
                        text = "Ethnicité: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = viewModel.persoCourant.value?.bio?.ethnicity ?: "")
                }
                Row {
                    Text(
                        text = "Armes de predilection: ",
                        fontWeight = FontWeight.Bold
                    )
                    val weapons = viewModel.persoCourant.value?.personalInformation?.weaponsOfChoice ?: emptyList()
                    val wpnString = weapons.joinToString(separator = ", ")
                    Text(text = wpnString)
                }
                Row {
                    Text(
                        text = "Première apparition: ",
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = viewModel.persoCourant.value?.chronologicalInformation?.firstAppearance ?: "")
                }
                Row {
                    Text(
                        text = "Voix: ",
                        fontWeight = FontWeight.Bold
                    )
                    val acteurs = viewModel.persoCourant.value?.chronologicalInformation?.voicedBy ?: emptyList()
                    val actString = acteurs.joinToString(separator = ", ")
                    Text(text = actString)
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.like),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                                likeNotificationService.showNotification(
                                    viewModel.persoCourant.value?.name ?: ""
                                )
                            }
                    )
                }
            }
        }
        HorizontalDivider(
            thickness = 16.dp,
            color = Color.Transparent
        )
        Button(
            onClick = {
                navController.navigate("ecran1"){
                    popUpTo("ecran1"){inclusive = true}
                }
            }
        ){
            Text(
                text = "Retour"
            )
        }
    }
}


@Composable
fun EcranCharacters(viewModel: StateViewModel, navController: NavController){
    val characters by viewModel.characters
    Box(
        modifier = with (Modifier){
            fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds)

        })
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logotop),
            contentDescription = "Logo",
            modifier = Modifier.padding(16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            content = {
                items(characters) { character ->
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 16.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier.padding(8.dp),

                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Log.d("mon perso", character.name)
                                        viewModel.persoCourant.value = character
                                        navController.navigate("ecran2")
                                    }
                        ) {
                            AsyncImage(
                                model = character?.image ?: "",
                                placeholder = painterResource(id = R.drawable.placeholder),
                                error = painterResource(id = R.drawable.error),
                                contentDescription = character.name
                            )
                            Text(text = character.name)
                        }
                    }
                }
            }
        )
    }
    DisposableEffect(Unit) {
        viewModel.getCharacters()
        onDispose {}
    }
}

@Preview(showBackground = true)
@Composable
fun LogoOnlyPreview() {
    Box(modifier = Modifier.size(120.dp).border(1.dp, Color.Red)) {
        Image(
            painter = painterResource(id = R.drawable.logotop),
            contentDescription = "logo preview",
            modifier = Modifier.fillMaxSize()
        )
    }
}