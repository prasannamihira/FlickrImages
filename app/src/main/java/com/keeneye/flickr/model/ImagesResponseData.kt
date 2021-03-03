package com.keeneye.flickr.model

/**
 * Images response data
 *
 * @param photos
 */
data class ImagesResponse(val photos: ImagesListData)

/**
 * Images list
 *
 * @param page
 * @param photo
 */
data class ImagesListData(
    val page: Int,
    val photo: List<ImageListItem>
)

/**
 * Image item
 *
 * @param id
 * @param owner
 * @param secret
 * @param server
 * @param farm
 * @param title
 */
data class ImageListItem(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String
)

/**
 * Image path
 *
 * @param id
 * @param url
 */
data class Image(val id: String, val url: String)