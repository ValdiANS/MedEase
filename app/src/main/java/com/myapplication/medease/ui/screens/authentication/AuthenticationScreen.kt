package com.myapplication.medease.ui.screens.authentication

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.ColorPrimary
import com.myapplication.medease.ui.theme.ColorTertiary
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapplication.medease.ViewModelFactory
import com.myapplication.medease.ui.theme.ColorNeutral
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    loginFormViewModel: LoginFormViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    registerFormViewModel: RegisterFormViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    onSignInAsGuest: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var authenticationTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val authenticationTabTitles =
        listOf(stringResource(R.string.sign_in), stringResource(R.string.sign_up))
    var authenticationTabPagerState = rememberPagerState { authenticationTabTitles.size }

    val snackbarHostState = remember { SnackbarHostState() }

    val onSignInTabClickHandler: () -> Unit = {
        coroutineScope.launch {
            authenticationTabPagerState.animateScrollToPage(0)
        }
    }

    val onSignUpTabClickHandler: () -> Unit = {
        coroutineScope.launch {
            authenticationTabPagerState.animateScrollToPage(1)
        }
    }

    val onChangeTabIndexHandler: (Int) -> Unit = { newIndex: Int ->
        coroutineScope.launch {
            authenticationTabPagerState.animateScrollToPage(newIndex)
        }
    }

    val onShowSnackbarHandler: (String) -> Unit = { message: String ->
        coroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        AuthenticationScreenContent(
            loginFormViewModel = loginFormViewModel,
            registerFormViewModel = registerFormViewModel,
            tabIndex = authenticationTabIndex,
            tabTitles = authenticationTabTitles,
            tabPagerState = authenticationTabPagerState,
            onSignUpTabClick = onSignUpTabClickHandler,
            onSignInTabClick = onSignInTabClickHandler,
            onSignInAsGuest = onSignInAsGuest,
            onChangeTabIndex = onChangeTabIndexHandler,
            onShowSnackbar = onShowSnackbarHandler,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthenticationScreenContent(
    loginFormViewModel: LoginFormViewModel,
    registerFormViewModel: RegisterFormViewModel,
    tabIndex: Int,
    tabTitles: List<String>,
    tabPagerState: PagerState,
    onSignUpTabClick: () -> Unit,
    onSignInTabClick: () -> Unit,
    onSignInAsGuest: () -> Unit,
    onChangeTabIndex: (Int) -> Unit,
    onShowSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        Spacer(Modifier.size(60.dp))

        AuthenticationHeader(
            title = when (tabPagerState.currentPage) {
                0 -> stringResource(R.string.login_title)
                1 -> stringResource(R.string.register_title)
                else -> stringResource(R.string.login_title)
            },
            description = stringResource(R.string.login_description),
        )

        Spacer(Modifier.size(20.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = ColorPrimary
            ),
            shape = RoundedCornerShape(
                topStart = 40.dp, topEnd = 40.dp
            ),
            modifier = when (tabPagerState.currentPage) {
                0 -> Modifier
                    .fillMaxSize()
                    .weight(1f)

                1 -> Modifier.fillMaxSize()

                else -> Modifier.fillMaxSize()
            }
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 40.dp)
            ) {

                AuthenticationTabRow(
                    tabIndex = tabIndex,
                    tabTitles = tabTitles,
                    pagerState = tabPagerState,
                    onChangeTabIndex = onChangeTabIndex,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                HorizontalPager(
                    state = tabPagerState,
                    verticalAlignment = Alignment.Top,
                ) { tabIndex ->
                    when (tabTitles[tabIndex]) {
                        stringResource(R.string.sign_in) -> {
                            LoginForm(
                                viewModel = loginFormViewModel,
                                onSignUpClick = onSignUpTabClick,
                                onSignInAsGuestClick = onSignInAsGuest
                            )
                        }

                        stringResource(R.string.sign_up) -> {
                            RegisterForm(
                                viewModel = registerFormViewModel,
                                onSignInClick = onSignInTabClick,
                                onShowSnackbar = onShowSnackbar,
                            )
                        }
                    }
                }

                Spacer(Modifier.size(24.dp))
            }
        }
    }
}

@Composable
fun AuthenticationHeader(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 24.sp,
                lineHeight = 32.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Bold,
                color = ColorPrimary
            )
        )

        Spacer(Modifier.size(8.dp))

        Text(
            text = description,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 16.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Medium,
                color = ColorTertiary,
            )
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthenticationTabRow(
    tabIndex: Int,
    tabTitles: List<String>,
    pagerState: PagerState,
    onChangeTabIndex: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = ColorNeutral,
        indicator = { tabPositions: List<TabPosition> ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(RoundedCornerShape(100))
                    .shadow(elevation = 2.dp, spotColor = Color.Black, ambientColor = Color.Black)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFCBD5E1),
                        shape = RoundedCornerShape(100)
                    )
                    .background(Color.White)
            )
        },
        modifier = modifier.clip(RoundedCornerShape(100.dp))
    ) {
        tabTitles.forEachIndexed { index, title ->
            val selected = pagerState.currentPage == index

            Tab(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(100))
                    .zIndex(10f),
                selected = selected,
                onClick = { onChangeTabIndex(index) },
                text = {
                    Text(
                        text = title,
                        color = if (selected) ColorPrimary else ColorTertiary
                    )
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    MedEaseTheme {
        AuthenticationScreen(
            onSignInAsGuest = {}
        )
    }
}