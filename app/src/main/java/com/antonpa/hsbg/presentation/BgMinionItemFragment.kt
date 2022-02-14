package com.antonpa.hsbg.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antonpa.hsbg.R
import com.antonpa.hsbg.domain.BgMinionItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BgMinionItemFragment : Fragment() {

    private lateinit var txtLayoutName: TextInputLayout
    private lateinit var txtLayoutCost: TextInputLayout
    private lateinit var txtEditName: TextInputEditText
    private lateinit var txtEditCost: TextInputEditText
    private lateinit var btnSave: Button

    private lateinit var viewModel: BgMinionItemViewModel
    private var screenMode: String = UNDEFINED_EXTRA_MODE
    private var screenBgMinionId: Int = BgMinionItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkFragmentParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bg_minion_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BgMinionItemViewModel::class.java]
        initViews(view)
        runCorrectMode()
        addEditTextChangeListener()
        addViewModelObserve()
    }

    private fun addViewModelObserve() {
        viewModel.isCorrectNameLiveData.observe(viewLifecycleOwner) {
            val message = if (!it) getString(R.string.error_input_name) else null
            txtLayoutName.error = message
        }

        viewModel.isCorrectCostLiveData.observe(viewLifecycleOwner) {
            val message = if (!it) getString(R.string.error_input_cost) else null
            txtLayoutCost.error = message
        }

        viewModel.isCanBeClosedLiveData.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun runCorrectMode() {
        when (screenMode) {
            MODE_SHOW -> launchShowMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addEditTextChangeListener() {
        txtEditName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        txtEditCost.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCost()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchAddMode() {
        btnSave.setOnClickListener {
            viewModel.addBgMinionItem(txtEditName.text?.toString(), txtEditCost.text?.toString())
        }
    }

    private fun launchShowMode() {
        viewModel.getBgMinionItem(screenBgMinionId)
        viewModel.bgMinionItemLiveData.observe(viewLifecycleOwner) {
            txtEditName.setText(it.name)
            txtEditCost.setText(it.cost.toString())
        }
        btnSave.setOnClickListener {
            viewModel.showBgMinionItem()
        }
    }

    private fun checkFragmentParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE))
            throw RuntimeException("Extra mode undefined")
        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_SHOW)
            throw RuntimeException("Unknown extra mode $mode")
        screenMode = mode
        if (screenMode == MODE_SHOW) {
            if (!args.containsKey(BG_MINION_ID))
                throw RuntimeException("Extra bg minion id undefined")
            screenBgMinionId = args.getInt(BG_MINION_ID, BgMinionItem.UNDEFINED_ID)
        }
    }

    private fun initViews(view: View) {
        txtLayoutName = view.findViewById(R.id.txt_layout_name)
        txtLayoutCost = view.findViewById(R.id.txt_layout_cost)
        txtEditName = view.findViewById(R.id.txt_edit_name)
        txtEditCost = view.findViewById(R.id.txt_edit_cost)
        btnSave = view.findViewById(R.id.btn_save)
    }

    companion object {
        private const val SCREEN_MODE = "screen_mode"
        private const val BG_MINION_ID = "bg_minion_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_SHOW = "mode_show"

        private const val UNDEFINED_EXTRA_MODE = ""


        fun createNewInstanceAddItem(): BgMinionItemFragment =
            BgMinionItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }


        fun createNewInstanceShowItem(bgMinionId: Int): BgMinionItemFragment =
            BgMinionItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_SHOW)
                    putInt(BG_MINION_ID, bgMinionId)
                }
            }
    }
}