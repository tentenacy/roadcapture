package com.untilled.roadcapture.features.root.mystudio.follow

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.databinding.FragmentFollowerBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.follow
import com.untilled.roadcapture.utils.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowerFragment : Fragment(){

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(layoutInflater,container,false)
        initAdapter()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.imageviewFollowerBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initAdapter(){

        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerviewFollower.addItemDecoration(customDivider)

        binding.recyclerviewFollower.withModels {
            DummyDataSet.user.forEachIndexed { index, user ->
                follow {
                    id(index)
                    user(user)
                }
            }
        }
    }

}