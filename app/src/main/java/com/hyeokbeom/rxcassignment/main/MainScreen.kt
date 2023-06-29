package com.hyeokbeom.rxcassignment.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hyeokbeom.domain.model.GoodsListResponse
import com.hyeokbeom.rxcassignment.R
import com.hyeokbeom.rxcassignment.ui.theme.*
import com.hyeokbeom.shared.decimalFormat

/**
 * [MainScreen]
 * MainActivity 의 View 구성
 */


/**
 * NavigationArea
 * [상단 앱 바 영역]
 * Title 및 Navigation Icon
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NavigationArea() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.wishlist),
                style = Typography.labelLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* TODO: to do something.. */ }) {
                Icon(imageVector = Icons.Sharp.ArrowBack, contentDescription = "Back Button")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = colorScheme.background)
    )
}


/**
 * GoodsArea
 * [상품 영역 전체 (List)]
 * @param goods 상품 리스트
 */
@Composable
fun GoodsArea(goods: List<GoodsListResponse.Good>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(goods) { index, item ->
            GoodView(good = item, index)
        }
    }
}

/**
 * GoodView
 * [상품 영역]
 * 버튼 클릭시 like 상태 변경
 * 유저 action -> 서버 요청 (intent) 이후 처리된 결과를 통해 View Update
 * @param good 상품 정보
 */
@Composable
private fun GoodView(good: GoodsListResponse.Good, id: Int) = with(good) {
    val viewModel: MainViewModel = hiltViewModel()
    var likedState by remember { mutableStateOf(this.isLike) }

    /* SideEffect: Local likeState 변경 -> List Item 인 Good 의 isLike property 변경 */
    LaunchedEffect(likedState) { good.isLike = likedState }

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /* 상품 이미지 */
        Box(modifier = Modifier.aspectRatio(1 / 1f)) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = C_F7F7F7A100, shape = RoundedCornerShape(8.dp))
                    .clip(shape = RoundedCornerShape(4.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailUrl)
                    .crossfade(300)
                    .build(),
                contentDescription = "Good Image",
            )

            /* 클릭 여부에 따라 아이콘 설정 */
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(2.dp),
                /* Action - Click */
                onClick = {
                    /* Intent - 좋아요 상태 변경 */
                    viewModel.requestLikeChange(id, isLike) {
                        likedState = it // 처리 결과를 바탕으로 상태 변경
                    }
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = if (likedState) R.drawable.ic_like_filled else R.drawable.ic_like),
                    contentDescription = "Like Icon",
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.height(144.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /* 상품 라벨 */
            Text(
                text = name,
                style = TitleLabel.displayMedium,
                color = colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            /* 가격 라벨 */
            PriceText(
                discountRate = discountRate,
                price = price,
                consumerPrice = consumerPrice
            )
        }
    }
}

/**
 * PriceText
 * [상품 영역 - 가격 텍스트]
 * 할인율 적용된 제품에 대하여 텍스트 다르게 설정함
 * @param discountRate - 할인율
 * @param price - 가격
 * @param consumerPrice - 소비자 가격
 */
@Composable
fun PriceText(
    discountRate: Int,
    price: Int,
    consumerPrice: Int,
) {
    if (discountRate > 0) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Text(
                    text = "${discountRate.decimalFormat}%",
                    style = PriceLabel.displayMedium,
                    color = C_DD3B3BA100,
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = price.decimalFormat + stringResource(id = R.string.won),
                    style = PriceLabel.displayMedium,
                    color = colorScheme.onBackground,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = consumerPrice.decimalFormat + stringResource(id = R.string.won),
                style = PriceLabel.displaySmall,
                color = colorScheme.onBackground,
            )
        }
    } else {
        Text(
            text = price.decimalFormat + stringResource(id = R.string.won),
            style = PriceLabel.displayMedium,
            color = colorScheme.onBackground,
        )
    }
}
