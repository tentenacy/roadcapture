package com.untilled.roadcapture.features.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.navigation.StackHostFragment
import com.untilled.roadcapture.databinding.FragmentRootBinding
import com.untilled.roadcapture.features.root.capture.AlbumCreationAskingBottomSheetDialog
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.setTint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : Fragment() {
    private var _binding: FragmentRootBinding? = null
    val binding get() = _binding!!

    private lateinit var albumsFragment: StackHostFragment
    private lateinit var searchRootFragment: StackHostFragment
    private lateinit var followingAlbumsFragment: StackHostFragment
    private lateinit var myStudioFragment: StackHostFragment

    private val fragments: Array<Fragment>
        get() = arrayOf(
            albumsFragment,
            searchRootFragment,
            followingAlbumsFragment,
            myStudioFragment,
        )

    private var selectedIndex = 0

    private val tabs: Array<ImageView>
        get() = binding.run {
            arrayOf(
                imgRootAlbumsTab,
                imgRootSearchTab,
                imgRootFollowingalbumsTab,
                imgRootMyStudioTab,
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initChildFragments()
            childFragmentManager.beginTransaction().addFragments().showDefaultFragment().commit()
        } else {
            selectedIndex = savedInstanceState.getInt("selectedIndex", 0)
            loadChildFragments()
        }
    }

    private fun loadChildFragments() {
        albumsFragment =
            childFragmentManager.findFragmentByTag(this@RootFragment::albumsFragment.name) as StackHostFragment
        searchRootFragment =
            childFragmentManager.findFragmentByTag(this@RootFragment::searchRootFragment.name) as StackHostFragment
        followingAlbumsFragment =
            childFragmentManager.findFragmentByTag(this@RootFragment::followingAlbumsFragment.name) as StackHostFragment
        myStudioFragment =
            childFragmentManager.findFragmentByTag(this@RootFragment::myStudioFragment.name) as StackHostFragment
    }

    private fun FragmentTransaction.addFragments() = apply {
        add(
            R.id.frame_root_container_contents,
            searchRootFragment,
            this@RootFragment::searchRootFragment.name
        )
        add(
            R.id.frame_root_container_contents,
            followingAlbumsFragment,
            this@RootFragment::followingAlbumsFragment.name
        )
        add(
            R.id.frame_root_container_contents,
            albumsFragment,
            this@RootFragment::albumsFragment.name
        )
        add(
            R.id.frame_root_container_contents,
            myStudioFragment,
            this@RootFragment::myStudioFragment.name
        )
    }

    private fun initChildFragments() {
        albumsFragment = StackHostFragment.newInstance(R.navigation.navigation_root_albums)
        searchRootFragment = StackHostFragment.newInstance(R.navigation.navigation_root_search)
        followingAlbumsFragment =
            StackHostFragment.newInstance(R.navigation.navigation_root_following_albums)
        myStudioFragment = StackHostFragment.newInstance(R.navigation.navigation_root_my_studio)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRootBinding.inflate(layoutInflater, container, false)

        mainActivity().viewModel.setBindingRoot(binding.root)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabs.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                selectFragment(index)
            }
        }

        setOnClickListeners()
        setupTabSelectedState(selectedIndex)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedIndex", selectedIndex)
    }

    private fun FragmentTransaction.showDefaultFragment() = selectFragment(selectedIndex)

    private fun FragmentTransaction.selectFragment(selectedIndex: Int): FragmentTransaction {
        fragments.forEachIndexed { index, fragment ->
            if (index == selectedIndex) {
                show(fragment)
            } else {
                hide(fragment)
            }
        }

        return this
    }

    private fun setupTabSelectedState(selectedIndex: Int) {
        tabs.forEachIndexed { index, imageView ->
            imageView.setTint(
                when (index) {
                    selectedIndex -> requireContext().getColor(R.color.tab_selected)
                    else -> requireContext().getColor(R.color.tab_unselected)
                }
            )
        }
    }

    private fun selectFragment(indexToSelect: Int) {
        this.selectedIndex = indexToSelect

        setupTabSelectedState(indexToSelect)

        childFragmentManager.beginTransaction()
            .selectFragment(indexToSelect)
            .commit()
    }


    private fun setOnClickListeners() {
        binding.imgRootCaptureTab.setOnClickListener {
            AlbumCreationAskingBottomSheetDialog().show(
                childFragmentManager,
                DialogTagConstant.ALBUM_CREATION_ASKING_BOTTOM_SHEET
            )
        }
    }
}