package com.turk.newsapp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Subject(val scope: CoroutineScope) {
    fun foo() {
        scope.launch {
            return@launch
        }
    }
}