package com.antonpa.hsbg.presentation

import android.content.Context
import android.content.Intent
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

class BgMinionItemFragment(
    private val screenMode: String = UNDEFINED_EXTRA_MODE,
    private val screenBgMinionId: Int = BgMinionItem.UNDEFINED_ID
) : Fragment() {

    private lateinit var txtLayoutName: TextInputLayout
    private lateinit var txtLayoutCost: TextInputLayout
    private lateinit var txtEditName: TextInputEditText
    private lateinit var txtEditCost: TextInputEditText
    private lateinit var btnSave: Button

    private lateinit var viewModel: BgMinionItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bg_minion_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkFragmentParams()
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
            MODE_SHOW_INTENT -> launchShowMode()
            MODE_ADD_INTENT -> launchAddMode()
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
        if (screenMode != MODE_ADD_INTENT && screenMode != MODE_SHOW_INTENT)
            throw RuntimeException("Unknown parameter $screenMode")
        if (screenMode == MODE_SHOW_INTENT && screenBgMinionId == BgMinionItem.UNDEFINED_ID)
            throw RuntimeException("Parameter bg minion id undefined")
    }

    private fun initViews(view: View) {
        txtLayoutName = view.findViewById(R.id.txt_layout_name)
        txtLayoutCost = view.findViewById(R.id.txt_layout_cost)
        txtEditName = view.findViewById(R.id.txt_edit_name)
        txtEditCost = view.findViewById(R.id.txt_edit_cost)
        btnSave = view.findViewById(R.id.btn_save)
    }

    companion object {
        private const val EXTRA_MODE_INTENT = "extra_mode"
        private const val EXTRA_BG_MINION_ID_INTENT = "extra_bg_minion_id"
        private const val MODE_ADD_INTENT = "mode_add"
        private const val MODE_SHOW_INTENT = "mode_show"

        private const val UNDEFINED_EXTRA_MODE = ""

        fun createIntentAddItem(context: Context): Intent {
            val intent = Intent(context, BgMinionItemActivity::class.java)
            intent.putExtra(EXTRA_MODE_INTENT, MODE_ADD_INTENT)
            return intent
        }

        fun createIntentShowItem(context: Context, bgMinionId: Int): Intent {
            val intent = Intent(context, BgMinionItemActivity::class.java)
            intent.putExtra(EXTRA_MODE_INTENT, MODE_SHOW_INTENT)
            intent.putExtra(EXTRA_BG_MINION_ID_INTENT, bgMinionId)
            return intent
        }

        fun createNewInstanceAddItem(): BgMinionItemFragment = BgMinionItemFragment(MODE_ADD_INTENT)

        fun createNewInstanceShowItem(bgMinionId: Int): BgMinionItemFragment =
            BgMinionItemFragment(MODE_SHOW_INTENT, bgMinionId)
    }
}