package com.magicapp.android_imperative.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.magicapp.android_imperative.R
import com.magicapp.android_imperative.adapters.TVEpisodesAdapter
import com.magicapp.android_imperative.adapters.TVShortAdapter
import com.magicapp.android_imperative.databinding.ActivityDetailsBinding
import com.magicapp.android_imperative.models.Episode
import com.magicapp.android_imperative.utils.Logger
import com.magicapp.android_imperative.viewmodels.DetailsViewModel

class DetailsActivity : BaseActivity() {
    private val TAG = DetailsActivity::class.java.simpleName
    val viewModel: DetailsViewModel by viewModels()
    lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {

        initObserves()

        binding.rvShorts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }

        binding.rvEpisodes.layoutManager = GridLayoutManager(this, 1)

        val extras = intent.extras
        val show_id = extras!!.getLong("show_id")
        val show_img = extras.getString("show_img")
        val show_name = extras.getString("show_name")
        val show_network = extras.getString("show_network")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val imageTransitionName = extras.getString("iv_movie")
            binding.ivDetails.transitionName = imageTransitionName
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(binding.ivDetails)

        viewModel.apiTVShowDetails(show_id.toInt())
    }

    private fun initObserves() {
        viewModel.tvShowDetails.observe(this) {
            Logger.d(TAG, it.toString())
            refreshAdapter(it.tvShow.pictures)
            binding.tvDetails.text = it.tvShow.description
            refreshEpisodesAdapter(it.tvShow.episodes)
        }
        viewModel.errorMessage.observe(this) {
            Logger.d(TAG, it.toString())
        }
        viewModel.isLoading.observe(this){
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            }else{
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun refreshAdapter(items: List<String>) {
        val adapter = TVShortAdapter(this, items)
        binding.rvShorts.adapter = adapter
    }

    private fun refreshEpisodesAdapter(items: List<Episode>) {
        val adapter = TVEpisodesAdapter(this, items)
        binding.rvEpisodes.adapter = adapter
    }
}