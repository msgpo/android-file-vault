package com.vandenbreemen.secretcamera.mvp.impl.pictures

import android.media.Image
import com.vandenbreemen.mobilesecurestorage.android.sfs.SFSCredentials
import com.vandenbreemen.mobilesecurestorage.file.ChainedDataUnit
import com.vandenbreemen.mobilesecurestorage.file.FileMeta
import com.vandenbreemen.mobilesecurestorage.file.api.FileType
import com.vandenbreemen.mobilesecurestorage.file.api.FileTypes
import com.vandenbreemen.mobilesecurestorage.log.SystemLog
import com.vandenbreemen.mobilesecurestorage.log.d
import com.vandenbreemen.mobilesecurestorage.patterns.mvp.Model
import com.vandenbreemen.mobilesecurestorage.security.Entropy
import com.vandenbreemen.mobilesecurestorage.security.crypto.setFileMetadata
import com.vandenbreemen.secretcamera.api.SEC_CAM_BYTE
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

enum class PictureFileTypes(override val firstByte: Byte, override val secondByte: Byte? = null) : FileType {

    SNAPPED_PHOTO(SEC_CAM_BYTE, 10),
    IMPORTED(SEC_CAM_BYTE, 11)

    ;

    //  Register these file types!
    companion object {
        init {
            FileTypes.registerFileTypes(values())
        }
    }

}

/**
 * <h2>Intro</h2>
 * App logic for storing photos snapped with the camera
 * <h2>Other Details</h2>
 * @author kevin
 */
class CameraModel(val credentials: SFSCredentials) : Model(credentials) {

    companion object {
        const val TAG = "PhotoStorage"
    }

    override fun onClose() {

    }

    override fun setup() {

    }

    /**
     * Store the given image to file
     */
    fun storeImage(image: Image): Single<Unit> {
        val fileName = "IMG_${System.currentTimeMillis()}"
        return Single.create(SingleOnSubscribe<Unit> {
            val buffer = image.planes[0].buffer
            val bytes = ByteArray(buffer.remaining())
            buffer.get(bytes)
            val unit = ChainedDataUnit()
            unit.data = bytes
            sfs.storeObject(fileName, unit)
            sfs.setFileMetadata(fileName, FileMeta(PictureFileTypes.SNAPPED_PHOTO))
            Entropy.get().randomFillBytes(bytes)
            Entropy.get().randomFillBytes(unit.data)
            SystemLog.get().d(TAG, "Successfully stored image")
            it.onSuccess(Unit)
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}