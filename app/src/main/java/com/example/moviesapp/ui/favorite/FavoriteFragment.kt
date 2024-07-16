package com.example.moviesapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentFavoriteBinding
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.ui.home.HomeFragmentDirections
import com.example.moviesapp.utils.initRecycler
import com.example.moviesapp.utils.showInVisible
import com.example.moviesapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    //binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //other
    private val viewModel:FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initViews
        binding.apply {
            //show all favorites
            viewModel.loadFavoriteList()
            //list
            viewModel.favoriteList.observe(viewLifecycleOwner){
                favoriteAdapter.setData(it)
                favoriteRecycler.initRecycler(LinearLayoutManager(requireContext()),favoriteAdapter)
            }
            //click
            favoriteAdapter.setOnItemClickListener {
                val direction = FavoriteFragmentDirections.actionToDetail(it.id)
                findNavController().navigate(direction)
            }
            //empty
            viewModel.empty.observe(viewLifecycleOwner){
                if (it){
                    emptyItemsLay.showInVisible(true)
                    favoriteRecycler.showInVisible(false)
                }else{
                    emptyItemsLay.showInVisible(false)
                    favoriteRecycler.showInVisible(true)
                }
            }

        }
    }

}