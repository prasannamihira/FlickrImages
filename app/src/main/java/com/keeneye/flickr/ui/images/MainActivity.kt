package com.keeneye.flickr.ui.images

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.keeneye.flickr.R
import com.keeneye.flickr.databinding.ActivityMainBinding
import com.keeneye.flickr.injection.ViewModelFactory
import com.keeneye.flickr.model.ImageListItem
import com.keeneye.flickr.viewmodel.ImageListViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ImageListViewModel
    private lateinit var layoutManager: GridLayoutManager

    private lateinit var mRunnable: Runnable
    private lateinit var mHandler: Handler

    companion object {
        // paginated image list
        var imageListPaginated = mutableListOf<ImageListItem>()

        private var searchText: String = "technology"

        // pagination parameters
        private var page: Int = 1
        var limit: Int = 10
        var isLoading: Boolean = false
        var isLoadingEnd: Boolean = false

        // adapter for search texts history
        lateinit var adapter: ArrayAdapter<String>
        var searchHistory = mutableListOf<String>()
    }

    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // grid layout manager with two columns
        layoutManager = GridLayoutManager(this, 2)
        binding.rvImages.layoutManager = layoutManager

        // Initialize the handler instance
        mHandler = Handler()

        // get the view model going to attached to the view
        viewModel =
            ViewModelProviders.of(
                this,
                ViewModelFactory(this, searchText, page, limit)
            ).get(ImageListViewModel::class.java)

        // error action
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        // Bind view model data to images list layout
        binding.viewModel = viewModel

        /****************************************************/
        /**** Add refresh listener to  the recycler view ****/
        /****************************************************/
        binding.srlImages.setOnRefreshListener {
            // Initialize a new Runnable
            mRunnable = Runnable {

                // Hide swipe to refresh icon animation
                binding.srlImages.isRefreshing = false

                // Bind view model data to images list layout
                binding.viewModel = viewModel
            }

            // Execute the task after specified time
            mHandler.postDelayed(
                mRunnable,
                500
            )
        }

        /***************************************************/
        /**** Add scroll listener to  the recycler view ****/
        /***************************************************/
        binding.rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (totalItemCount > 1) {
                    if (lastVisibleItem >= totalItemCount - 1) {
                        if (!isLoading && !isLoadingEnd) {
                            if (layoutManager.findLastCompletelyVisibleItemPosition() == imageListPaginated.size - 1) {
                                page += 1
                                viewModel.loadImages(searchText, page, limit)
                                isLoading = true
                            }
                        }
                    }
                }
            }
        })

        // add on click listeners
        binding.btnSearch.setOnClickListener(this)
    }

    /**
     * Show error message using snack bar
     *
     * @param errorMessage
     */
    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    /**
     * Hide error message
     */
    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_search -> {
                    // get search key
                    searchText = binding.actvSearch.text.toString()

                    // add keyword to search history
                    searchHistory.add(searchText)

                    binding.actvSearch.threshold = 1
                    //Creating the instance of ArrayAdapter containing list of search strings
                    adapter = ArrayAdapter<String>(
                        this,
                        android.R.layout.select_dialog_item,
                        searchHistory
                    )
                    binding.actvSearch.setAdapter(adapter)

                    // reset pagination
                    page = 1
                    imageListPaginated.clear()

                    // search images
                    viewModel.loadImages(searchText, page, limit)
                }
            }
        }
    }
}