package com.untilled.roadcapture.features.root.albums

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        initViews()
        setOnClickListeners()
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
            dismiss()
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
                        if(compareDate(startDate, endDate)) {
                            binding.buttonFilterEndDate.text = dateToString(startDate)
                        }
                    }
                    R.id.button_filter_end_date -> {
                        val startDate = getCalendar(binding.buttonFilterStartDate.text.toString())
                        val endDate = getCalendar(year, month, dayOfMonth)
                        view.text = dateToString(endDate)
                        if(compareDate(startDate, endDate)) {
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