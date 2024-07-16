package com.example.moviesapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentDetailBinding
import com.example.moviesapp.db.MoviesEntity
import com.example.moviesapp.utils.initRecycler
import com.example.moviesapp.utils.showInVisible
import com.example.moviesapp.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {
    //binding
    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var imageAdapter:ImagesAdapter

    @Inject
    lateinit var entity: MoviesEntity

    //other
    private var movieId = 0
    private val viewModel:DetailViewModel by viewModels()
    private val args:DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //get data
        movieId = args.movieID
        //call api
        if (movieId > 0){
            viewModel.loadDetailMovie(movieId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initView
        binding.apply {
            //load data
            viewModel.detailMovie.observe(viewLifecycleOwner){ response ->
                posterBigImg.load(response.poster)
                posterNormalImg.load(response.poster){
                    crossfade(true)
                    crossfade(800)
                }
                movieNameTxt.text = response.title
                movieRateTxt.text = response.imdbRating
                movieTimeTxt.text = response.runtime
                movieDateTxt.text = response.released
                movieSummaryInfo.text = response.plot
                movieActorsInfo.text = response.actors
                //images adapter
                imageAdapter.differ.submitList(response.images)
                imagesRecycler.initRecycler(
                    LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false),
                    imageAdapter
                )
                //fav click
                favImg.setOnClickListener {
                    entity.id =movieId
                    entity.poster = response.poster.toString()
                    entity.title = response.title.toString()
                    entity.rate = response.rated.toString()
                    entity.country = response.country.toString()
                    entity.year = response.year.toString()
                    viewModel.favoriteMovie(movieId,entity)
                }
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner){
                if (it){
                    detailLoading.showInVisible(true)
                    detailScrollView.showInVisible(false)
                }else{
                    detailLoading.showInVisible(false)
                    detailScrollView.showInVisible(true)
                }
            }
            //default fav icon color
            lifecycleScope.launchWhenCreated {
                if (viewModel.existMovie(movieId)){
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                }else{
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(),R.color.philippineSilver))
                }
            }
            //change image with click
            viewModel.isFavorite.observe(viewLifecycleOwner){
                if (it){
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.scarlet))
                }else{
                    favImg.setColorFilter(ContextCompat.getColor(requireContext(),R.color.philippineSilver))
                }
            }
            //back
            backImg.setOnClickListener{
                findNavController().navigateUp()
            }
        }
    }
}