package com.antonpa.hsbg.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.antonpa.hsbg.R
import com.antonpa.hsbg.domain.BgMinionItem
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class BgMinionItemActivity : AppCompatActivity() {

    private lateinit var txtLayoutName: TextInputLayout
    private lateinit var txtLayoutCost: TextInputLayout
    private lateinit var txtEditName: TextInputEditText
    private lateinit var txtEditCost: TextInputEditText
    private lateinit var btnSave: Button

    private lateinit var viewModel: BgMinionItemViewModel

    private var extraMode = UNDEFINED_EXTRA_MODE
    private var extraBgMinionId = BgMinionItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_minion_item)
        checkIntentExtras()
        viewModel = ViewModelProvider(this)[BgMinionItemViewModel::class.java]
        initViews()
        runCorrectMode()
        addEditTextChangeListener()
        addViewModelObserve()
    }

    private fun addViewModelObserve() {
        viewModel.isCorrectNameLiveData.observe(this) {
            val message = if (!it) getString(R.string.error_input_name) else null
            txtLayoutName.error = message
        }

        viewModel.isCorrectCostLiveData.observe(this) {
            val message = if (!it) getString(R.string.error_input_cost) else null
            txtLayoutCost.error = message
        }

        viewModel.isCanBeClosedLiveData.observe(this) {
            finish()
        }
    }

    private fun runCorrectMode() {
        when (extraMode) {
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
        viewModel.getBgMinionItem(extraBgMinionId)
        viewModel.bgMinionItemLiveData.observe(this) {
            txtEditName.setText(it.name)
            txtEditCost.setText(it.cost.toString())
        }
        btnSave.setOnClickListener {
            viewModel.showBgMinionItem()
        }
    }

    private fun checkIntentExtras() {
        if (!intent.hasExtra(EXTRA_MODE_INTENT))
            throw RuntimeException("Extra mode undefined")
        val mode = intent.getStringExtra(EXTRA_MODE_INTENT)
        if (mode != MODE_ADD_INTENT && mode != MODE_SHOW_INTENT)
            throw RuntimeException("Unknown extra mode $mode")
        extraMode = mode
        if (extraMode == MODE_SHOW_INTENT) {
            if (!intent.hasExtra(EXTRA_BG_MINION_ID_INTENT))
                throw RuntimeException("Extra bg minion id undefined")
            extraBgMinionId =
                intent.getIntExtra(EXTRA_BG_MINION_ID_INTENT, BgMinionItem.UNDEFINED_ID)
        }
    }

    private fun initViews() {
        txtLayoutName = findViewById(R.id.txt_layout_name)
        txtLayoutCost = findViewById(R.id.txt_layout_cost)
        txtEditName = findViewById(R.id.txt_edit_name)
        txtEditCost = findViewById(R.id.txt_edit_cost)
        btnSave = findViewById(R.id.btn_save)
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
    }
}