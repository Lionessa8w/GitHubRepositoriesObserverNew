package ru.marina.githubrepositoriesobservernew.auth

import kotlinx.serialization.Serializable

@Serializable
class SignInResponseUserLoginEntity (
    val login: String
)