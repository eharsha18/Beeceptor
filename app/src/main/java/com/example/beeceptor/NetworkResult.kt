package com.example.beeceptor

sealed class NetworkResult<T>(val data:List<T>? = null, val message:String?=null) {
    class Success<T>(data: List<T>): NetworkResult<T>(data)
    class Error<T>(message: String?, data:List<T>?=null): NetworkResult<T>(data,message)
    class Loading<T>: NetworkResult<T>()
}