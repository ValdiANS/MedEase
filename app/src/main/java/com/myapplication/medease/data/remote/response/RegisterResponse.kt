package com.myapplication.medease.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("data")
	val data: RegisterData,

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class RegisterData(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("birthdate")
	val birthdate: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
