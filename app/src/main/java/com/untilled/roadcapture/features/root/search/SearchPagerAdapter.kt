package com.untilled.roadcapture.features.root.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.lang.IndexOutOfBoundsException

const val TITLE_CRITERIA_PAGE_INDEX = 0
const val USERNAME_CRITERIA_PAGE_INDEX = 1
const val PLACE_CRITERIA_PAGE_INDEX = 2

class SearchPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        TITLE_CRITERIA_PAGE_INDEX to { TitleSearchFragment() },
        USERNAME_CRITERIA_PAGE_INDEX to { UsernameSearchFragment() },
        PLACE_CRITERIA_PAGE_INDEX to { PlaceCriteriaSearchFragment() }
    )

    override fun getItemCount(): Int = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment = tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}