package com.example.nakshatratak

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class form : AppCompatActivity() {

    private lateinit var daySpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var yearSpinner: Spinner
    private lateinit var hourSpinner: Spinner
    private lateinit var minuteSpinner: Spinner
    private lateinit var ampmSpinner: Spinner
    private lateinit var countrySpinner: Spinner
    private lateinit var stateSpinner: Spinner
    private lateinit var citySpinner: Spinner
    private lateinit var addressInputLayout: TextInputLayout
    private lateinit var addressEditText: TextInputEditText
    private lateinit var countryInputLayout: TextInputLayout
    private lateinit var countryEditText: TextInputEditText
    private lateinit var genderRadioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        // Initialize views
        val nameEditText = findViewById<TextInputEditText>(R.id.name_edit_text)
        genderRadioGroup = findViewById(R.id.gender_radio_group)
        daySpinner = findViewById(R.id.day_spinner)
        monthSpinner = findViewById(R.id.month_spinner)
        yearSpinner = findViewById(R.id.year_spinner)
        hourSpinner = findViewById(R.id.hour_spinner)
        minuteSpinner = findViewById(R.id.minute_spinner)
        ampmSpinner = findViewById(R.id.ampm_spinner)
        countrySpinner = findViewById(R.id.country_spinner)
        stateSpinner = findViewById(R.id.state_spinner)
        citySpinner = findViewById(R.id.city_spinner)
        addressInputLayout = findViewById(R.id.address_input_layout)
        addressEditText = findViewById(R.id.address_edit_text)
        countryInputLayout = findViewById(R.id.country_input_layout)
        countryEditText = findViewById(R.id.country_edit_text)
        val submitButton = findViewById<MaterialButton>(R.id.submit_button)

        // Set up Spinners for Date of Birth
        val days = Array(31) { (it + 1).toString() }
        val months = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")
        val years = Array(100) { (2024 - it).toString() }

        daySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, days)
        monthSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, months)
        yearSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years)

        // Set up Spinners for Birth Time
        val hours = Array(12) { (it + 1).toString() }
        val minutes = Array(60) { String.format("%02d", it) }
        val ampm = arrayOf("AM", "PM")

        hourSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hours)
        minuteSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, minutes)
        ampmSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ampm)

        // Set up Spinners for Location
        val countries = arrayOf("Country", "India", "Other")
        val states = arrayOf("State", "California", "Texas", "New York")
        val cities = arrayOf("City", "Los Angeles", "Houston", "New York City")

        countrySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        stateSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, states)
        citySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)

        // Handle visibility of additional fields based on country selection
        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCountry = parent.getItemAtPosition(position).toString()

                if (selectedCountry == "Other") {
                    stateSpinner.visibility = View.GONE
                    citySpinner.visibility = View.GONE
                    countryInputLayout.visibility = View.VISIBLE
                    addressInputLayout.visibility = View.VISIBLE
                } else {
                    stateSpinner.visibility = View.VISIBLE
                    citySpinner.visibility = View.VISIBLE
                    countryInputLayout.visibility = View.GONE
                    addressInputLayout.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle case when no item is selected
            }
        }

        submitButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val gender = when (genderRadioGroup.checkedRadioButtonId) {
                R.id.gender_male -> "Male"
                R.id.gender_female -> "Female"
                R.id.gender_other -> "Other"
                else -> "Not Specified"
            }

            val day = daySpinner.selectedItem.toString()
            val month = monthSpinner.selectedItem.toString()
            val year = yearSpinner.selectedItem.toString()
            val hour = hourSpinner.selectedItem.toString()
            val minute = minuteSpinner.selectedItem.toString()
            val ampm = ampmSpinner.selectedItem.toString()
            val birthDate = "$day $month, $year"
            val birthTime = "$hour:$minute $ampm"
            val country = if (countrySpinner.selectedItem.toString() == "Other") {
                countryEditText.text.toString()
            } else {
                countrySpinner.selectedItem.toString()
            }

            val state = if (stateSpinner.visibility == View.VISIBLE) {
                stateSpinner.selectedItem.toString()
            } else {
                "N/A"
            }

            val city = if (citySpinner.visibility == View.VISIBLE) {
                citySpinner.selectedItem.toString()
            } else {
                "N/A"
            }

            val address = if (addressInputLayout.visibility == View.VISIBLE) {
                addressEditText.text.toString()
            } else {
                "N/A"
            }

            // Handle form submission
            // For example, you could validate inputs or send them to a server
        }
    }
}
