package com.onirutla.newsapp.ui

sealed interface ResultState<out T> {
    data object Loading : ResultState<Nothing>
    data object Error : ResultState<Nothing>
    data class InitialState<T>(val initialState: T) : ResultState<T>
    data class Success<T>(val data: T) : ResultState<T>
}