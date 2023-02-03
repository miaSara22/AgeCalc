package com.miaekebom.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDatePicker.setOnClickListener {
            displayDatePicker()
        }
    }

    private fun displayDatePicker(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this,
                {_, selectedYear, selectedMonth, selectedDayOfMonth ->

                    tv1.visibility = View.VISIBLE
                    tv2.visibility = View.VISIBLE
                    tv3.visibility = View.VISIBLE
                    tv4.visibility = View.VISIBLE
                    tv5.visibility = View.VISIBLE

                    val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                    tvSelectedDate?.text = selectedDate

                    val sdf = SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH)
                    val df = DecimalFormat("#.#")
                    df.roundingMode = RoundingMode.DOWN

                    val theChosenDate = sdf.parse(selectedDate)
                    theChosenDate?.let {

                        val selectedDateInMinutes: Double = theChosenDate.time / 60000.0
                        val selectedDateInDays: Double = theChosenDate.time / 86400000.0
                        val selectedDateInMonths: Double = theChosenDate.time / 2629746000.0
                        val selectedDateInYears: Double = theChosenDate.time / 31556952000.0

                        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                        currentDate?.let {
                            val currentDateInMinutes: Double = currentDate.time / 60000.0
                            val currentDateInDays: Double = currentDate.time / 86400000.0
                            val currentDateInMonths: Double = currentDate.time / 2629746000.0
                            val currentDateInYears: Double = currentDate.time / 31556952000.0

                            val differenceInMinutes: Float = (currentDateInMinutes - selectedDateInMinutes).toFloat()
                            val differenceInDays: Float = (currentDateInDays - selectedDateInDays).toFloat()
                            val differenceInMonths: Float = (currentDateInMonths - selectedDateInMonths).toFloat()
                            val differenceInYears: Float = (currentDateInYears - selectedDateInYears).toFloat()

                            tvAgeInMinutes?.text = df.format(differenceInMinutes).toString()
                            tvAgeInDays?.text = df.format(differenceInDays).toString()
                            tvAgeInMonths?.text = df.format(differenceInMonths).toString()
                            tvAgeInYears?.text = df.format(differenceInYears).toString()
                        }
                    }
                },
                year,
                month,
                day
            )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePickerDialog.show()
    }
}