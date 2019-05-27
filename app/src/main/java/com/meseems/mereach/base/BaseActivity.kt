package com.meseems.mereach.base

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.meseems.mereach.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein


abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    val parentKodein by kodein()

    @CallSuper
    open fun initUi() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    fun checkGrantedPermissions(grantResults: IntArray) =
        grantResults.all { it == PackageManager.PERMISSION_GRANTED }

    fun isGrantedPermission(permission: String): Boolean {
        val result = ActivityCompat.checkSelfPermission(this, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}
