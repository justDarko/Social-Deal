package com.example.core.domain.useCase

import com.example.core.data.CustomResult
import kotlinx.coroutines.flow.Flow

abstract class BaseUseCaseFlowNoParams<out Type> where Type : Any {
    abstract operator fun invoke(): Flow<CustomResult<Type>>
}