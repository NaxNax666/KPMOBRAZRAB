package com.example.kpmobrazrab.utils.build

import android.os.Build

object BuildUtils {

    fun isSdk33(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}
