package com.raka.revolutdemo.domain.exception

class DomainException(val error: Error) : Exception(error.description) {

    open class Error(val description: String) {
        object GenericError : Error("Something went wrong")
        object NoInternet : Error("No internet connection")
    }
}
