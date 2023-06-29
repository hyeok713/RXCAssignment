package com.hyeokbeom.rxcassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hyeokbeom.domain.model.GoodsListResponse
import com.hyeokbeom.rxcassignment.ui.theme.*
import com.hyeokbeom.shared.decimalFormat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var goodsList by remember { mutableStateOf(emptyList<GoodsListResponse.Good>()) }
            /* Main View 상태 관리 */
            when (val it = viewModel.mainViewState.collectAsState().value) {
                is MainViewState.Idle -> {}
                is MainViewState.IsLoading -> {}
                is MainViewState.Error -> {}
                is MainViewState.MainList -> {
                    goodsList = it.list
                }
            }

            RXCAssignmentTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    /* Top Navigation */
                    NavigationArea()

                    /* Goods List */
                    GoodsArea(goodsList)
                }
            }
        }.also { viewModel.fetchGoodsList() }
    }
}

/**
 * NavigationArea
 * [상단 앱 바 영역]
 * - Title 및 Navigation Icon
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NavigationArea() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.wishlist),
                style = Typography.labelLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* TODO: to do something.. */ }) {
                Icon(imageVector = Icons.Sharp.ArrowBack, contentDescription = "Back Button")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}


/**
 * GoodsArea
 * [상품 영역 전체 (List)]
 */
@Composable
fun GoodsArea(goods: List<GoodsListResponse.Good>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(goods) {
            GoodView(good = it)
        }
    }
}

/**
 * GoodView
 * [상품 영역]
 * @param good 상품 정보
 */
@Preview
@Composable
private fun GoodView(good: GoodsListResponse.Good = GoodsListResponse.Good.SAMPLE) = with(good) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /* 상품 이미지 */
        Box(
            modifier = Modifier
                .aspectRatio(1 / 1f)
                .background(color = C_F7F7F7A100, shape = RoundedCornerShape(8.dp))
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = good.thumbnailUrl,
                contentDescription = "Good Image",
            )
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
 * 할인율 적용된 제품에 대하여 텍스트를 달리 설정함
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
                    style = PriceLabel.displayMedium.copy(color = C_DD3B3BA100)
                )

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = price.decimalFormat + stringResource(id = R.string.won),
                    style = PriceLabel.displayMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = consumerPrice.decimalFormat + stringResource(id = R.string.won),
                style = PriceLabel.displaySmall
            )
        }
    } else {
        Text(
            text = price.decimalFormat + stringResource(id = R.string.won),
            style = PriceLabel.displayMedium
        )
    }
}



