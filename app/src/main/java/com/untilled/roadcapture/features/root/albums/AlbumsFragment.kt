package com.untilled.roadcapture.features.root.albums

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.orhanobut.logger.Logger
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.data.entity.Album
import com.untilled.roadcapture.databinding.FragmentAlbumsBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlbumsBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarAlbums)

        binding.recyclerviewAlbums.adapter = AlbumsAdapter(DummyDataSet.albums)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListeners()
    }

    private fun onClickListeners() {
        binding.imageviewAlbumsSetting.setOnClickListener {
              Navigation.findNavController((parentFragment?.parentFragment?.parentFragment as RootFragment).binding.root)
                   .navigate(R.id.action_rootFragment_to_settingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}