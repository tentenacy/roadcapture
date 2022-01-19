package com.untilled.roadcapture.features.notification

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.databinding.FragmentNotificationBinding
import com.untilled.roadcapture.features.common.CustomDivider
import com.untilled.roadcapture.notification
import com.untilled.roadcapture.utils.dummy.DummyDataSet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment(){

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(layoutInflater,container,false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarNotification)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imgNotificationBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun initAdapter(){

        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recycleNotification.addItemDecoration(customDivider)

        binding.recycleNotification.withModels {
//            DummyDataSet.notification.forEachIndexed { index, album ->
//                notification {
//                    id(index)
//                    notification(album)
//
//                    onClickItem { model, parentView, clickedView, position ->
//                        when(clickedView.id){
//                            R.id.img_inotification_profile->
//                                Navigation.findNavController(binding.root)
//                                    .navigate(R.id.action_notificationFragment_to_studioFragment)
//                        }
//                    }
//                }
//            }
        }
    }

}