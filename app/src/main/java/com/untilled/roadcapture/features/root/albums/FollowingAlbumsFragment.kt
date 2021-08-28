package com.untilled.roadcapture.features.root.albums

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentFollowingAlbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingAlbumsFragment : Fragment() {

    private var _binding: FragmentFollowingAlbumsBinding? = null
    private val binding get() = _binding!!

    private var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowingAlbumsBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarFollowingAlbums)

        binding.recyclerviewFollowingAlbums.adapter = AlbumsAdapter(DummyDataSet.albums)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 아래로 스크롤 시 플로팅 버튼 hide
        binding.recyclerviewFollowingAlbums.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //super.onScrolled(recyclerView, dx, dy)
                if(dy>0) {  // 아래로 스크롤
                    binding.fabFollowingAlbumsSort.hide()
                } else if(dy < 0) { // 위로 스크롤
                    binding.fabFollowingAlbumsSort.show()
                }
            }
        })

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.fabFollowingAlbumsSort.setOnClickListener {
            val sortingBottomSheetDialog = SortingBottomSheetDialog()
            sortingBottomSheetDialog.show(childFragmentManager, "sortingBottomSheetDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}