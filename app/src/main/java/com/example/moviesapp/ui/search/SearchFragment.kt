package com.example.moviesapp.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.databinding.FragmentSearchBinding
import com.example.moviesapp.ui.favorite.FavoriteFragmentDirections
import com.example.moviesapp.ui.home.adapters.LastMoviesAdapter
import com.example.moviesapp.utils.initRecycler
import com.example.moviesapp.utils.showInVisible
import com.example.moviesapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var searchAdapter: LastMoviesAdapter

    //other
    private val viewModel:SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initView
        binding.apply {
            //search
            searchEdt.addTextChangedListener {
                val search = it.toString()
                if (search.isNotEmpty()) {
                    viewModel.loadSearchMovies(search)
                }
            }
            //get movies list
            viewModel.moviesList.observe(viewLifecycleOwner){
                searchAdapter.setData(it.data)
                moviesRecycler.initRecycler(LinearLayoutManager(requireContext()),searchAdapter)
            }
            //click
            searchAdapter.setOnItemClickListener {
                val direction = SearchFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    searchLoading.showInVisible(true)
                }else{
                    searchLoading.showInVisible(false)
                }
            }
            //empty items
            viewModel.empty.observe(viewLifecycleOwner){
                if (it){
                    emptyItemsLay.showInVisible(true)
                    moviesRecycler.showInVisible(false)
                }else{
                    emptyItemsLay.showInVisible(false)
                    moviesRecycler.showInVisible(true)
                }
            }
            
        }
    }
}