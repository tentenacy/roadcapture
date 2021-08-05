package com.untilled.roadcapture.features.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.navigation.StackHostFragment
import com.untilled.roadcapture.databinding.FragmentRootBinding
import com.untilled.roadcapture.utils.extension.setTint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : Fragment() {
    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!

    lateinit var albumsFragment: StackHostFragment
    lateinit var searchRootFragment: StackHostFragment
    lateinit var followingAlbumsFragment: StackHostFragment
    lateinit var myStudioFragment: StackHostFragment

    private val fragments: Array<Fragment>
        get() = arrayOf(
            albumsFragment,
            searchRootFragment,
            followingAlbumsFragment,
            myStudioFragment
        )
    private var selectedIndex = 0

    private val tabs: Array<ImageView>
        get() = binding.run {
            arrayOf(
                imageViewRootAlbumsTab,
                imageViewRootSearchTab,
                imageViewRootFollowingAlbumsTab,
                imageViewRootMyStudioTab
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initChildFragments()
            childFragmentManager.beginTransaction().addFragments().setDefaultFragment().commit()
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

    private fun FragmentTransaction.setDefaultFragment() = selectFragment(selectedIndex)

    private fun FragmentTransaction.addFragments() = apply {
        add(
            R.id.frame_layout_root_container_bottom_nav_content,
            albumsFragment,
            this@RootFragment::albumsFragment.name
        )
        add(
            R.id.frame_layout_root_container_bottom_nav_content,
            searchRootFragment,
            this@RootFragment::searchRootFragment.name
        )
        add(
            R.id.frame_layout_root_container_bottom_nav_content,
            followingAlbumsFragment,
            this@RootFragment::followingAlbumsFragment.name
        )
        add(
            R.id.frame_layout_root_container_bottom_nav_content,
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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabs.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                selectFragment(index)
            }
        }

        setupTabSelectedState(selectedIndex)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedIndex", selectedIndex)
    }

    private fun FragmentTransaction.selectFragment(selectedIndex: Int): FragmentTransaction {
        fragments.forEachIndexed { index, fragment ->
            if (index == selectedIndex) {
                attach(fragment)
            } else {
                detach(fragment)
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
}