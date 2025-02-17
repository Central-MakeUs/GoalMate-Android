package cmc.goalmate.data.exception

sealed class GoalMateException(val throwable: Throwable) : Exception(throwable)

class HttpException(val code: Int, throwable: Throwable) : GoalMateException(throwable)

class NetworkException(throwable: Throwable) : GoalMateException(throwable)

class UnknownException(throwable: Throwable) : GoalMateException(throwable)
