package com.keeneye.flickr.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.keeneye.flickr.R
import com.keeneye.flickr.base.BaseViewModel
import com.keeneye.flickr.model.ImageListItem
import com.keeneye.flickr.model.ImagesListData
import com.keeneye.flickr.model.ImagesResponse
import com.keeneye.flickr.network.ApiService
import com.keeneye.flickr.repository.ImagesApiRepository
import com.keeneye.flickr.ui.images.MainActivity
import com.keeneye.flickr.ui.images.adapter.ImageListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageListViewModel(private val text: String?, private val page: Int, private val limit: Int) : BaseViewModel() {

    @Inject
    lateinit var locksRepository: ImagesApiRepository

    @Inject
    lateinit var flickrApi: ApiService

    // image list
    var imageList = mutableListOf<ImageListItem>()

    val imageListAdapter: ImageListAdapter = ImageListAdapter(MainActivity.imageListPaginated)
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadImages(text, page, limit) }

    private val subscription = CompositeDisposable()

    init {
        loadImages(text, page, limit)
    }

    /**
     * Fetch images by text, page number and items per page
     *
     * @param text
     * @param page
     * @param limit
     */
    fun loadImages(text: String?, page: Int, limit: Int) {
        imageList.clear()

        subscription.add(locksRepository.fetchImagesList(text, page, limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveImageListStart() }
            .doOnTerminate { onRetrieveImageListFinish() }
            .subscribe {
                if (it != null && locksRepository.dataImagesRetrieved) {

                    if (locksRepository.dataImageList?.photos?.photo != null) {
                        imageList =
                            locksRepository.dataImageList?.photos?.photo?.toCollection(ArrayList()) as ArrayList<ImageListItem>

                        MainActivity.imageListPaginated.addAll(imageList)
                    }

                    MainActivity.isLoading = false

                    if(imageList.size < MainActivity.limit) {
                        MainActivity.isLoadingEnd = true
                    }

                    onRetrieveImageListSuccess()
                } else {
                    onRetrieveImageListError()
                }
            })
    }

    // do when process start
    private fun onRetrieveImageListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    // do when process completed
    private fun onRetrieveImageListFinish() {
        loadingVisibility.value = View.GONE
    }

    // when success process response, notify data items to the list adapter
    private fun onRetrieveImageListSuccess() {
        imageListAdapter.updateImageList()
    }

    // when error occurred, show message using snack bar
    private fun onRetrieveImageListError() {
        errorMessage.value = R.string.image_loading_error
    }

    // clear and close the subscription
    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}