package com.myapplication.medease.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapplication.medease.R
import com.myapplication.medease.ViewModelFactory
import com.myapplication.medease.data.local.entity.MedicineEntity
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.ui.common.UiState
import com.myapplication.medease.ui.components.ErrorScreen
import com.myapplication.medease.ui.components.LoadingItem
import com.myapplication.medease.ui.components.MedicineItem

@Composable
fun HomeScreen(
    userModel: UserModel,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    onDetailClick: (String) -> Unit,
    onNavigateToCamera: () -> Unit,
) {
    val query by homeViewModel.query
    val allMedicine by homeViewModel.listMedicineState.collectAsState()
    val recentSearchState by homeViewModel.recentSearched.collectAsState()
    val searchedMedicineState by homeViewModel.searchedMedicine.collectAsState()
    var isSearch by remember {
        mutableStateOf(false)
    }

    BackHandler(enabled = isSearch) {
        if (isSearch) {
            isSearch = false
            homeViewModel.getAllRecentMedicine()
        }
    }

    HomeContent(
        username = userModel.name,
        query = query,
        allMedicineState = allMedicine,
        recentSearchState = recentSearchState,
        searchedMedicineState = searchedMedicineState,
        isSearch = isSearch,
        onQueryChanged = homeViewModel::onQueryChange,
        onSearch = {
            homeViewModel.searchMedicine(query)
            isSearch = true
        },
        navigateToDetail = onDetailClick,
        navigateToScan = onNavigateToCamera
    )
}

@Composable
fun HomeContent(
    username: String,
    query: String,
    allMedicineState: UiState<List<MedicineEntity>>,
    recentSearchState: UiState<List<MedicineEntity>>,
    searchedMedicineState: UiState<List<MedicineEntity>>,
    isSearch: Boolean,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToScan: () -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FABCamera(onClick = navigateToScan)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Banner(
                username = username,
                query = query,
                onSearch = onSearch,
                onQueryChanged = onQueryChanged
            )
            if (!isSearch) {
                LazyColumn {
                    item {
                        HomePage(
                            allMedicineState = allMedicineState,
                            recentSearchState = recentSearchState,
                            navigateToDetail = navigateToDetail
                        )
                    }
                }
            } else {
                SearchPage(
                    uiState = searchedMedicineState,
                    navigateToDetail = navigateToDetail,
                )
            }
        }
    }
}

@Composable
fun SearchPage(
    uiState: UiState<List<MedicineEntity>>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    when (uiState) {
        is UiState.Loading -> LoadingItem()
        is UiState.Success -> {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(uiState.data) { medicine ->
                    MedicineItem(
                        name = medicine.name,
                        types = medicine.type,
                        doses = stringResource(R.string.doses, medicine.doses),
                        description = medicine.description,
                        onClick = {
                            navigateToDetail(medicine.id)
                        }
                    )
                }
            }
        }
        is UiState.Error -> ErrorScreen()
        else -> ErrorScreen()
    }
}

@Composable
fun HomePage(
    allMedicineState: UiState<List<MedicineEntity>>,
    recentSearchState: UiState<List<MedicineEntity>>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    HomeSection(
        title = stringResource(R.string.list_of_medicine),
        uiState = allMedicineState,
        navigateToDetail = navigateToDetail
    )
    HomeSection(
        title = stringResource(R.string.recent_search),
        uiState = recentSearchState,
        navigateToDetail = navigateToDetail
    )
    // TODO section for favorite/bookmark medicine
//    HomeSection(
//        title = stringResource(R.string.recent_medicine),
//        uiState = allMedicineState,
//        navigateToDetail = navigateToDetail
//    )
}

@Composable
fun HomeSection(
    title: String,
    uiState: UiState<List<MedicineEntity>>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
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
        ) {
            when (uiState) {
                is UiState.Loading -> LoadingItem()
                is UiState.Success -> MedicineRow(listMedicine = uiState.data, navigateToDetail = navigateToDetail)
                is UiState.Error -> ErrorScreen()
                else -> ErrorScreen()
            }
        }

    }
}

@Composable
fun MedicineRow(
    listMedicine: List<MedicineEntity>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(listMedicine, key = { it.id }) { medicine ->
            MedicineItem(
                name = medicine.name,
                types = medicine.type,
                doses = stringResource(R.string.doses, medicine.doses),
                description = medicine.description,
                onClick = {
                    navigateToDetail(medicine.id)
                }
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
    onSearch: (String) -> Unit,
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
        MySearch(
            query = query,
            onSearch = onSearch,
            onQueryChanged = onQueryChanged,
            modifier = Modifier
                .offset(y = (-30).dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearch(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
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
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        content = {}
    )
}

@Composable
fun FABCamera(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_camera),
            contentDescription = "Open Camera"
        )
    }
}

@Preview
@Composable
fun HomeContentPrev() {
//    MedEaseTheme {
//        HomeContent(
//            username = "Bedul",
//            query = "",
//            listMedicine = dummyMedicineItems,
//            onQueryChanged = {},
//            onSearch = {},
//            navigateToDetail = {},
//            navigateToScan = {}
//        )
//    }
}