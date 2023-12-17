package com.myapplication.medease.data.local.preference

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val token: String,
    val isLogin: Boolean,
    val isGuest: Boolean
)
