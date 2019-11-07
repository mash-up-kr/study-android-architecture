package com.tistory.mashuparchitecture.di

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourcesProvider {
    fun getString(@StringRes resId: Int): String

    fun getQuantityString(
        @PluralsRes pluralsId: Int,
        quantity1: Int,
        quantity2: Int
    ): String?
}

