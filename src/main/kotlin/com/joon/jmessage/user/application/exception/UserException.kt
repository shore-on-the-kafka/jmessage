package com.joon.jmessage.user.application.exception

class UserException {
    class NotFoundUserException : RuntimeException()
    class InvalidPasswordException : RuntimeException()
}