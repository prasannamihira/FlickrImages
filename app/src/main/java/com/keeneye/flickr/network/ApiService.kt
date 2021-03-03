package com.keeneye.flickr.network

import com.keeneye.flickr.model.ImagesResponse
import com.keeneye.flickr.utils.Config
import io.reactivex.Flowable
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * fetch flickr images list
     *
     * @param searchKey
     * @param page
     * @param limit
     */
    @Headers(Config.CONTENT_TYPE_JSON)
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=873aa7a6882640372aa70014d983d242")
    fun fetchImagesList(
        @Query("text") searchKey: String?,
        @Query("page") page: Int,
        @Query("per_page") limit: Int
    ): Flowable<Response<ImagesResponse>>
}