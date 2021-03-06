package com.vandenbreemen.secretcamera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.camerakit.CameraKitView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vandenbreemen.mobilesecurestorage.android.sfs.SFSCredentials
import com.vandenbreemen.mobilesecurestorage.message.ApplicationError
import com.vandenbreemen.secretcamera.di.injectTakePicture
import com.vandenbreemen.secretcamera.mvp.takepicture.TakePicturePresenter
import com.vandenbreemen.secretcamera.mvp.takepicture.TakePictureView
import javax.inject.Inject

class TakePictureActivity : Activity(), TakePictureView {


    companion object {
        const val TAG = "TakePictureActivity"
    }

    lateinit var cameraView: CameraKitView

    @Inject
    lateinit var presenter: TakePicturePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectTakePicture(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)
        this.cameraView = findViewById(R.id.camera)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
        cameraView.onResume()
    }

    override fun onPause() {
        cameraView.onPause()
        super.onPause()
        presenter.close()
        finish()
    }

    override fun onReadyToUse() {


        findViewById<FloatingActionButton>(R.id.takePicture).setOnClickListener(View.OnClickListener { v ->
            cameraView.captureImage(CameraKitView.ImageCallback { cameraKitView, bytes -> presenter.capture(bytes) })
        })
    }

    override fun showError(error: ApplicationError) {
        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.back()
    }

    override fun returnToMain(credentials: SFSCredentials) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SFSCredentials.PARM_CREDENTIALS, credentials)
        startActivity(intent)
    }
}
