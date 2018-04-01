package com.vandenbreemen.secretcamera.mvp.pictures

import android.media.Image
import com.vandenbreemen.mobilesecurestorage.android.sfs.SFSCredentials
import com.vandenbreemen.mobilesecurestorage.patterns.mvp.PresenterContract
import com.vandenbreemen.mobilesecurestorage.patterns.mvp.View

/**
 * <h2>Intro</h2>
 * Standard APIs for storing/loading images
 * <h2>Other Details</h2>
 * @author kevin
 */
interface CameraPresenter : PresenterContract {

    /**
     * Take picture
     */
    fun snapPhoto(image: Image)

}

interface CameraView : View {

    /**
     * Signal the view we're done - go back to main or whatever
     */
    fun done(credentials: SFSCredentials)

}