package com.meseems.mereach.utils.extensions.collections

import com.meseems.mereach.base.BaseViewModel

inline fun <reified T> Map<String, BaseViewModel>.getTyped(key: String): T = this[key] as T