package com.example.core.domain.useCase

import com.example.core.data.CustomResult

abstract class BaseUseCase<in Params, out Type> where Type : Any {
    abstract suspend fun invoke(params: Params): CustomResult<Type>

    suspend operator fun invoke(): CustomResult<Type> {
        return invoke(Unit as Params)
    }
}