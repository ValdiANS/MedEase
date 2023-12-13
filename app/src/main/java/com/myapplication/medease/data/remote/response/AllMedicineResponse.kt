package com.myapplication.medease.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllMedicineResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("detail_obat")
	val detailObat: DetailObat,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("kapasitas")
	val kapasitas: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("tipe")
	val tipe: Tipe
)

data class DetailObat(

	@field:SerializedName("dewasa")
	val dewasa: String? = null,

	@field:SerializedName("kontraIndikasi")
	val kontraIndikasi: String? = null,

	@field:SerializedName("indikasi_umum")
	val indikasiUmum: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("efek_samping")
	val efekSamping: String? = null,

	@field:SerializedName("anak")
	val anak: String? = null,

	@field:SerializedName("perhatian")
	val perhatian: String? = null
)

data class Tipe(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String
)
