package com.example.core.domain.useCase

abstract class BaseUseCaseNoResult<in Params> {
    abstract suspend fun invoke(params: Params)
}