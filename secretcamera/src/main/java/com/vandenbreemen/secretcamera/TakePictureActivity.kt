package com.vandenbreemen.secretcamera

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.vandenbreemen.mobilesecurestorage.android.sfs.SFSCredentials
import com.vandenbreemen.mobilesecurestorage.message.ApplicationError
import com.vandenbreemen.secretcamera.fragment.Camera2BasicFragment
import com.vandenbreemen.secretcamera.fragment.CameraListener
import com.vandenbreemen.secretcamera.mvp.pictures.CameraPresenter
import com.vandenbreemen.secretcamera.mvp.pictures.CameraView
import dagger.android.AndroidInjection
import javax.inject.Inject

class TakePictureActivity : AppCompatActivity(), CameraListener, CameraView {

    @Inject
    lateinit var presenter: CameraPresenter

    override fun done(credentials: SFSCredentials) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SFSCredentials.PARM_CREDENTIALS, credentials)
        startActivity(intent)
        finish()
    }

    override fun onReadyToUse() {

    }

    override fun showError(error: ApplicationError) {
        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onSnapPhoto(image: Image) {
        presenter.snapPhoto(image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)

        supportFragmentManager.beginTransaction().replace(R.id.content, Camera2BasicFragment.newInstance()).commit()
    }
}
