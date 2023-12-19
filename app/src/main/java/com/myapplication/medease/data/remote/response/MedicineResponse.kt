package com.myapplication.medease.data.remote.response

import com.google.gson.annotations.SerializedName

data class MedicineResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Tipe(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String
)

data class DataItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("detail_obat")
	val detailObat: DetailObat,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("kapasitas")
	val kapasitas: List<Float>,

	@field:SerializedName("deskripsi")
	val deskripsi: String?,

	@field:SerializedName("tipe")
	val tipe: Tipe
)

data class DetailObat(

	@field:SerializedName("komposisi")
	val komposisi: List<String>,

	@field:SerializedName("dewasa")
	val dewasa: String,

	@field:SerializedName("kontraIndikasi")
	val kontraIndikasi: String,

	@field:SerializedName("indikasi_umum")
	val indikasiUmum: String,

	@field:SerializedName("golongan")
	val golongan: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("efek_samping")
	val efekSamping: String,

	@field:SerializedName("anak")
	val anak: String,

	@field:SerializedName("perhatian")
	val perhatian: String
)
