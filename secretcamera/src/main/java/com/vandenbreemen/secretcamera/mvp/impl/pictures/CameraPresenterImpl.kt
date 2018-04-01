package com.vandenbreemen.secretcamera.mvp.impl.pictures

import android.media.Image
import com.vandenbreemen.mobilesecurestorage.log.SystemLog
import com.vandenbreemen.mobilesecurestorage.log.e
import com.vandenbreemen.mobilesecurestorage.message.ApplicationError
import com.vandenbreemen.mobilesecurestorage.patterns.mvp.Presenter
import com.vandenbreemen.secretcamera.mvp.pictures.CameraPresenter
import com.vandenbreemen.secretcamera.mvp.pictures.CameraView

/**
 * <h2>Intro</h2>
 *
 * <h2>Other Details</h2>
 * @author kevin
 */
class CameraPresenterImpl(val model: CameraModel, val view: CameraView) : Presenter<CameraModel, CameraView>(model, view), CameraPresenter {
    override fun snapPhoto(image: Image) {
        model.storeImage(image).subscribe({
            view.done(model.copyCredentials())
        }, {
            SystemLog.get().e("CameraPresenter", "Failed to snap photo", it)
            view.showError(ApplicationError("Unknown Error Occurred"))
        })
    }

    override fun setupView() {

    }


}