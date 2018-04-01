package com.vandenbreemen.secretcamera.di.mvp

import com.vandenbreemen.mobilesecurestorage.android.sfs.SFSCredentials
import com.vandenbreemen.secretcamera.TakePictureActivity
import com.vandenbreemen.secretcamera.mvp.impl.pictures.CameraModel
import com.vandenbreemen.secretcamera.mvp.impl.pictures.CameraPresenterImpl
import com.vandenbreemen.secretcamera.mvp.pictures.CameraPresenter
import dagger.Module
import dagger.Provides

/**
 * <h2>Intro</h2>
 *
 * <h2>Other Details</h2>
 * @author kevin
 */
@Module
class CameraPresenterModule {

    @Provides
    fun providePresenter(activity: TakePictureActivity): CameraPresenter {
        return CameraPresenterImpl(CameraModel(activity.intent.getParcelableExtra(SFSCredentials.PARM_CREDENTIALS)), activity)
    }

}