package com.vandenbreemen.secretcamera

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager

/**
 *
 * @author kevin
 */
class SFSActionsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_sfs_actions)
    }

}