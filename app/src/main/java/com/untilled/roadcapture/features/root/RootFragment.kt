package com.untilled.roadcapture.features.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.navigation.StackHostFragment
import com.untilled.roadcapture.databinding.FragmentRootBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : Fragment() {
    private var _binding: FragmentRootBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumsFragment: StackHostFragment
    private lateinit var searchRootFragment: StackHostFragment
    private lateinit var followingAlbumsFragment: StackHostFragment
    private lateinit var myStudioFragment: StackHostFragment

    private val fragments: Array<Fragment>
        get() = arrayOf(
            albumsFragment,
            searchRootFragment,
            followingAlbumsFragment,
            myStudioFragment
        )
    private var selectedIndex = 0

    private val tabs: Array<TextView>
        get() = binding.run {
            arrayOf(
                textViewRootAlbumsTab,
                textViewRootSearchTab,
                textViewRootFollowingAlbumsTab,
                textViewRootMyStudioTab
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initChildFragments()
            addTransactionalFragments().setDefaultFragment().commit()
        } else {
            selectedIndex = savedInstanceState.getInt("selectedIndex", 0)
            initSavedChildFragments()
        }
    }

    private fun initSavedChildFragments() {
        albumsFragment =
            childFragmentManager.findFragmentByTag("albumsFragment") as StackHostFragment
        searchRootFragment =
            childFragmentManager.findFragmentByTag("searchRootFragment") as StackHostFragment
        followingAlbumsFragment =
            childFragmentManager.findFragmentByTag("followingAlbumsFragment") as StackHostFragment
        myStudioFragment =
            childFragmentManager.findFragmentByTag("myStudioFragment") as StackHostFragment
    }

    private fun FragmentTransaction.setDefaultFragment() = selectFragment(selectedIndex)

    private fun addTransactionalFragments() = childFragmentManager.beginTransaction()
        .add(
            R.id.frame_layout_root_container_bottom_nav_content,
            albumsFragment,
            "albumsFragment"
        )
        .add(
            R.id.frame_layout_root_container_bottom_nav_content,
            searchRootFragment,
            "searchRootFragment"
        )
        .add(
            R.id.frame_layout_root_container_bottom_nav_content,
            followingAlbumsFragment,
            "followingAlbumsFragment"
        )
        .add(
            R.id.frame_layout_root_container_bottom_nav_content,
            myStudioFragment,
            "myStudioFragment"
        )

    private fun initChildFragments() {
        albumsFragment = StackHostFragment.newInstance(R.navigation.navigation_root_albums)
        searchRootFragment = StackHostFragment.newInstance(R.navigation.navigation_root_search)
        followingAlbumsFragment = StackHostFragment.newInstance(R.navigation.navigation_root_following_albums)
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

        tabs.forEachIndexed { index, textView ->
            textView.setOnClickListener {
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

        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        return this
    }

    private fun setupTabSelectedState(selectedIndex: Int) {
        tabs.forEachIndexed { index, textView ->
            textView.setTextColor(
                when (index) {
                    selectedIndex -> ContextCompat.getColor(
                        requireContext(),
                        R.color.tab_selected
                    )
                    else -> ContextCompat.getColor(requireContext(), R.color.tab_unselected)
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