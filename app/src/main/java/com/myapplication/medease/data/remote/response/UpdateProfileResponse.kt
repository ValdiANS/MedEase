package com.myapplication.medease.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: List<Int>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
