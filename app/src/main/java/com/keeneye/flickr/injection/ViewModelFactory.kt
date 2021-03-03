package com.keeneye.flickr.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.keeneye.flickr.viewmodel.ImageListViewModel

/**
 * Factory class used to create view models with data parameters
 *
 * @param activity
 * @param text
 * @param page
 * @param limit
 */
class ViewModelFactory(private val activity: AppCompatActivity, private val text: String, private val page: Int, private val limit: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageListViewModel::class.java)) {
            return ImageListViewModel(text, page, limit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}