
package com.neotrick.callinfos.utility_section


import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import com.otpverification.R


import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object CommonUtils {

    fun showLoadingDialog(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    fun isEmailValid(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    fun isPhoneValid(phone:String): Boolean {
        return if (TextUtils.isEmpty(phone)) {
            false
        } else {
            android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

    fun isNameValid(name: String): Boolean {
        return if (TextUtils.isEmpty(name)) {
            false
        } else {
            name.matches("^[A-Za-z]+$".toRegex())
        }
    }

    fun localToGMT(receivedDateInLongStringForGoogleFit: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return ""+sdf.format(receivedDateInLongStringForGoogleFit)
    }


    fun localToGMTStr(dateStr: String):String{
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date=sdf.parse(dateStr)
        return ""+sdf.format(date!!)
    }


    fun convertStringToList(receivedData: String): ArrayList<Int>
    {
        val regex = ","
        val sampleData = receivedData.split(regex).toMutableList()
        val sampleDayList=arrayListOf<Int>()
        if(sampleData[0] == "true"){
            sampleDayList.add(Calendar.MONDAY)
        }
        if(sampleData[1] == "true"){
            sampleDayList.add(Calendar.TUESDAY)
        }
        if(sampleData[2] == "true"){
            sampleDayList.add(Calendar.WEDNESDAY)
        }
        if(sampleData[3] == "true"){
            sampleDayList.add(Calendar.THURSDAY)
        }
        if(sampleData[4] == "true"){
            sampleDayList.add(Calendar.FRIDAY)
        }
        if(sampleData[5] == "true"){
            sampleDayList.add(Calendar.SATURDAY)
        }
        if(sampleData[6] == "true"){
            sampleDayList.add(Calendar.SUNDAY)
        }
        return sampleDayList
    }

    fun getDiffYears(first: Date, last: Date): Int {
        val a = getCalendar(first)
        val b = getCalendar(last)
        var diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR)
        if (a.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR)) {
            diff--
        }
        return diff
    }

    private fun getCalendar(date: Date): Calendar {
        val cal = Calendar.getInstance(Locale.US)
        cal.time = date
        return cal
    }

    fun isValidPassword(password: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        //val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val passwordPattern="^(?=.*[0-9])(?=.*[!@#\$*])(?=.*[a-z])(?=.*[A-Z])(?=.{8,})"
        pattern = Pattern.compile(passwordPattern)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isDateGreater(lastDate: String, newDate: String):Boolean{
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        val lastDateSeen = sdf.parse(lastDate)
        val newDateSeen = sdf.parse(newDate)
        if (newDateSeen.time> lastDateSeen.time) {
            return true
        }
        return false
    }

    fun showGreetingsForUser():String{
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]

        if (timeOfDay in 0..11) {
            return "Good Morning, "
        } else if (timeOfDay in 12..15) {
            return "Good Afternoon, "
        } else {
            return "Good Evening, "
        }
    }

    fun getDifference(startDate: Date, endDate: Date):String {
        //milliseconds
        var different = endDate.time - startDate.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different %= daysInMilli
        val elapsedHours = different / hoursInMilli
        different %= hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        var days=""+elapsedDays
        if(days == "0")
        {
            days=""+elapsedHours
            return if(days == "0") {
                days=""+elapsedMinutes
                if(days == "0"){
                    "now"
                }else{
                    "$days minutes"
                }
            }else{
                if(days == "1"){
                    "$days hour"
                }else{
                    "$days hours"
                }
            }
        }else{
            return if(days == "1"){
                "$days day"
            }else{
                "$days days"
            }

        }
    }

    fun isToday(date: Date?): Boolean {
        val today = Calendar.getInstance()
        val specifiedDate = Calendar.getInstance()
        specifiedDate.time = date
        return today[Calendar.DAY_OF_MONTH] === specifiedDate[Calendar.DAY_OF_MONTH] && today[Calendar.MONTH] === specifiedDate[Calendar.MONTH] && today[Calendar.YEAR] === specifiedDate[Calendar.YEAR]
    }

    fun getDateDifferenceInDays(startDate: Date, endDate: Date):Int{
        val differenceInDays=getDifference(startDate, endDate)
        return if(differenceInDays.contains("day", true)||differenceInDays.contains("days", true)){
            val differenceDay =differenceInDays.split(" ").toTypedArray()
            differenceDay[0].toInt()
        } else{
            0
        }
    }

}// This utility class is not publicly instantiable
