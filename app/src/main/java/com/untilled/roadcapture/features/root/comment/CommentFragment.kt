package com.untilled.roadcapture.features.root.comment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.application.MainActivity
import com.untilled.roadcapture.comment
import com.untilled.roadcapture.databinding.FragmentCommentBinding
import com.untilled.roadcapture.features.base.CustomDivider
import com.untilled.roadcapture.utils.DummyDataSet
import com.untilled.roadcapture.utils.extension.getPxFromDp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommentBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarComment)

        initAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.imageviewCommentBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun initAdapter(){
        val customDivider = CustomDivider(2.5f,1f, Color.parseColor("#EFEFEF"))

        binding.recyclerviewComment.addItemDecoration(customDivider)

        binding.recyclerviewComment.withModels {
            DummyDataSet.comment.forEachIndexed { index, comment ->
                comment {
                    id(index)
                    comment(comment)

                    onClickItem { model, parentView, clickedView, position ->
                        when(clickedView.id) {
                            R.id.imageview_item_comment_more -> {
                                val popupMenu = PopupMenu(requireContext(), clickedView)
                                popupMenu.apply {
                                    menuInflater.inflate(R.menu.popup_menu_comment_more, popupMenu.menu)
                                    setOnMenuItemClickListener { item ->
                                        when (item.itemId) {
                                            R.id.popup_menu_comment_more_report -> {
                                            }
                                        }
                                        true
                                    }
                                }.show()
                            }
                        }
                    }
                }
            }
        }
    }
}