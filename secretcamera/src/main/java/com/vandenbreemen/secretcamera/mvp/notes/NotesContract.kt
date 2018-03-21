package com.vandenbreemen.secretcamera.mvp.notes

import com.vandenbreemen.mobilesecurestorage.patterns.mvp.PresenterContract
import com.vandenbreemen.mobilesecurestorage.patterns.mvp.View

/**
 * <h2>Intro</h2>
 *
 * <h2>Other Details</h2>
 * @author kevin
 */
interface TakeNewNotePresenter : PresenterContract {

    fun provideNoteDetails(title: String?, note: String?)

    fun onCancel()

}

interface TakeNewNoteView : View {

    fun onNoteSucceeded(message: String)

    fun close()
}