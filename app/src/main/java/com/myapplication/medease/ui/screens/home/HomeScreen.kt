package com.myapplication.medease.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapplication.medease.R
import com.myapplication.medease.data.local.entity.MedicineItems
import com.myapplication.medease.data.local.entity.dummyMedicineItems
import com.myapplication.medease.ui.common.UiState
import com.myapplication.medease.ui.components.ErrorScreen
import com.myapplication.medease.ui.components.LoadingItem
import com.myapplication.medease.ui.components.MedicineItem
import com.myapplication.medease.ui.theme.MedEaseTheme

@Composable
fun HomeScreen(
) {
    HomeContent(
        username = "Bedul",
        query = "",
        listMedicine = dummyMedicineItems,
        onQueryChanged = {},
        onSearch = {},
        navigateToDetail = {},
        navigateToScan = {}
    )
}

@Composable
fun HomeContent(
    username: String,
    query: String,
    listMedicine: List<MedicineItems>,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    navigateToDetail: () -> Unit,
    navigateToScan: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FABCamera(onClick = navigateToScan)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Banner(username = username, query = query, onSearch = onSearch, onQueryChanged = onQueryChanged)
            HomeSection(title = stringResource(R.string.list_of_medicine), uiState = UiState.Success(listMedicine)) {
                MedicineRow(listMedicine = listMedicine, navigateToDetail = navigateToDetail)
            }
            HomeSection(title = stringResource(R.string.resent_search), uiState = UiState.Loading) {
                MedicineRow(listMedicine = listMedicine, navigateToDetail = navigateToDetail)
            }
            HomeSection(title = stringResource(R.string.recent_medicine), uiState = UiState.Error("no data")) {
                MedicineRow(listMedicine = listMedicine, navigateToDetail = navigateToDetail)
            }
        }
    }
}

@Composable
fun HomeSection(
    title: String,
    uiState: UiState<Any>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.ExtraBold
            ),
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Box(
            modifier = Modifier.heightIn(min = 150.dp),
            contentAlignment = Alignment.Center
        ){
            when (uiState) {
                is UiState.Loading -> LoadingItem()
                is UiState.Success -> content()
                is UiState.Error -> ErrorScreen()
            }
        }

    }
}

@Composable
fun MedicineRow(
    listMedicine: List<MedicineItems>,
    modifier: Modifier = Modifier,
    navigateToDetail: () -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(listMedicine, key = { it.name }) { medicine ->
            MedicineItem(
                name = medicine.name,
                types = medicine.types,
                doses = medicine.doses,
                description = medicine.description,
                onClick = navigateToDetail
            )
        }
    }
}

@Composable
fun Banner(
    username: String,
    query: String,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(
                bottomStartPercent = 30,
                bottomEndPercent = 30
            ),
            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(R.string.hello_home, username),
                modifier = Modifier.padding(
                    top = 32.dp,
                    start = 16.dp,
                    bottom = 8.dp
                ),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(R.string.welcome_home),
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 32.dp
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Card(
            modifier = Modifier
                .offset(y = (-30).dp)
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp
                ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 16.dp
            )
        ) {
            MySearch(
                query = query,
                onSearch = onSearch,
                onQueryChanged = onQueryChanged
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearch(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChanged,
        onSearch = onSearch,
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(text = stringResource(R.string.search))
        },
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        content = {}
    )
}

@Composable
fun FABCamera(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera) ,
            contentDescription = "Open Camera"
        )
    }
}

@Preview
@Composable
fun HomeContentPrev() {
    MedEaseTheme {
        HomeContent(
            username = "Bedul",
            query = "",
            listMedicine = dummyMedicineItems,
            onQueryChanged = {},
            onSearch = {},
            navigateToDetail = {},
            navigateToScan = {}
        )
    }
}