package com.example.ui_character_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.character_domain.PhotoModel
import com.example.components.isInPreview
import com.example.modularized_rickandmortyapp.components.R as componentsR

@Composable
fun CharacterListItem(
    character: PhotoModel,
    modifier: Modifier = Modifier,
    onCharacterSelected: (id: Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            .clickable { onCharacterSelected(character.id ?: 0) },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(0.dp)
        ) {
            val (image, text) = createRefs()
            if (!isInPreview) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.url)
                        .placeholder(componentsR.drawable.white_background)
                        .error(componentsR.drawable.error_image)
                        .crossfade(true)
                        .build(),
                    contentDescription = character.title,
                    modifier = Modifier
                        .height(200.dp)
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                    contentScale = ContentScale.FillWidth,
                )
            } else {
                Image(
                    painter = painterResource(id = componentsR.drawable.character_avatar),
                    contentDescription = character.title,
                    modifier = Modifier
                        .constrainAs(image) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(text.top)
                            width = Dimension.fillToConstraints
                        }
                        .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                    contentScale = ContentScale.FillWidth,
                )
            }
            Text(
                text = character.title ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                modifier = Modifier
                    .constrainAs(text) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(image.bottom)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(horizontal = 8.dp)
            )
        }
    }
}