package com.keeneye.flickr.ui.images.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.keeneye.flickr.R
import com.keeneye.flickr.databinding.ImageBinding
import com.keeneye.flickr.model.Image
import com.keeneye.flickr.model.ImageListItem
import com.keeneye.flickr.viewmodel.ImageViewModel

/**
 * Adapter bind list of image items to the recycler view
 *
 * @param imageList
 */
class ImageListAdapter(private val imageList: List<ImageListItem>) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ImageBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.image,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun updateImageList() {
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ImageViewModel()

        fun bind(image: ImageListItem) {
            var id = image.id
            var url: String = "https://farm${image.farm}.staticflickr.com/${image.server}/${image.id}_${image.secret}.jpg"
            var image = Image(id, url)
            viewModel.bind(image)
            binding.viewModel = viewModel
        }
    }
}