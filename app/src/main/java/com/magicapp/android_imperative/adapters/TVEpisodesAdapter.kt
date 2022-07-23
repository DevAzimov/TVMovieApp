package com.magicapp.android_imperative.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magicapp.android_imperative.R
import com.magicapp.android_imperative.activity.DetailsActivity
import com.magicapp.android_imperative.databinding.ItemTvEpisodesBinding
import com.magicapp.android_imperative.databinding.ItemTvShortBinding
import com.magicapp.android_imperative.models.Episode

class TVEpisodesAdapter(var activity: DetailsActivity, var items: List<Episode>) :
    BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_episodes, parent, false)
        return TVEpisodesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is TVEpisodesViewHolder) {
            holder.binding.tvEpisodes.text = "Episodes: ${item.episode}   Name: ${item.name}"
            holder.binding.tvAirDate.text = "Air Date: ${item.air_date}"
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TVEpisodesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvEpisodesBinding.bind(view)
    }

}
