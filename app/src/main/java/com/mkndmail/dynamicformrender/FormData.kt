package com.mkndmail.dynamicformrender

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


//"id":"customer_name","type":"EditText","label":"Name","hintText":"Name","required":"False",
// "validator":[{"type":"Text","Value":"Alphabetic"},{"type":"Regex","Value":"/^\\w+/"}]}


/**
 * Created by Mukund, mkndmail@gmail.com on 12, August, 2020 , 02:35 PM
 */


@JsonClass(generateAdapter = true)
data class Form(val data: List<FormData>)

@JsonClass(generateAdapter = true)
data class FormData(
    val id: String,
    val type: String,
    val label: String,
    val hintText: String,
    val required: String,
    val validator: List<ValidatorClass>
)

@JsonClass(generateAdapter = true)
data class ValidatorClass(val type: String, @Json(name = "Value") val value: String)

@Parcelize
@JsonClass(generateAdapter = true)
data class SubmissionData(val formData: List<SubmitData>) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class SubmitData(val id: String, val text: String?) : Parcelable
