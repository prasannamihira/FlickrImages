package com.keeneye.flickr.repository

import com.keeneye.flickr.model.ImagesResponse
import com.keeneye.flickr.network.ApiService
import com.keeneye.flickr.utils.Config
import javax.inject.Inject

class ImagesApiRepository @Inject constructor(
    private val apiService: ApiService
) {
    var dataImageList: ImagesResponse? = null
    var dataImagesRetrieved: Boolean = false
    var errorCode: String = ""
    var errorMessage: String = ""

    /**
     * Fetch images list by injecting api service
     *
     * @param searchKey
     * @param page
     * @param limit
     */
    fun fetchImagesList(searchKey: String?, page: Int, limit: Int) =
        apiService.fetchImagesList(searchKey, page, limit)
            .map {
                if (it.isSuccessful && it.code() == Config.RESPONSE_SUCCESS) {
                    dataImageList = null
                    dataImageList = it.body()
                    dataImagesRetrieved = true
                } else {
                    // error
                    try {
                        errorCode = it.code().toString()
                        errorMessage = it.message()
                    } catch (e: Exception) {
                        e.message.toString()
                    }
                }
            }
}