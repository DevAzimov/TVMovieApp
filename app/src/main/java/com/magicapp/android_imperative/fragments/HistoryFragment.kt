package com.magicapp.android_imperative.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.magicapp.android_imperative.R
import com.magicapp.android_imperative.adapters.TVShowAdapter
import com.magicapp.android_imperative.databinding.FragmentHistoryBinding
import com.magicapp.android_imperative.models.TVShow
import com.magicapp.android_imperative.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    private val adapterTVShowAdapter by lazy { TVShowAdapter(requireContext(), ArrayList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getTvShowsFromDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserves()
    }

    private fun initObserves() {

        val lm = GridLayoutManager(requireContext(), 2)
        binding.rvHistory.layoutManager = lm
        binding.rvHistory.adapter = adapterTVShowAdapter
        viewModel.tvShowsFromDb.observe(viewLifecycleOwner) {
            if (it.size > 0){
                binding.tvEmptyMessage.visibility = View.GONE
            } else{
                binding.tvEmptyMessage.visibility = View.VISIBLE
            }
            adapterTVShowAdapter.setNewTVShows(it)
        }
    }
}