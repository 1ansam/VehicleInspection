package com.yxf.vehicleinspection

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 *   author:yxf
 *   time:2022/3/11
 */
class Test {
    suspend fun main() = coroutineScope {
        launch {
            val a = async {  }.await()
        }
    }
}