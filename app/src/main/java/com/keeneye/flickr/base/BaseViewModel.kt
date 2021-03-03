package com.keeneye.flickr.base

import androidx.lifecycle.ViewModel
import com.keeneye.flickr.injection.component.DaggerViewModelInjector
import com.keeneye.flickr.injection.component.ViewModelInjector
import com.keeneye.flickr.injection.model.NetworkModule
import com.keeneye.flickr.viewmodel.ImageListViewModel
import com.keeneye.flickr.viewmodel.ImageViewModel

abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModel(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ImageListViewModel -> injector.inject(this)
            is ImageViewModel -> injector.inject(this)
        }
    }
}