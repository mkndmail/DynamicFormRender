package com.mkndmail.dynamicformrender

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.mkndmail.dynamicformrender.databinding.ActivityMainBinding
import com.mkndmail.dynamicformrender.databinding.DynamicEditTextBinding
import java.util.*

/**
 * Created by Mukund, mkndmail@gmail.com on 12, August, 2020 , 02:30 PM
 */

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        renderUI()
        activityMainBinding.button.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val submittedData = mutableListOf<SubmitData>()
        val childCounts: Int = activityMainBinding.llDynamicForm.childCount
        var isValid = true
        for (i in 0 until childCounts) {
            val view: View = activityMainBinding.llDynamicForm.getChildAt(i)
            val editText =
                (view as TextInputLayout).getChildAt(0)
                    .findViewById<EditText>(R.id.edt_text)
            val formData = editText.tag as FormData
            if (formData.required.equals("True", true)) {
                if (editText.getTextFromEditText().isNotEmpty()) {
                    showError(view, false, null)
                    if (editText.getTextFromEditText()
                            .isValidDataEntered(formData.validator.last().value)
                    ) {
                        showError(view, false, null)
                        submittedData.add(SubmitData(formData.id, editText.getTextFromEditText()))
                    } else {
                        isValid = false
                        showError(view, true, "Entered data is not correct")
                    }
                } else {
                    isValid = false
                    showError(view, true, "Input value is required")
                }
            } else {
                submittedData.add(SubmitData(formData.id, editText.getTextFromEditText()))
            }

        }
        if (isValid) {
            val intent = Intent(this, ShowFormDataActivity::class.java).apply {
                putExtra(ENTERED_FORM_DATA, SubmissionData(submittedData))
            }
            startActivity(intent)
        }

    }

    private fun renderUI() {
        getDataClassFromJson()?.let { form ->
            form.data.forEach { formData ->
                val binding: DynamicEditTextBinding = DynamicEditTextBinding.inflate(layoutInflater)
                val view = binding.root
                binding.txtInputLayout.hint = formData.hintText
                binding.edtText.tag = formData

                /*
                To change the input type simply change the type value in validator and the appropriate
                * keyboard is shown to the user
                */

                when (formData.validator.first().type.toLowerCase(Locale.getDefault())) {
                    "text" -> binding.edtText.inputType = InputType.TYPE_CLASS_TEXT
                    "email" -> binding.edtText.inputType =
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    "password" -> binding.edtText.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    "number" -> binding.edtText.inputType = InputType.TYPE_CLASS_NUMBER
                }
                activityMainBinding.llDynamicForm.addView(view)
            }
        }
    }

    private fun showError(inputLayout: TextInputLayout, showFlag: Boolean, errorString: String?) {
        when (showFlag) {
            true -> {
                inputLayout.isErrorEnabled = true
                inputLayout.error = errorString
            }
            else -> {
                inputLayout.isErrorEnabled = false
                inputLayout.error = errorString
            }
        }
    }

    companion object {
        const val ENTERED_FORM_DATA = "entered_form_data"
    }
}