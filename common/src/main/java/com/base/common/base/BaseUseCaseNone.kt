package com.base.common.base

import com.base.common.error.ErrorEntity
import com.base.common.functional.Either
import kotlinx.coroutines.*

abstract class BaseUseCaseNone<out Type>(private val ioScope: CoroutineScope, val main : CoroutineDispatcher = Dispatchers.Main) where Type : Any? {
    abstract suspend fun run(): Either<ErrorEntity, Type>
    operator fun invoke( onResult: (Either<ErrorEntity, Type>) -> Unit) {
        ioScope.launch {
            val result = run()
            withContext(main)
            {
                onResult(result)
            }
        }
    }

}