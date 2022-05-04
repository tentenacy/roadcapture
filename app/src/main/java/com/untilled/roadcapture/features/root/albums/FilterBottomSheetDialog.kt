package com.untilled.roadcapture.features.root.albums

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.untilled.roadcapture.R
import com.untilled.roadcapture.databinding.BottomsheetAlbumsFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.untilled.roadcapture.utils.*

@AndroidEntryPoint
class FilterBottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding: BottomsheetAlbumsFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumsViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = BottomsheetAlbumsFilterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandFullHeight()
        observeData()
        setOnClickListeners()
        setOtherListeners()
    }

    private fun setOtherListeners() {
        binding.radiogroupDlgfilterDuration.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radiobtn_dlgfilter_whole -> {
                    binding.btnDlgfilterStartdate.text = "-"
                    binding.btnDlgfilterEnddate.text = Date().toDateFormat()
                }
                R.id.radiobtn_dlgfilter_today -> {
                    binding.btnDlgfilterStartdate.text = Date().toDateFormat()
                    binding.btnDlgfilterEnddate.text = Date().toDateFormat()
                }
                R.id.radiobtn_dlgfilter_week -> {
                    binding.btnDlgfilterStartdate.text = Date().minusDay(7).toDateFormat()
                    binding.btnDlgfilterEnddate.text = Date().toDateFormat()
                }
                R.id.radiobtn_dlgfilter_month -> {
                    binding.btnDlgfilterStartdate.text = Date().minusMonth(1).toDateFormat()
                    binding.btnDlgfilterEnddate.text = Date().toDateFormat()
                }
                R.id.radiobtn_dlgfilter_year -> {
                    binding.btnDlgfilterStartdate.text = Date().minusYear(1).toDateFormat()
                    binding.btnDlgfilterEnddate.text = Date().toDateFormat()
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnDlgfilterApply.setOnClickListener {
            applyFilter()
        }
        binding.imageDlgfilterClose.setOnClickListener {
            dismiss()
        }
        binding.textDlgfilterReset.setOnClickListener {
            resetFilter()
        }
        binding.btnDlgfilterStartdate.setOnClickListener {
            showDatePicker(it as Button)
        }
        binding.btnDlgfilterEnddate.setOnClickListener {
            showDatePicker(it as Button)
        }
    }

    private fun observeData() {
        viewModel.durationCheckedRadioId.observe(viewLifecycleOwner) {
            when (it) {
                R.id.radiobtn_dlgfilter_whole -> binding.radiobtnDlgfilterWhole.isChecked =
                    true
                R.id.radiobtn_dlgfilter_today -> binding.radiobtnDlgfilterToday.isChecked =
                    true
                R.id.radiobtn_dlgfilter_week -> binding.radiobtnDlgfilterWeek.isChecked =
                    true
                R.id.radiobtn_dlgfilter_month -> binding.radiobtnDlgfilterMonth.isChecked =
                    true
                R.id.radiobtn_dlgfilter_year -> binding.radiobtnDlgfilterYear.isChecked =
                    true
                else -> binding.radiogroupDlgfilterDuration.clearCheck()
            }
        }
        viewModel.sortCheckedRadioId.observe(viewLifecycleOwner) {
            when(it) {
                R.id.radiobtn_dlgfilter_sort_latest -> binding.radiobtnDlgfilterSortLatest.isChecked = true
                R.id.radiobtn_dlgfilter_sort_popularity -> binding.radiobtnDlgfilterSortPopularity.isChecked = true
            }
        }
        viewModel.dateFromText.observe(viewLifecycleOwner) {
            binding.btnDlgfilterStartdate.text = it ?: "-"
        }
        viewModel.dateToText.observe(viewLifecycleOwner) {
            binding.btnDlgfilterEnddate.text = it ?: Date().start().toDateFormat()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun expandFullHeight() {
        BottomSheetBehavior.from(dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!).state =
            BottomSheetBehavior.STATE_EXPANDED
    }

    private fun resetFilter() {

        binding.apply {
            binding.radiogroupDlgfilterDuration.clearCheck()
            binding.radiogroupDlgfilterSorting.clearCheck()
        }
    }

    private fun applyFilter() {

        binding.apply {
            viewModel.setDurationCheckedRadioId(radiogroupDlgfilterDuration.checkedRadioButtonId)
            viewModel.setSortCheckedRadioId(radiogroupDlgfilterSorting.checkedRadioButtonId)
            viewModel.setDateFromText(binding.btnDlgfilterStartdate.text.toString().takeIf { it != "-" })
            viewModel.setDateToText(binding.btnDlgfilterEnddate.text.toString())
            viewModel.setSortText(when(binding.radiogroupDlgfilterSorting.checkedRadioButtonId) {
                R.id.radiobtn_dlgfilter_sort_latest -> "createdAt,desc"
                //TODO: 서버에서 좋아요순 구현 후 작업
                R.id.radiobtn_dlgfilter_sort_popularity -> null
                else -> null
            })
            viewModel.albums()
            dismiss()
        }
    }

    private fun showDatePicker(view: Button) {
        val cal = calendarFrom(view.text.toString())
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { _, year, month, dayOfMonth ->
                binding.radiogroupDlgfilterDuration.clearCheck()

                when (view.id) {
                    R.id.btn_dlgfilter_startdate -> {

                        val startDateText = "$year-${
                            month.plus(1).toZeroZeroFormat()
                        }-${dayOfMonth.toZeroZeroFormat()}"

                        view.text = startDateText

                        if (!dateFrom(startDateText).before(dateFrom(binding.btnDlgfilterEnddate.text.toString()))) {
                            binding.btnDlgfilterEnddate.text = startDateText
                        }
                    }
                    R.id.btn_dlgfilter_enddate -> {

                        val endDateText = "$year-${
                            month.plus(1).toZeroZeroFormat()
                        }-${dayOfMonth.toZeroZeroFormat()}"

                        view.text = endDateText

                        if (binding.btnDlgfilterStartdate.text.toString() != "-" && !dateFrom(binding.btnDlgfilterStartdate.text.toString()).before(
                                dateFrom(endDateText)
                            )
                        ) {
                            binding.btnDlgfilterStartdate.text = endDateText
                        }
                    }
                }
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        // 날짜 선택 제한
        limitDateSelection(datePickerDialog)
    }

    private fun limitDateSelection(dialog: DatePickerDialog) {
        dialog.apply {

            val now = Calendar.getInstance()

            datePicker.maxDate = now.timeInMillis

            now.add(Calendar.YEAR, -2)
            datePicker.minDate = now.timeInMillis
        }.show()
    }
}