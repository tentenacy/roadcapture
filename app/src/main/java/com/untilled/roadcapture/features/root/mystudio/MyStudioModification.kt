package com.untilled.roadcapture.features.root.mystudio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.core.navigation.StackHostFragment
import com.untilled.roadcapture.databinding.FragmentMyStudioModificationBinding
import com.untilled.roadcapture.features.root.RootFragment
import com.untilled.roadcapture.features.root.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyStudioModification : Fragment() {
    private var _binding: FragmentMyStudioModificationBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyStudioModificationBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners(){
        binding.buttonTest.setOnClickListener {
            binding.textviewMyStudioModificationProfileDescription.text =
                binding.edittextMyStudioModificationChangeProfileDescription.text.toString()
        }
        binding.imageviewMyStudioModificationCheck.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
        binding.imageviewMyStudioModificationBack.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

}