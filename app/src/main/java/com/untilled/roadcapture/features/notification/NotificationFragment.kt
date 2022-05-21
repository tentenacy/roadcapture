package com.untilled.roadcapture.features.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentNotificationBinding
import com.untilled.roadcapture.utils.mainActivity
import com.untilled.roadcapture.utils.ui.CustomDivider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationFragment : Fragment(){

    private var _binding: FragmentNotificationBinding? = null

    @Inject
    lateinit var customDivider: CustomDivider

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(layoutInflater,container,false)


        mainActivity().setSupportActionBar(binding.toolbarNotification)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imgNotificationBack.setOnClickListener {
            mainActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun initAdapter(){
        binding.recycleNotification.addItemDecoration(customDivider)
    }

}