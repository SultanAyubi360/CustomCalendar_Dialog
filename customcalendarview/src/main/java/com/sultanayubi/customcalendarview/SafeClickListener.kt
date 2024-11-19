package com.sultanayubi.customcalendarview

import android.os.SystemClock
import android.view.View
import com.sultanayubi.customcalendarview.SafeClicking.lastTimeClicked



class SafeClickListener(
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < SafeClicking.defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }

}
object SafeClicking{
    var defaultInterval: Int = 600
    var lastTimeClicked: Long = 0

    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }
}