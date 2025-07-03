package com.example.core.domain.useCase

import com.example.core.data.CustomResult
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCaseFlow<in Params, out Type> where Type : Any {
    abstract operator fun invoke(params: Params):
            Flow<CustomResult<Type>>
}