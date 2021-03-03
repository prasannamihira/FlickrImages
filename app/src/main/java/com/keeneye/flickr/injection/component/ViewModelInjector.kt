package com.keeneye.flickr.injection.component

import com.keeneye.flickr.injection.model.NetworkModule
import com.keeneye.flickr.viewmodel.ImageListViewModel
import com.keeneye.flickr.viewmodel.ImageViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    /**
     * Injects required dependencies into the specified ImageListViewModel.
     * @param ImageListViewModel ImageListViewModel in which to inject the dependencies
     */
    fun inject(ImageListViewModel: ImageListViewModel)

    /**
     * Injects required dependencies into the specified ImageViewModel.
     * @param imageViewModel ImageViewModel in which to inject the dependencies
     */
    fun inject(imageViewModel: ImageViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModel(networkModel: NetworkModule): Builder
    }
}