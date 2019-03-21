package com.sumitt.findhome.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sumitt.findhome.R
import com.sumitt.findhome.model.Homes

/**
 * Adapter to show listing
 * @author sumit.T
 * */
class ListingAdapter(private val activity: FragmentActivity?, private var listing: List<Homes>) : RecyclerView.Adapter<ListingAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return if (listing != null) {
            listing?.size
        } else 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageGallery: LinearLayout
        var price: TextView
        var address: TextView
        var no_of_bedrooms: TextView
        var no_of_bathrooms: TextView

        init {
            imageGallery = view.findViewById(R.id.gallery_view)
            price = view.findViewById(R.id.price)
            address = view.findViewById(R.id.address)
            no_of_bedrooms = view.findViewById(R.id.no_of_bedrooms)
            no_of_bathrooms = view.findViewById(R.id.no_of_bathrooms)
        }
    }

    fun setHomes(items: List<Homes>) {
        this.listing = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingAdapter.ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.listing_item_list_view, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = 20
        with(listing[position]) {
            holder.imageGallery.tag = position
            setImages(context, holder.imageGallery, photos)
            holder.price.setText(context?.getString(R.string.price_label, price))
            holder.address.setText(context?.getString(R.string.address_label, streetNumber, streetName, city, stateCode))
            holder.no_of_bedrooms.setText(context?.getString(R.string.bedroom_label, bedrooms))
            holder.no_of_bathrooms.setText(context?.getString(R.string.bathroom_label, bathrooms))
        }
    }

    private fun setImages(context: Context, galleryLayout: LinearLayout, photos: ArrayList<String>) {
        galleryLayout.removeAllViews()
        photos?.forEach {
            activity?.apply {
                Log.d("Sumit: ", "url: $it")
                Glide.with(this)
                        .load(it)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                Log.d("Sumit: ", "onLoadFailed")
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                Log.d("Sumit: ", "onResourceReady")
                                val view = LayoutInflater.from(context).inflate(R.layout.gallery_item_view, galleryLayout, false)
                                val imageView: ImageView = view.findViewById(R.id.gallery_item_image)

                                galleryLayout?.apply {
                                    imageView.setImageDrawable(resource)
                                    addView(view)
                                }
                                return false
                            }
                        })
                        .submit()
            }
        }
    }
}