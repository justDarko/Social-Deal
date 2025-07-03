import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.components.DealCardComponent
import com.example.core.data.IMAGE_BASE_URL
import com.example.socialdealdetails.SocialDealDetailsScreenViewModel
import com.example.socialdealdetails.SocialDealDetailsScreenViewState
import timber.log.Timber

@Composable
fun SocialDealDetailsScreen(
    id: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SocialDealDetailsScreenViewModel = hiltViewModel()
) {
    // Unit set on purpose so this
    LaunchedEffect(Unit) {
        Timber.d("Id inside Details Screen is: $id")
        viewModel.getSocialDealDetails(id = id)
    }
    val state = viewModel.state.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (val currentState = state.value) {
            SocialDealDetailsScreenViewState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is SocialDealDetailsScreenViewState.SocialDealDetails -> {
                val deal = currentState.socialDealDetails
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
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
                            description = deal.description,
                            imageCover = {
                                AsyncImage(
                                    model = "$IMAGE_BASE_URL${deal.image}",
                                    contentDescription = "Deal image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            },
                            onDealCardClick = {
                                // Exit the details
                                Timber.d("Current Deal Title: ${deal.title}")
                                onCardClick()
                            },
                            onFavoriteClick = {
                                Timber.d("Current Deal id: ${deal.id}")
                                viewModel.setFavoriteSocialDeal(socialDeal = deal)
                            }
                        )
                    }
                }
            }

            is SocialDealDetailsScreenViewState.Error -> {
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

