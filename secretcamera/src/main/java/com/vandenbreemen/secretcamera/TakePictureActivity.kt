package com.vandenbreemen.secretcamera

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vandenbreemen.secretcamera.fragment.Camera2BasicFragment

class TakePictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        supportFragmentManager.beginTransaction().replace(R.id.content, Camera2BasicFragment.newInstance()).commit()
    }
}
