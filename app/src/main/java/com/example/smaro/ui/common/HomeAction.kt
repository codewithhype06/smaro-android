package com.example.smaro.ui.common

sealed class HomeAction {
    object Learn : HomeAction()
    object Play : HomeAction()
    object Revise : HomeAction()
}
