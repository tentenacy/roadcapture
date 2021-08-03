package com.untilled.roadcapture.core.navigation

import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.untilled.roadcapture.R

class StackHostFragment : Fragment(R.layout.stack_host_fragment) {
    companion object {
        fun newInstance(@NavigationRes navigationId: Int): StackHostFragment =
            StackHostFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putInt("navigationId", navigationId)
                }
            }
    }

    lateinit var navController: NavController
        private set

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =
            NavHostFragment.findNavController(childFragmentManager.findFragmentById(R.id.containerStackHost) as NavHostFragment)

        val hasGraph = try {
            navController.graph
            true
        } catch (e: IllegalStateException) {
            false
        }

        if (!hasGraph) {
            navController.setGraph(requireArguments().getInt("navigationId"))
        }
    }
}