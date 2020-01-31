package com.example.kacmacuna

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties

data class UserInfo(
    val name: String = "",
    val mobile: String? = "",
    val address: String = ""
)

