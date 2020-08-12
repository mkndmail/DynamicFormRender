package com.mkndmail.dynamicformrender

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.mkndmail.dynamicformrender.MainActivity.Companion.ENTERED_FORM_DATA
import com.mkndmail.dynamicformrender.databinding.ActivityShowFormDataBinding
import com.mkndmail.dynamicformrender.databinding.DynamicEditTextBinding


/**
 * Created by Mukund, mkndmail@gmail.com on 12, August, 2020 , 02:55 PM
 */


class ShowFormDataActivity : AppCompatActivity() {
    private val binding: ActivityShowFormDataBinding by lazy {
        ActivityShowFormDataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.filled_form)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val submittedData = intent.getParcelableExtra<SubmissionData>(ENTERED_FORM_DATA)
        renderUI(submittedData?.formData)
    }

    private fun renderUI(formData: List<SubmitData>?) {
        formData?.let {
            it.forEach { submitData ->
                val linearLayout = LinearLayout(this)
                linearLayout.apply {
                    orientation = LinearLayout.VERTICAL
                }
                val dynamicEditTextBinding: DynamicEditTextBinding =
                    DynamicEditTextBinding.inflate(layoutInflater)
                val view = dynamicEditTextBinding.root
                dynamicEditTextBinding.txtInputLayout.hint = submitData.id
                dynamicEditTextBinding.edtText.setText(submitData.text)
                dynamicEditTextBinding.edtText.isFocusable = false
                dynamicEditTextBinding.edtText.isEnabled = false
                linearLayout.addView(view)
                binding.llDynamicData.addView(linearLayout)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}