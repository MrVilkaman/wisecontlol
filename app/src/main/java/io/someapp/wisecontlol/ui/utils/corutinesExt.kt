package io.someapp.wisecontlol.ui.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun <T> withIO(
        block: suspend CoroutineScope.() -> T
): T = withContext(Dispatchers.IO, block)