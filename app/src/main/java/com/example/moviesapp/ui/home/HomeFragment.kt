package com.example.moviesapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.ui.home.adapters.GenresAdapter
import com.example.moviesapp.ui.home.adapters.LastMoviesAdapter
import com.example.moviesapp.ui.home.adapters.TopMoviesAdapter
import com.example.moviesapp.utils.initRecycler
import com.example.moviesapp.utils.showInVisible
import com.example.moviesapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var genresAdapter: GenresAdapter

    @Inject
    lateinit var lastMoviesAdapter:LastMoviesAdapter

    //other
    private val viewModel:HomeViewModel by viewModels()
    private val pagerHelper:PagerSnapHelper by lazy { PagerSnapHelper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //call api
        viewModel.loadTopMoviesList(3)
        viewModel.loadGenresList()
        viewModel.loadLastMoviesList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initViews
        binding.apply {
            //get topMovies
            viewModel.topMoviesList.observe(viewLifecycleOwner){
                topMoviesAdapter.differ.submitList(it.data)
                //recyclerView
                topMoviesRecycler.initRecycler(
                    LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false),
                    topMoviesAdapter
                )
                //indicator
                pagerHelper.attachToRecyclerView(topMoviesRecycler)
                topMoviesIndicator.attachToRecyclerView(topMoviesRecycler,pagerHelper)
            }
            //get genres
            viewModel.genresList.observe(viewLifecycleOwner){
                genresAdapter.differ.submitList(it)
                //recyclerView
                genresRecycler.initRecycler(
                    LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false),
                    genresAdapter
                )
            }
            //get lastMovies
            viewModel.lastMoviesList.observe(viewLifecycleOwner){
                lastMoviesAdapter.setData(it.data)
                //recyclerView
                lastMoviesRecycler.initRecycler(
                    LinearLayoutManager(requireContext()),
                    lastMoviesAdapter
                )
            }
            //click
            lastMoviesAdapter.setOnItemClickListener {
                val direction =HomeFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    moviesLoading.showInVisible(true)
                    moviesScrollLay.showInVisible(false)
                }else{
                    moviesLoading.showInVisible(false)
                    moviesScrollLay.showInVisible(true)
                }
            }
        }
    }
}