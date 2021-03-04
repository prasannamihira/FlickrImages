package com.keeneye.flickr.ui.images

import com.keeneye.flickr.viewmodel.ImageListViewModel
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class ImageListViewModelUnitTest {

    private val search: String = "Animals"
    private val page: Int = 1
    private val limit: Int = 10

    @Mock
    var viewModel = ImageListViewModel(search, page, limit)

    @Test
    fun testLoadImages_response_success() {

        viewModel.loadImages(search, page, limit)
    }
}