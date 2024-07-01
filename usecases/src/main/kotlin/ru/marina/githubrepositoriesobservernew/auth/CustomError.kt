package ru.marina.githubrepositoriesobservernew.auth

sealed class CustomError {

    object BadCredentials : CustomError()

}