package com.base.common.base

import com.base.common.error.ErrorEntity
import com.base.common.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//by default the return type is Any no need to write this line is for learning purpose
//where Type : Any?
abstract class BaseUseCase<out Type, in Params>() {
    abstract suspend fun run(param: Params): Either<ErrorEntity, Type>
    operator fun invoke(viewModelScope: CoroutineScope, params: Params, onResult: (Either<ErrorEntity, Type>) -> Unit) {

        viewModelScope.launch {
            CoroutineScope(Dispatchers.IO)
                    .launch {
                        val result = run(params)

                        withContext(Main)
                        {
                            onResult(result)
                        }
                    }
        }
    }

    class None
}