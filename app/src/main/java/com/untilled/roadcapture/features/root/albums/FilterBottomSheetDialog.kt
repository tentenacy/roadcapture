package com.untilled.roadcapture.features.root.albums

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.ModalBottomSheetFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@RequiresApi(Build.VERSION_CODES.O) // LocalDate.now() 함수 사용하기 위함
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
            buttonFilterStartDate.text = makeDateString(LocalDate.now())
            buttonFilterEndDate.text = makeDateString(LocalDate.now())
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
        val dateNow = LocalDate.now()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                if (compareDate(view, year, month + 1, dayOfMonth)) {
                    Toast.makeText(requireContext(), "시작일과 종료일이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    binding.radiogroupFilterDuration.clearCheck()
                    val date = makeDateString(year, month + 1, dayOfMonth)
                    view.text = date
                }
            },
            dateNow.year, dateNow.monthValue - 1, dateNow.dayOfMonth
        )
        // 날짜 선택 제한
        datePickerDialog.apply {
            val cal = Calendar.getInstance()
            datePicker.maxDate = cal.timeInMillis

            cal.add(Calendar.YEAR, -2)
            datePicker.minDate = cal.timeInMillis
        }.show()
    }

    private fun makeDateString(year: Int, month: Int, dayOfMonth: Int): String =
        "${year}년 ${String.format("%02d", month)}월 ${String.format("%02d", dayOfMonth)}일"

    private fun makeDateString(date: LocalDate): String =
        "${date.year}년 ${String.format("%02d", date.monthValue)}월 ${String.format("%02d", date.dayOfMonth)}일"

    private fun makeDateFormat(date: String): String {
        var token = date.split(" ")

        return token[0].substring(0, 4) +
                token[1].substring(0, 2) +
                token[2].substring(0, 2)
    }

    private fun compareDate(view: Button, year: Int, month: Int, dayOfMonth: Int): Boolean {
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        when (view.id) {
            R.id.button_filter_start_date -> {  // 시작일 입력 시 종료일과 비교
                val startDate =
                    dateFormat.parse("${year}${String.format("%02d", month)}${dayOfMonth}").time
                val endDate =
                    dateFormat.parse(makeDateFormat(binding.buttonFilterEndDate.text.toString())).time
                val result = (endDate - startDate) / (24 * 60 * 60 * 1000)
                return result < 0
            }
            R.id.button_filter_end_date -> { // 종료일 입력 시 시작일과 비교
                val startDate =
                    dateFormat.parse(makeDateFormat(binding.buttonFilterStartDate.text.toString())).time
                val endDate =
                    dateFormat.parse("${year}${String.format("%02d", month)}${dayOfMonth}").time
                val result = (endDate - startDate) / (24 * 60 * 60 * 1000)
                return result < 0
            }
        }
        return true
    }
}