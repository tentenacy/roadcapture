package com.untilled.roadcapture.features.root.albums

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentFollowingAlbumsBinding
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
                    binding.fabFollowingAlbumsSortLatest.hide()
                    binding.fabFollowingAlbumsSortPopularity.hide()
                } else if(dy < 0) { // 위로 스크롤
                    binding.fabFollowingAlbumsSort.show()
                    binding.fabFollowingAlbumsSortLatest.show()
                    binding.fabFollowingAlbumsSortPopularity.show()
                }
            }
        })

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.fabFollowingAlbumsSort.setOnClickListener {
            isClicked = if(isClicked){  // fbutton이 눌렸다면
                ObjectAnimator.ofFloat(binding.fabFollowingAlbumsSortPopularity,"translationY",0f).apply { start() }
                ObjectAnimator.ofFloat(binding.fabFollowingAlbumsSortLatest,"translationY",0f).apply { start() }
                false
            } else {  // fbutton이 눌리지 않았다면
                ObjectAnimator.ofFloat(binding.fabFollowingAlbumsSortPopularity,"translationY",-450f).apply { start() }
                ObjectAnimator.ofFloat(binding.fabFollowingAlbumsSortLatest,"translationY",-250f).apply { start() }
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}