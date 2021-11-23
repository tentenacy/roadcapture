package com.untilled.roadcapture.features.root.albums

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.RootFragmentDirections
import com.untilled.roadcapture.homeAlbum
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private var flagLike: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumsBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarAlbums)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 아래로 스크롤 시 플로팅 버튼 hide
        binding.recyclerviewAlbums.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //super.onScrolled(recyclerView, dx, dy)
                if(dy>0) {  // 아래로 스크롤
                    binding.fabAlbumsSort.hide()
                } else if(dy < 0) { // 위로 스크롤
                    binding.fabAlbumsSort.show()
                }
            }
        })

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewAlbumsSetting.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_settingsFragment)
        }

        binding.imageviewAlbumsNotification.setOnClickListener {
            Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                .navigate(R.id.action_rootFragment_to_notificationFragment)
        }

        binding.fabAlbumsSort.setOnClickListener {
            val filterBottomSheetDialog = FilterBottomSheetDialog()
            filterBottomSheetDialog.show(childFragmentManager, "filterBottomSheet")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(){
        binding.recyclerviewAlbums.withModels {
            DummyDataSet.albums.forEachIndexed { index, album ->
                homeAlbum {
                    id(index)
                    album(album)

                    onClickItem { model, parentView, clickedView, position ->
                        when (clickedView.id) {
                            R.id.imageview_item_home_album_comment -> Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                                .navigate(R.id.action_rootFragment_to_commentFragment)

                            R.id.imageview_item_home_album_like -> if (!flagLike) {
                                val animator = ValueAnimator.ofFloat(0f, 0.5f).setDuration(800)
                                animator.addUpdateListener {
                                    (clickedView as LottieAnimationView).progress =
                                        it.animatedValue as Float
                                }
                                animator.start()
                                flagLike = true
                            } else {
                                val animator = ValueAnimator.ofFloat(0.5f, 1f).setDuration(800)
                                animator.addUpdateListener {
                                    (clickedView as LottieAnimationView).progress =
                                        it.animatedValue as Float
                                }
                                animator.start()
                                flagLike = false
                            }
                            R.id.imageview_item_home_albums_thumbnail ->
                                Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root).
                                        navigate(RootFragmentDirections.actionRootFragmentToPictureViewerFragment(model.album()))
                            R.id.imageview_item_home_album_more -> {
                                val popupMenu = PopupMenu(requireContext(), clickedView)
                                popupMenu.apply {
                                    menuInflater.inflate(R.menu.popup_menu_albums_more, popupMenu.menu)
                                    setOnMenuItemClickListener { item ->
                                        when (item.itemId) {
                                            R.id.popup_menu_albums_more_share -> {
                                            }
                                            R.id.popup_menu_albums_more_report -> {
                                            }
                                            R.id.popup_menu_albums_more_hide -> {
                                            }
                                        }
                                        true
                                    }
                                }.show()
                            }
                        }
                    }
                }
            }
        }
    }
}