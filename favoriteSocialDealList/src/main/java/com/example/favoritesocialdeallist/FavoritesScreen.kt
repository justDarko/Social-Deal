import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.components.DealCardComponent
import com.example.favoritesocialdeallist.FavoritesScreenViewModel
import com.example.favoritesocialdeallist.FavoritesScreenViewState
import timber.log.Timber

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    onOpenDetails: () -> Unit,
    viewModel: FavoritesScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val currentState = state.value) {
            FavoritesScreenViewState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is FavoritesScreenViewState.FavoriteSocialDealsList -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        currentState.socialDealsList,
                        // Using key to optimize recomposition
                        key = { deal -> deal.id }
                    ) { deal ->
                        DealCardComponent(
                            modifier = Modifier.fillMaxWidth(),
                            city = deal.city,
                            company = deal.company,
                            sold = deal.sold,
                            oldPrice = deal.oldPrice,
                            newPrice = deal.newPrice,
                            currencySign = deal.currencySign,
                            isFavorite = deal.isFavorite,
                            title = deal.title,
                            imageCover = {
                                AsyncImage(
                                    model = "https://media.socialdeal.nl${deal.image}",
                                    contentDescription = "Deal image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            },
                            onDealCardClick = {
                                Timber.d("Current Deal Title: ${deal.title}")
                                onOpenDetails()
                            },
                            onFavoriteClick = {
                                Timber.d("Current Deal id: ${deal.id}")
                                viewModel.setFavoriteSocialDeal(socialDeal = deal)
                            }
                        )
                    }
                }
            }

            is FavoritesScreenViewState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error loading deals")
                }
            }
        }
    }
}

