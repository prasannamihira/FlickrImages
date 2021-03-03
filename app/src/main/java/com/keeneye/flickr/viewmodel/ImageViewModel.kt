package com.keeneye.flickr.viewmodel

import androidx.lifecycle.MutableLiveData
import com.keeneye.flickr.base.BaseViewModel
import com.keeneye.flickr.model.Image

class ImageViewModel: BaseViewModel() {

    private val imageUrl = MutableLiveData<String>()

    fun bind(image: Image) {
        imageUrl.value = image.url
    }

    fun getImageUrl(): MutableLiveData<String> {
        return imageUrl
    }
}