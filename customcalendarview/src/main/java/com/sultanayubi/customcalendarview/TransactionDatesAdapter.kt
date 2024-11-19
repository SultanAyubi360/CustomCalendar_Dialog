package com.sultanayubi.customcalendarview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.sultanayubi.customcalendarview.databinding.TransactionDateListItemBinding


class TransactionDatesAdapter(
    private val context: Context,
    private var selectedPosition: Int = 0, // Default 1st selected
    private val onTap: ((PastDatesResponse) -> Unit)? = null
) : BaseAdapter<PastDatesResponse, TransactionDateListItemBinding>(R.layout.transaction_date_list_item) {

    private var selectedTextColor: Int = Color.TRANSPARENT // Use transparent color
    private var unselectedTextColor: Int = Color.TRANSPARENT
    private var textviewBackground: Int = R.drawable.white_bg_calendar

    // Method to set the selected text color
    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedTextColor(color: Int) {
        selectedTextColor = color
        notifyDataSetChanged() // Notify the adapter to refresh the items
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTextViewBackground(drawableRes: Int){
        textviewBackground = drawableRes
        notifyDataSetChanged() // Notify adapter to refresh
    }
    // Method to set the unselected text color
    @SuppressLint("NotifyDataSetChanged")
    fun setUnselectedTextColor(color: Int) {
        unselectedTextColor = color
        notifyDataSetChanged() // Notify the adapter to refresh the items
    }

    override fun onItemInflated(
        item: PastDatesResponse,
        position: Int,
        binding: TransactionDateListItemBinding,
    ) {
        val customFont = ResourcesCompat.getFont(context, R.font.manrope_regular)
        if (selectedPosition == position) {
            binding.datesText.setTypeface(customFont, Typeface.BOLD)
            binding.datesText.setTextColor(selectedTextColor) // Use selected text color
        } else {
            binding.datesText.setTypeface(customFont, Typeface.NORMAL)
            binding.datesText.setTextColor(unselectedTextColor) // Use unselected text color
        }
        binding.datesText.text = item.pastDate
        binding.datesText.setBackgroundResource(textviewBackground)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(item: PastDatesResponse, index: Int) {
        // Update the selected position
        selectedPosition = index
        notifyDataSetChanged() // Refresh the adapter to reflect changes
        onTap?.invoke(item) // Invoke the callback with the clicked item
    }

}