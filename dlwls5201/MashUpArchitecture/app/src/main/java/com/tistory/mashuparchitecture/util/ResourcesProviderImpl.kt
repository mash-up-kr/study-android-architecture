
package com.tistory.mashuparchitecture.util

import android.content.Context

class ResourcesProviderImpl(private val context: Context) :
    ResourcesProvider {
    override fun getQuantityString(pluralsId: Int, quantity1: Int, quantity2: Int) =
        context.resources.getQuantityString(pluralsId, quantity1, quantity2)

    override fun getString(resId: Int) = context.getString(resId)

}