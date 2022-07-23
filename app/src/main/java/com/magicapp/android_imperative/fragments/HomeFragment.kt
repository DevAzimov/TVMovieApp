package com.magicapp.android_imperative.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.magicapp.android_imperative.R
import com.magicapp.android_imperative.activity.DetailsActivity
import com.magicapp.android_imperative.adapters.TVShowAdapter
import com.magicapp.android_imperative.databinding.FragmentHomeBinding
import com.magicapp.android_imperative.models.TVShow
import com.magicapp.android_imperative.utils.Logger
import com.magicapp.android_imperative.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private  var _binding: FragmentHomeBinding? = null
    private val TAG = this::class.java.simpleName
    private val viewModel: MainViewModel by viewModels()
    lateinit var adapter: TVShowAdapter

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apiTVShowPopular(1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        initObserves()
        val lm = GridLayoutManager(requireContext(), 2)
        binding.rvHome.layoutManager = lm
        refreshAdapter(ArrayList())
        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (lm.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    val totalPage = viewModel.tvShowPopular.value!!.pages
                    if (nextPage <= totalPage) {
                        viewModel.apiTVShowPopular(nextPage)
                    }
                }
            }
        })
        binding.bFab.setOnClickListener {
            binding.rvHome.scrollToPosition(0)
        }
    }

    fun refreshAdapter(items: ArrayList<TVShow>) {
        adapter = TVShowAdapter(requireContext(), items)
        binding.rvHome.adapter = adapter
        adapter.onClick = {tvShow, imageView ->
            callDetailsActivity(tvShow, imageView)
            viewModel.insertTVShowToDB(tvShow)
        }
    }

    private fun initObserves() {
        // Retrofit Related

        viewModel.tvShowsFromApi.observe(viewLifecycleOwner) {
            Logger.d(TAG, it!!.size.toString())
            adapter.setNewTVShows(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }

        //Room Related

    }


    fun callDetailsActivity(tvShow: TVShow, sharedImageView: ImageView) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("show_id", tvShow.id)
        intent.putExtra("show_img", tvShow.image_thumbnail_path)
        intent.putExtra("show_name", tvShow.name)
        intent.putExtra("show_network", tvShow.network)
        intent.putExtra("iv_movie", ViewCompat.getTransitionName(sharedImageView))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(), sharedImageView, ViewCompat.getTransitionName(sharedImageView)!!
        )
        startActivity(intent, options.toBundle())
    }


}