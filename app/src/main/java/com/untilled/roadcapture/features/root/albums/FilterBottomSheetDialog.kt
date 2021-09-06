package com.untilled.roadcapture.features.root.albums

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ModalBottomSheetFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O) // LocalDate.now() 함수 사용하기 위함
@AndroidEntryPoint
class FilterBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: ModalBottomSheetFilterBinding? = null
    private val binding get() = _binding!!
    private lateinit var date : String

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

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.buttonFilterApply.setOnClickListener {
            dismiss()
        }
        binding.imageviewFilterClose.setOnClickListener {
            dismiss()
        }
        binding.textviewFilterReset.setOnClickListener {
            binding.radiogroupFilterDuration.clearCheck()
            binding.radiogroupFilterSorting.clearCheck()
            binding.radiobuttonFilterWholeDuration.isChecked = true
            binding.radiobuttonFilterSortingLatest.isChecked = true
            binding.layoutFilterSetDuration.isVisible = false
        }
        binding.textviewFilterSetDuration.setOnClickListener {
            binding.radiogroupFilterDuration.clearCheck()
            binding.layoutFilterSetDuration.isVisible = true
            binding.buttonFilterStartDate.text = makeDate(LocalDate.now())
            binding.buttonFilterEndDate.text = makeDate(LocalDate.now())
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

    private fun onCreateDatePicker(view : Button) {
        val dateNow = LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                val date = makeDate(year, month + 1, dayOfMonth)
                view.text = date
            },
            dateNow.year, dateNow.monthValue - 1, dateNow.dayOfMonth
        )
        datePickerDialog.show()
    }

    private fun makeDate(year: Int, month: Int, dayOfMonth: Int): String =
        "${year}년 ${month}월 ${dayOfMonth}일"

    private fun makeDate(date : LocalDate): String =
        "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일"
}