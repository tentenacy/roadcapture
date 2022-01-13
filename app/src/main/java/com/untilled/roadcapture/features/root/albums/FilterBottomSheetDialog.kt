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
    private val viewModel: AlbumsViewModel by viewModels({requireParentFragment()})
    private lateinit var albumsFragment: AlbumsFragment

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

        albumsFragment = parentFragment as AlbumsFragment
        expandFullHeight()
        initViews()
        setOnClickListeners()
    }

    private fun expandFullHeight() {
        val behavior = BottomSheetBehavior.from<View>(dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initViews() {
        binding.run {
            val now = Calendar.getInstance()
            btnDlgfilterStartdate.text = dateToString(now)
            btnDlgfilterEnddate.text = dateToString(now)
            radiogroupDlgfilterDuration.clearCheck()
            radiogroupDlgfilterSorting.clearCheck()
            radiobtnDlgfilterWhole.isChecked = true
            radiobtnDlgfilterSortLatest.isChecked = true
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
            initViews()
        }
        binding.btnDlgfilterStartdate.setOnClickListener {
            onCreateDatePicker(it as Button)
        }
        binding.btnDlgfilterEnddate.setOnClickListener {
            onCreateDatePicker(it as Button)
        }
    }

    private fun applyFilter() {
        when (binding.radiogroupDlgfilterDuration.checkedRadioButtonId) {
            binding.radiobtnDlgfilterWhole.id -> {
                albumsFragment.updateView(" ", getFilterDate(TimeUtil.TODAY))
            }

            binding.radiobtnDlgfilterToday.id -> {
                albumsFragment.updateView(getFilterDate(TimeUtil.TODAY), getFilterDate(TimeUtil.TODAY))
            }
            binding.radiobtnDlgfilterWeek.id -> {
                albumsFragment.updateView(getFilterDate(TimeUtil.WEEK), getFilterDate(TimeUtil.TODAY))
            }

            binding.radiobtnDlgfilterMonth.id -> {
                albumsFragment.updateView(getFilterDate(TimeUtil.MONTH), getFilterDate(TimeUtil.TODAY))
            }

            binding.radiobtnDlgfilterYear.id -> {
                albumsFragment.updateView(getFilterDate(TimeUtil.YEAR), getFilterDate(TimeUtil.TODAY))
            }
            else -> {
                albumsFragment.updateView(getFilterDate(binding.btnDlgfilterStartdate.text.toString()), getFilterDate(binding.btnDlgfilterEnddate.text.toString()))
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
                binding.radiogroupDlgfilterDuration.clearCheck()

                when (view.id) {
                    R.id.btn_dlgfilter_startdate -> {
                        val startDate = getCalendar(year, month, dayOfMonth)
                        val endDate = getCalendar(binding.btnDlgfilterEnddate.text.toString())
                        view.text = dateToString(startDate)
                        if (compareDate(startDate, endDate)) {
                            binding.btnDlgfilterEnddate.text = dateToString(startDate)
                        }
                    }
                    R.id.btn_dlgfilter_enddate -> {
                        val startDate = getCalendar(binding.btnDlgfilterStartdate.text.toString())
                        val endDate = getCalendar(year, month, dayOfMonth)
                        view.text = dateToString(endDate)
                        if (compareDate(startDate, endDate)) {
                            binding.btnDlgfilterStartdate.text = dateToString(endDate)
                        }
                    }
                }
            },
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
        )
        // 날짜 선택 제한
        limitDate(datePickerDialog)
    }

    private fun limitDate(datePickerDialog: DatePickerDialog) {
        datePickerDialog.apply {
            val now = Calendar.getInstance()
            datePicker.maxDate = now.timeInMillis

            now.add(Calendar.YEAR, -2)
            datePicker.minDate = now.timeInMillis
        }.show()
    }
}