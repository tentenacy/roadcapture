package com.untilled.roadcapture.features.root.albums

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ModalBottomSheetFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.untilled.roadcapture.utils.*

@AndroidEntryPoint
class FilterBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumsViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalBottomSheetFilterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandFullHeight()
        initViews()
        setOnClickListeners()
    }

    private fun expandFullHeight() {
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initViews() {
        binding.run {
            val now = Calendar.getInstance()
            buttonFilterStartDate.text = dateToString(now)
            buttonFilterEndDate.text = dateToString(now)
            radiogroupFilterDuration.clearCheck()
            radiogroupFilterSorting.clearCheck()
            radiobuttonFilterWholeDuration.isChecked = true
            radiobuttonFilterSortingLatest.isChecked = true
        }

    }

    private fun setOnClickListeners() {
        binding.buttonFilterApply.setOnClickListener {
            filterApply()
        }
        binding.imageviewFilterClose.setOnClickListener {
            dismiss()
        }
        binding.textviewFilterReset.setOnClickListener {
            initViews()
        }
        binding.buttonFilterStartDate.setOnClickListener {
            onCreateDatePicker(it as Button)
        }
        binding.buttonFilterEndDate.setOnClickListener {
            onCreateDatePicker(it as Button)
        }
    }

    private fun filterApply() {
        when (binding.radiogroupFilterDuration.checkedRadioButtonId) {
            binding.radiobuttonFilterWholeDuration.id -> {
                viewModel.getAlbums(" ", getFilterDate(TimeUtil.TODAY))
            }

            binding.radiobuttonFilterToday.id -> {
                viewModel.getAlbums(getFilterDate(TimeUtil.TODAY), getFilterDate(TimeUtil.TODAY))
            }
            binding.radiobuttonFilterThisWeek.id -> {
                viewModel.getAlbums(getFilterDate(TimeUtil.WEEK), getFilterDate(TimeUtil.TODAY))
            }

            binding.radiobuttonFilterThisMonth.id -> {
                viewModel.getAlbums(getFilterDate(TimeUtil.MONTH), getFilterDate(TimeUtil.TODAY))
            }

            binding.radiobuttonFilterThisYear.id -> {
                viewModel.getAlbums(getFilterDate(TimeUtil.YEAR), getFilterDate(TimeUtil.TODAY))
            }
            else -> {
                viewModel.getAlbums(getFilterDate(binding.buttonFilterStartDate.text.toString()), getFilterDate(binding.buttonFilterEndDate.text.toString()))
            }
        }
        dismiss()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onCreateDatePicker(view: Button) {
        val cal = getCalendar(view.text.toString())
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                binding.radiogroupFilterDuration.clearCheck()

                when (view.id) {
                    R.id.button_filter_start_date -> {
                        val startDate = getCalendar(year, month, dayOfMonth)
                        val endDate = getCalendar(binding.buttonFilterEndDate.text.toString())
                        view.text = dateToString(startDate)
                        if (compareDate(startDate, endDate)) {
                            binding.buttonFilterEndDate.text = dateToString(startDate)
                        }
                    }
                    R.id.button_filter_end_date -> {
                        val startDate = getCalendar(binding.buttonFilterStartDate.text.toString())
                        val endDate = getCalendar(year, month, dayOfMonth)
                        view.text = dateToString(endDate)
                        if (compareDate(startDate, endDate)) {
                            binding.buttonFilterStartDate.text = dateToString(endDate)
                        }
                    }
                }
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        // 날짜 선택 제한
        datePickerDialog.apply {
            val now = Calendar.getInstance()
            datePicker.maxDate = now.timeInMillis

            now.add(Calendar.YEAR, -2)
            datePicker.minDate = now.timeInMillis
        }.show()
    }
}