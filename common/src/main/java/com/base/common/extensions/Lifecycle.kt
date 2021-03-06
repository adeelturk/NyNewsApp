/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.common.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.base.common.error.ErrorEntity

fun <T : Any, L : LiveData<T>> AppCompatActivity.observe(liveData: L, body: (T?) -> Unit) =
        liveData.observe(this, Observer(body))
fun <L : LiveData<ErrorEntity>> AppCompatActivity.failure(liveData: L, body: (ErrorEntity?) -> Unit) =
        liveData.observe(this, Observer(body))

fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, body: (T) -> Unit) =
        liveData.observe(this, Observer(body))

fun <T : Any, L : LiveData<T>> Fragment.removeObserver(liveData: L) =
        liveData.removeObservers(this)
@Suppress("unused")
fun <L : LiveData<ErrorEntity>> Fragment.fault(liveData: L, body: (ErrorEntity?) -> Unit) =
        liveData.observe(viewLifecycleOwner, Observer(body))