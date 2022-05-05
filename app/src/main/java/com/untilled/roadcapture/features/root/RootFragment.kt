package com.untilled.roadcapture.features.root

import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.navigation.StackHostFragment
import com.untilled.roadcapture.databinding.FragmentRootBinding
import com.untilled.roadcapture.features.root.capture.AlbumCreationAskingBottomSheetDialog
import com.untilled.roadcapture.utils.constant.tag.DialogTagConstant
import com.untilled.roadcapture.utils.mainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootFragment : Fragment() {

    private var _binding: FragmentRootBinding? = null
    val binding get() = _binding!!

    private lateinit var albumsFragment: StackHostFragment
    private lateinit var searchRootFragment: StackHostFragment
    private lateinit var followingAlbumsFragment: StackHostFragment
    private lateinit var myStudioFragment: StackHostFragment

    private val fragments: Map<Int, Fragment>
        get() = mapOf(
            R.id.menu_albums to albumsFragment,
            R.id.menu_search to searchRootFragment,
            R.id.menu_followingalbums to followingAlbumsFragment,
            R.id.menu_mystudio to myStudioFragment,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFragments()
        fragments[R.id.menu_albums]?.let { selectFragment(it) }
    }

    private fun initFragments() {
        albumsFragment = StackHostFragment.newInstance(R.navigation.navigation_root_albums)
        searchRootFragment = StackHostFragment.newInstance(R.navigation.navigation_root_search)
        followingAlbumsFragment =
            StackHostFragment.newInstance(R.navigation.navigation_root_following_albums)
        myStudioFragment = StackHostFragment.newInstance(R.navigation.navigation_root_my_studio)

        childFragmentManager.beginTransaction().apply {
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
        }.commit()
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

        setOtherListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setOtherListeners() {
        binding.bnvRoot.setOnNavigationItemSelectedListener {
            if(it.itemId == R.id.menu_capture) {
                AlbumCreationAskingBottomSheetDialog().show(
                    childFragmentManager,
                    DialogTagConstant.ALBUM_CREATION_ASKING_BOTTOM_SHEET
                )
                return@setOnNavigationItemSelectedListener false
            } else {
                fragments[it.itemId]?.let { fragment ->
                    selectFragment(fragment)
                }
            }

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun selectFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {
            fragments.forEach {
                if(it.value == fragment) {
                    show(fragment)
                } else {
                    hide(it.value)
                }
            }
        }.commit()
    }

}