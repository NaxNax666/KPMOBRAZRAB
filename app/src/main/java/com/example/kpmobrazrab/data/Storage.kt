package com.example.kpmobrazrab.data

import android.graphics.Bitmap

object Storage {

    /**
     * Это должен быть класс хранилища, который инициализируется при старте приложения,
     * и который прокидывается во ViewModels фрагментов.
     *
     * Откуда фрагменты смогут подписыватсья на обновление значения этой картинки. Например.
     */
    var bitmap: Bitmap? = null
}