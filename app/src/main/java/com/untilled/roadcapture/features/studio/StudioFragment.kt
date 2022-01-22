package com.untilled.roadcapture.features.studio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.untilled.roadcapture.data.datasource.api.dto.album.AlbumsResponse
import com.untilled.roadcapture.data.datasource.api.dto.common.PageResponse
import com.untilled.roadcapture.data.datasource.api.dto.user.UsersResponse
import com.untilled.roadcapture.databinding.FragmentStudioBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudioFragment : Fragment() {

    private var _binding: FragmentStudioBinding? = null
    val binding get() = _binding!!

    val args: StudioFragmentArgs by navArgs()
    private val viewModel: StudioViewModel by viewModels()

    private val userObserver = { user: UsersResponse ->
        binding.user = user
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStudioBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initViews()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.userInfo.observe(viewLifecycleOwner,userObserver)
    }

    private fun initViews(){
        viewModel.getUserInfo(args.id)
    }

    private fun setOnClickListeners() {
        binding.textStudioFollower.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(StudioFragmentDirections.actionStudioFragmentToFollowerFragment(args.id))
        }
        binding.textStudioFollowing.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(StudioFragmentDirections.actionStudioFragmentToFollowingFragment(args.id))
        }
        binding.btnStudioFollow.setOnClickListener {
            viewModel.follow(args.id)
        }
        binding.imgStudioMoreBefore.setOnClickListener {

        }
        binding.imgStudioMoreAfter.setOnClickListener {

        }
        binding.imgStudioBackAfter.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.imgStudioBackBefore.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initAdapter(albums: PageResponse<AlbumsResponse>) {
//        binding.recyclerStudioAlbum.withModels { initStudioAlbumsItem(albums) }
//        binding.recyclerStudioPlace.withModels {
//            DummyDataSet.places.forEachIndexed { index, place ->
//                placeFilter {
//                    id(index)
//                    place(place)
//
//                    onClickItem { model, parentView, clickedView, position ->
//                        when(clickedView.id){
//                            R.id.view_iplace_filter_overlay ->
//                                clickedView.isSelected = !clickedView.isSelected
//                        }
//                    }
//                }
//            }
//        }
    }

//    private fun EpoxyController.initStudioAlbumsItem(albums: PageResponse<AlbumsResponse>) {
//        albums.content.forEachIndexed { index, album ->
//            albumsStudio {
//                id(index)
//                studio(album)
//                onClickItem { model, parentView, clickedView, position ->
//                    when(clickedView.id){
//
//                    }
//                }
//            }
//        }
//    }
}