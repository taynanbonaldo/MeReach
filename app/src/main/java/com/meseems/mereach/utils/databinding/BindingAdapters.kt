package com.meseems.mereach.utils.databinding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * BindingAdapters.kt contains Databinding conversions and adapters
 */

@BindingConversion
fun setVisibility(state: Boolean): Int {
    return if (state) View.VISIBLE else View.GONE
}

@BindingAdapter("onRefresh")
fun onSwipeRefreshLayoutRefresh(iv: SwipeRefreshLayout, onRefreshCall: () -> Unit) {
    iv.setOnRefreshListener {
        onRefreshCall()
    }
}

@BindingAdapter("isRefreshing")
fun swipeRefreshLayoutIsRefreshing(iv: SwipeRefreshLayout, isRefreshing: Boolean) {
    iv.isRefreshing = isRefreshing
}
