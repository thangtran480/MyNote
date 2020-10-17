package com.thangtv.mynote.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.thangtv.mynote.R
import com.thangtv.mynote.data.database.StatusRepository
import com.thangtv.mynote.data.entity.Note
import com.thangtv.mynote.ui.popup.CustomTimePickerDialog
import com.thangtv.mynote.util.Utils
import com.thangtv.mynote.viewmodel.AddNoteViewModel
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.util.*
import com.thangtv.mynote.databinding.FragmentAddNoteBinding

@Suppress("DEPRECATION")
class AddNoteFragment : Fragment(), PopupMenu.OnMenuItemClickListener{

    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var navController: NavController

    private var binding : FragmentAddNoteBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        addNoteViewModel = ViewModelProviders.of(requireActivity()).get(AddNoteViewModel::class.java)

        binding = DataBindingUtil.bind(view)
        binding?.lifecycleOwner = this

        initView()
        initLiveData()
    }


    /**
     * init params
     * @param
     * @return: null
     *
     * @author: thangtv
     */
    @SuppressLint("SetTextI18n")
    private fun initParam(note : Note?){

        val startTimeListener = TimePickerDialog.OnTimeSetListener{ _, hour, minute ->
            addNoteViewModel.startTimeAlertPicker.updateTime(hour, minute)
            tv_start_time_alert.text = "$hour:$minute"
        }

        val startDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            addNoteViewModel.startDateAlertPicker.updateDate(year, month, day)
            tv_start_date_alert.text = "$day/$month/$year"
        }

        val endTimeListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            addNoteViewModel.endTimeAlertPicker.updateTime(hour, minute)
            tv_end_time_alert.text = "$hour:$minute"
        }


        val endDateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            addNoteViewModel.endDateAlertPicker.updateDate(year, month, day)
            tv_end_date_alert.text = "$day/$month/$year"
        }

        val calendarStart = Calendar.getInstance()
        val calendarEnd = Calendar.getInstance()

        if (note != null){

            ed_title.setText(note.title)
            ed_description.setText(note.description)

            calendarStart.time = note.startDateAlert
            calendarEnd.time = note.endDateAlert

            tv_repeat.text = Utils.convertRepeat(requireContext(), note.repeat)

        }else{
            calendarStart.set(Calendar.MINUTE, calendarStart.get(Calendar.MINUTE)+10)
            calendarEnd.set(Calendar.HOUR_OF_DAY, calendarEnd.get(Calendar.HOUR_OF_DAY) + 1)

        }
        addNoteViewModel.startTimeAlertPicker    = CustomTimePickerDialog(context, startTimeListener, calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE), true)
        addNoteViewModel.startDateAlertPicker    = DatePickerDialog(requireContext(), startDateListener, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH))

        tv_start_time_alert.text                 = "${calendarStart.get(Calendar.HOUR_OF_DAY)}:${calendarStart.get(Calendar.MINUTE)}"
        tv_start_date_alert.text                 =  "${calendarStart.get(Calendar.DAY_OF_MONTH)}/${calendarStart.get(Calendar.MONTH)}/${calendarStart.get(Calendar.YEAR)}"

        addNoteViewModel.endTimeAlertPicker      = CustomTimePickerDialog(context, endTimeListener, calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE), true)
        addNoteViewModel.endDateAlertPicker      = DatePickerDialog(requireContext(), endDateListener, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH))

        tv_end_time_alert.text                   = "${calendarEnd.get(Calendar.HOUR_OF_DAY)}:${calendarEnd.get(Calendar.MINUTE)}"
        tv_end_date_alert.text                   = "${calendarEnd.get(Calendar.DAY_OF_MONTH)}/${calendarEnd.get(Calendar.MONTH)}/${calendarEnd.get(Calendar.YEAR)}"


        addNoteViewModel.setNoteValue(note)
        binding?.note = addNoteViewModel.note
    }


    /**
     * Init view
     * @param
     * @return: null
     *
     * @author: thangtv
     */
    private fun initView(){

        btn_save.setOnClickListener {
            onClickBtnSave()
        }
        tv_repeat.setOnClickListener {
            showPopupRepeat(it)
        }

    }


    /**
     * init live date
     * @param
     * @return
     *
     * @author: thangtv
     */
    private fun initLiveData() {
        addNoteViewModel.statusRepository.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when(it.code){
                StatusRepository.PROCESSING ->{

                }
                StatusRepository.SUCCESSFUL ->{
                    Log.e("DEMO", "SUCCESSFUL")
                    navController.navigate(R.id.action_AddNoteFragment_to_ListNoteFragment)
                }
                StatusRepository.ERROR ->{
                    Log.e("DEMO", "ERROR")
                }
            }
        })

        addNoteViewModel.allNotes.observe(viewLifecycleOwner, androidx.lifecycle.Observer {addNote ->
            var note: Note? = null

            if (arguments != null && addNote.isNotEmpty()){
                arguments?.get(ListNoteFragment.KEY_NOTE).let{
                    if (it != null){
                        val position = it as Int
                        note = addNoteViewModel.noteRepository.allNote.value?.get(position)
                    }
                }
            }
            initParam(note)
        })

    }

    private fun showPopupRepeat(v: View){
        val popupMenu = PopupMenu(context, v)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.inflate(R.menu.menu_popup_repeat)
        popupMenu.show()
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {

        addNoteViewModel.note.repeat = when(p0?.itemId){
            R.id.item_repeat_none ->{
                0
            }
            R.id.item_repeat_daily ->{
                1
            }
            R.id.item_repeat_every_week ->{
                2
            }
            R.id.item_repeat_monthly ->{
                3
            }
            R.id.item_repeat_yearly ->{
                4
            }
            else -> {
                0
            }
        }

        tv_repeat.text = Utils.convertRepeat(requireContext(), addNoteViewModel.note.repeat)

        return true
    }

    private fun onClickBtnSave(){
        addNoteViewModel.insert(context = requireContext())
    }

}