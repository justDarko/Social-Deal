package com.example.core.domain.useCase

import com.example.core.data.CustomResult

abstract class BaseUseCase<in Params, out Type> where Type : Any {
    abstract suspend operator fun invoke(params: Params):
            CustomResult<Type>
}