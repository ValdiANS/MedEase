package com.myapplication.medease.data.remote.response

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: DataPredict,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class ObatData(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("detail_obat")
	val detailObat: DetailObat,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("kapasitas")
	val kapasitas: List<Float>,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("tipe")
	val tipe: Tipe,

	@field:SerializedName("id_tipe")
	val idTipe: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class JsonData(

	@field:SerializedName("ING")
	val iNG: List<String?>? = null,

	@field:SerializedName("DES")
	val dES: List<String?>? = null,

	@field:SerializedName("ORG")
	val oRG: List<Any?>? = null,

	@field:SerializedName("TYPE")
	val tYPE: List<Any?>? = null,

	@field:SerializedName("NAME")
	val nAME: List<String?>? = null
)

data class DataPredict(

	@field:SerializedName("jsonData")
	val jsonData: JsonData? = null,

	@field:SerializedName("obatData")
	val obatData: ObatData
)