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

    private fun reset() {
        binding.run {
            btnDlgfilterStartdate.text = dateToString(getTodayCalendar())
            btnDlgfilterEnddate.text = dateToString(getTodayCalendar())
            radiogroupDlgfilterDuration.clearCheck()
            radiogroupDlgfilterSorting.clearCheck()
            radiobtnDlgfilterWhole.isChecked = true
            radiobtnDlgfilterSortLatest.isChecked = true
            viewModel.radioId = R.id.radiobtn_dlgfilter_whole
        }
    }

    private fun initViews(){
        binding.run {
            btnDlgfilterStartdate.text = viewModel.dateTimeFrom
            btnDlgfilterEnddate.text = viewModel.dateTimeTo
            initRadioState()
        }
    }

    private fun initRadioState(){
        when(viewModel.radioId){
            R.id.radiobtn_dlgfilter_whole -> binding.radiobtnDlgfilterWhole.isChecked = true
            R.id.radiobtn_dlgfilter_today -> binding.radiobtnDlgfilterToday.isChecked = true
            R.id.radiobtn_dlgfilter_week -> binding.radiobtnDlgfilterWeek.isChecked = true
            R.id.radiobtn_dlgfilter_month -> binding.radiobtnDlgfilterMonth.isChecked = true
            R.id.radiobtn_dlgfilter_year -> binding.radiobtnDlgfilterYear.isChecked = true
            null -> binding.radiogroupDlgfilterDuration.clearCheck()
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
            reset()
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
                albumsFragment.refresh(null, null)
                viewModel.radioId = R.id.radiobtn_dlgfilter_whole
            }

            binding.radiobtnDlgfilterToday.id -> {
                albumsFragment.refresh(getRadioFilterDate(TimeUtil.TODAY), null)
                viewModel.radioId = R.id.radiobtn_dlgfilter_today
            }
            binding.radiobtnDlgfilterWeek.id -> {
                albumsFragment.refresh(getRadioFilterDate(TimeUtil.WEEK), null)
                viewModel.radioId = R.id.radiobtn_dlgfilter_week
            }

            binding.radiobtnDlgfilterMonth.id -> {
                albumsFragment.refresh(getRadioFilterDate(TimeUtil.MONTH), null)
                viewModel.radioId = R.id.radiobtn_dlgfilter_month
            }

            binding.radiobtnDlgfilterYear.id -> {
                albumsFragment.refresh(getRadioFilterDate(TimeUtil.YEAR), null)
                viewModel.radioId = R.id.radiobtn_dlgfilter_year
            }
            else -> {
                albumsFragment.refresh(getFilterDateFrom(binding.btnDlgfilterStartdate.text.toString()), getFilterDateTo(binding.btnDlgfilterEnddate.text.toString()))
                viewModel.radioId = null
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
                        viewModel.dateTimeFrom = dateToString(startDate)
                        if (compareDate(startDate, endDate)) {
                            binding.btnDlgfilterEnddate.text = dateToString(startDate)
                        }
                    }
                    R.id.btn_dlgfilter_enddate -> {
                        val startDate = getCalendar(binding.btnDlgfilterStartdate.text.toString())
                        val endDate = getCalendar(year, month, dayOfMonth)
                        view.text = dateToString(endDate)
                        viewModel.dateTimeTo = dateToString(endDate)
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