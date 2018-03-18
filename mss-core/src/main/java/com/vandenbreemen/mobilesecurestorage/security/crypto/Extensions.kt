package com.vandenbreemen.mobilesecurestorage.security.crypto

import com.vandenbreemen.mobilesecurestorage.file.FileMeta
import com.vandenbreemen.mobilesecurestorage.security.crypto.persistence.SecureFileSystem
import java.io.Serializable
import java.util.function.Supplier

//  SFS extensions in Kotlin

/**
 * Filename to store the metadata in
 */
private const val METADATA_FILENAME = ".__FILEMETADATA"

private class FileMetadata : Serializable {

    private val anotherMap = hashMapOf<String, FileMeta>()

    fun setMetadata(fileName: String, metadata: FileMeta) {
        anotherMap[fileName] = metadata
    }

    fun getMetadata(filename: String): FileMeta? {
        return this.anotherMap[filename]
    }

}

/**
 * Specificy metadata for the given file
 */
fun SecureFileSystem.setFileMetadata(fileName: String, metadata: FileMeta) {
    var fileMetadata: FileMetadata
    if (!this.exists(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME)) {
        fileMetadata = FileMetadata()
        this.storeObject(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME, fileMetadata)
    } else {
        fileMetadata = this.loadFile(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME) as FileMetadata
    }

    fileMetadata.setMetadata(fileName, metadata)
    this.storeObject(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME, fileMetadata)
}

/**
 * Gets the file metadata for the given file
 */
fun SecureFileSystem.getFileMeta(fileName: String): FileMeta? {
    if (!this.exists(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME)) {
        return null
    }

    val metadata = this.loadFile(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME) as FileMetadata
    return metadata.getMetadata(fileName)
}

fun SecureFileSystem.getFileMeta(fileName: String, provider: Supplier<FileMeta>): FileMeta {
    if (!this.exists(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME)) {
        this.storeObject(com.vandenbreemen.mobilesecurestorage.security.crypto.METADATA_FILENAME, FileMetadata())
    }

    var meta = getFileMeta(fileName)
    meta ?: kotlin.run {
        meta = provider.get()
        setFileMetadata(fileName, meta!!)
        return@getFileMeta meta!!
    }

    return meta!!
}

/**
 * List files, ignoring any special files required to make the extensions work
 */
fun SecureFileSystem.extListFiles(): List<String> {
    return this.listFiles().filter { !METADATA_FILENAME.equals(it) }
}