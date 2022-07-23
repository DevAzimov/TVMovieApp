package com.magicapp.android_imperative.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magicapp.android_imperative.R
import com.magicapp.android_imperative.activity.MainActivity
import com.magicapp.android_imperative.databinding.ItemTvShowBinding
import com.magicapp.android_imperative.models.TVShow

class TVShowAdapter(var activity: Context, var items: ArrayList<TVShow>) : BaseAdapter() {

    var onClick: ((TVShow, ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow: TVShow = items[position]
        if (holder is TVShowViewHolder) {
            Glide.with(activity).load(tvShow.image_thumbnail_path).into(holder.binding.ivMovie)
            holder.binding.tvName.text = tvShow.name
            holder.binding.tvType.text = tvShow.network

            ViewCompat.setTransitionName(holder.binding.ivMovie, tvShow.name)
            holder.binding.ivMovie.setOnClickListener {
                onClick?.invoke(tvShow, holder.binding.ivMovie)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewTVShows(tvShows: ArrayList<TVShow>) {
        items.addAll(tvShows)
        notifyDataSetChanged()
    }



    inner class TVShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvShowBinding.bind(view)
    }
}