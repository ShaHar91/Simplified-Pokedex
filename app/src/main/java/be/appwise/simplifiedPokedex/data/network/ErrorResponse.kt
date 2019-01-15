package be.appwise.simplifiedPokedex.data.network

class ErrorResponse(val code: Int, message: String) : Throwable("($code) $message")