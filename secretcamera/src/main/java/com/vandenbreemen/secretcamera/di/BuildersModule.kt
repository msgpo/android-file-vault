package com.vandenbreemen.secretcamera.di

import com.vandenbreemen.secretcamera.MainActivity
import com.vandenbreemen.secretcamera.NoteDetailsActivity
import com.vandenbreemen.secretcamera.TakeNoteActivity
import com.vandenbreemen.secretcamera.TakePictureActivity
import com.vandenbreemen.secretcamera.di.mvp.CameraPresenterModule
import com.vandenbreemen.secretcamera.di.mvp.NoteDetailsPresenterModule
import com.vandenbreemen.secretcamera.di.mvp.TakeNotePresenterModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by kevin on 24/03/18.
 */
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity():MainActivity

    @ContributesAndroidInjector(modules = [TakeNotePresenterModule::class])
    abstract fun bindTakeNoteActivity():TakeNoteActivity

    @ContributesAndroidInjector(modules = [NoteDetailsPresenterModule::class])
    abstract fun bindNoteDetailsActivity():NoteDetailsActivity

    @ContributesAndroidInjector(modules = [CameraPresenterModule::class])
    abstract fun bindTakePictureActivity(): TakePictureActivity

}