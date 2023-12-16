package com.myapplication.medease.ui.screens.detail_medicine

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myapplication.medease.R
import com.myapplication.medease.ViewModelFactory
import com.myapplication.medease.ui.common.UiState
import com.myapplication.medease.ui.components.ErrorScreen
import com.myapplication.medease.ui.components.LoadingItem
import com.myapplication.medease.ui.theme.ColorNeutral
import com.myapplication.medease.ui.theme.ColorPrimary
import com.myapplication.medease.ui.theme.ColorSecondary
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily

@Composable
fun DetailMedicineScreen(
    medicineId: String,
    onNavigateBack: () -> Unit,
    onSetSchedule: (medicineName: String) -> Unit,
    modifier: Modifier = Modifier,
    detailScreenViewModel: DetailScreenViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    // Dummy
//    DetailMedicineContent(
//        medicineName = "Panadol Merah",
//        medicineCategory = "Obat Bebas (Hijau)",
//        generalIndication = "Obat ini dapat digunakan untuk meringankan sakit kepala dan sakit gigi.",
//        medicineIngredients = "Tiap kaplet mengandung Paracetamol 500 mg dan Caffeine 65 mg.",
//        adultDose = "1 kaplet ditelan dengan segelas air, 3-4 kali sehari bila gejala memburuk, diminum sebelum atau sesudah makan. Tidak melebihi 8 kaplet dalam 24 jam. Minimum interval penggunaan dosis adalah 4 jam.",
//        kidDose = "Anak-anak usia lebih dari 12 tahun, 1 kaplet ditelan dengan segelas air, 3-4 kali sehari bila gejala memburuk, diminum sebelum atau sesudah makan. Tidak melebihi 8 kaplet dalam 24 jam. Minimum interval penggunaan dosis adalah 4 jam.",
//        additionalThings = "Bila setelah 2 hari demam tidak menurun atau setelah 5 hari nyeri tidak menghilang, segera hubungi Unit Pelayanan Kesehatan. Kategori kehamilan : Kategori C: Mungkin berisiko. Selengkapnya bisa diakses melalui website Halodoc berikut.",
//        contraindications = "Wanita hamil dan menyusui. Tidak dianjurkan untuk digunakan pada anak dibawah usia 12 tahun",
//        medicineDosage = "750mg / capsules",
//        medicineDetail = "Balsalazide is a 5-aminosalicylic acid (5-ASA) medication used to treat ulcerative colitis, a chronic inflammatory bowel disease (IBD) that affects the large intestine. It works by reducing inflammation in the colon. ",
//        medicineSideEffect = "Reaksi efek samping jarang terjadi seperti  reaksi hipersensitifitas. Pemakaian obat umumnya memiliki efek samping tertentu dan sesuai dengan masing-masing individu. Jika terjadi efek samping yang berlebih dan berbahaya, hentikan penggunaan obat dan segera hubungi dokter. Selengkapnya bisa diakses melalui website Halodoc berikut.",
//        onNavigateBack = onNavigateBack,
//        modifier = modifier
//    )
    detailScreenViewModel.medicineState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                LoadingItem()
                detailScreenViewModel.getDetailMedicineById(medicineId)
            }
            is UiState.Success -> {
                // TODO specify ingredients and medicine detail and also kids dose if null
                DetailMedicineContent(
                    medicineName = uiState.data.nama,
                    medicineCategory = uiState.data.tipe.nama,
                    generalIndication = uiState.data.deskripsi,
                    medicineIngredients = "",
                    adultDose = uiState.data.detailObat.dewasa,
                    kidDose = uiState.data.detailObat.anak ?: "",
                    additionalThings = uiState.data.detailObat.perhatian,
                    contraindications = uiState.data.detailObat.kontraIndikasi,
                    medicineSideEffect = uiState.data.detailObat.efekSamping,
                    medicineDosage = stringResource(R.string.doses, uiState.data.kapasitas),
                    medicineDetail = "",
                    onNavigateBack = onNavigateBack,
                    onSetSchedule = onSetSchedule,
                    modifier = modifier)
            }
            else -> ErrorScreen()
        }
    }
}

@Composable
fun DetailMedicineContent(
    medicineName: String,
    medicineCategory: String,
    generalIndication: String,
    medicineIngredients: String,
    adultDose: String,
    kidDose: String,
    additionalThings: String,
    contraindications: String,
    medicineSideEffect: String,
    medicineDosage: String,
    medicineDetail: String,
    onNavigateBack: () -> Unit,
    onSetSchedule: (medicineName: String) -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(ColorNeutral)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row {
                    IconButton(
                        onClick = {
                            // Navigate back
                            onNavigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }

                Spacer(Modifier.size(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                        border = BorderStroke(2.dp, ColorPrimary),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Text(
                            text = medicineName,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontFamily = montserratFamily,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 24.dp),
                        )
                    }

                    Spacer(Modifier.size(20.dp))

                    Row {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = ColorSecondary,
                            ),
                            shape = RoundedCornerShape(size = 6.dp)
                        ) {
                            Text(
                                text = medicineCategory,
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    fontFamily = montserratFamily,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center,
                                ),
                                modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                    Spacer(Modifier.size(28.dp))

                    DetailMedicineContentSection(
                        title = "General Indications",
                        content = generalIndication,
                    )

                    Spacer(Modifier.size(24.dp))

                    DetailMedicineContentSection(
                        title = "Ingredients",
                        content = medicineIngredients
                    )

                    Spacer(Modifier.size(24.dp))

                    DetailMedicineContentSection(
                        title = "Dose",
                        subContent = {
                            DetailMedicineContentSection(
                                title = "Kid",
                                content = kidDose,
                            )

                            Spacer(Modifier.size(8.dp))

                            DetailMedicineContentSection(
                                title = "Adult",
                                content = adultDose,
                            )
                        }
                    )

                    Spacer(Modifier.size(24.dp))

                    DetailMedicineContentSection(
                        title = "Attention",
                        content = additionalThings
                    )

                    Spacer(Modifier.size(24.dp))

                    DetailMedicineContentSection(
                        title = "Contraindications",
                        content = contraindications
                    )

                    Spacer(Modifier.size(24.dp))

                    DetailMedicineContentSection(
                        title = "Side Effects",
                        content = medicineSideEffect
                    )

                    Spacer(Modifier.size(40.dp))

                    Button(
                        onClick = {
                            onSetSchedule(medicineName)
                        },
                        shape = RoundedCornerShape(size = 20.dp),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Set Schedule",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontFamily = montserratFamily,
                                    fontWeight = FontWeight.Bold,
                                )
                            )

                            Icon(
                                imageVector = Icons.Default.ArrowForward, contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailMedicineScreenPreview() {
    MedEaseTheme {
        DetailMedicineScreen(
            medicineId = "123",
            onNavigateBack = {},
            onSetSchedule = {}
        )
    }
}

@Composable
fun DetailMedicineContentSection(
    title: String,
    modifier: Modifier = Modifier,
    content: String = "",
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        fontFamily = montserratFamily,
    ),
    contentStyle: TextStyle = MaterialTheme.typography.bodyLarge.copy(
        fontSize = 12.sp,
        fontFamily = montserratFamily,
        fontWeight = FontWeight(400),
    ),
    subContent: @Composable () -> Unit = {},
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = titleStyle
        )

        if (content.isNotEmpty()) {
            Spacer(Modifier.size(10.dp))

            Text(
                text = content,
                style = contentStyle,
            )
        } else {
            subContent()
        }
    }
}