package com.mkndmail.dynamicformrender

import android.widget.EditText
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.util.regex.Pattern

/**
 * Created by Mukund, mkndmail@gmail.com on 12, August, 2020 , 02:45 PM
 */

/*
* Sample Json Data
* */
private const val formData =
    "{   \"data\":[{\"id\":\"customer_name\",\"type\":\"EditText\",\"label\":\"Name\",\"hintText\":\"Name\",\"required\":\"False\",\"validator\":[{\"type\":\"Text\"," +
            "\"Value\":\"Alphabetic\"},{\"type\":\"Regex\",\"Value\":\"/^\\\\w+/\"}]},{\"id\":\"customer_email\",\"type\":\"EditText\",\"label\":\"Email\",\"hintText\"" +
            ":\"Email\",\"required\":\"True\",\"validator\":[{\"type\":\"Text\",\"Value\":\"Alphanumeric\"},{\"type\":\"Regex\",\"Value\":\"^\\\\w+@[a-zA-Z_]+?\\\\." +
            "[a-zA-Z]{2,3}\$\"}\n" + "]},{\"id\":\"customer_password\",\"type\":\"EditText\",\"label\":\"Password\",\"hintText\":\"Password\",\"required\":\"True\"" +
            ",\"validator\":[{\"type\":\"password\",\"Value\":\"Alphanumeric\"},{\"type\":\"Regex\",\"Value\":\"^(?=.*[A-Za-z])(?=.*\\\\d)[A-Za-z\\\\d]{8,}\$\"}]}   ]}"


private val moshi = Moshi.Builder().build()

/*
* This method creates an Appropriate Kotlin Data class from a json
* */
fun getDataClassFromJson(): Form? {
    val jsonAdapter: JsonAdapter<Form> = moshi.adapter(Form::class.java)
    return jsonAdapter.fromJson(formData)
}

/*
* An extension function on the top of Edittext to get the entered text from EditText
* */
fun EditText.getTextFromEditText(): String = this.text.toString()

/*
*  An extension function on the top of the String to validate the input text
* */
fun String.isValidDataEntered(regex: String): Boolean =
    Pattern.compile(regex).matcher(this).matches()