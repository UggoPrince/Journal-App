package com.journalingapp.util

import android.content.Intent
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.journalingapp.JournalDetailsActivity
import com.journalingapp.MainActivity
import com.journalingapp.R
import com.journalingapp.model.Journal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RecyclerViewHolder(itemView: View, private var activity: MainActivity) :
    RecyclerView.ViewHolder(itemView) {
    private val journalTitle: TextView by lazy {itemView.findViewById(R.id.title_tv)}
    private val dateText: TextView by lazy { itemView.findViewById(R.id.date_tv) }
    val mLayout: ConstraintLayout by lazy { itemView.findViewById(R.id.journal_details_ll) }
    val checkBox: CheckBox by lazy { itemView.findViewById(R.id.deleteCheckbox)}

    fun setClickListeners(journal: Journal) {
//        val intent = Intent(itemView.context, JournalDetailsActivity::class.java)
//        intent.putExtra("id", journal.id.toString())
//        this.mLayout.setOnClickListener {
//            if (!Adapter.showCheckboxes) {
//                itemView.context.startActivity(intent)
//            } else {
//                this.checkBox.isChecked = !this.checkBox.isChecked
//                journal.isChecked = !this.checkBox.isChecked
//                if (this.checkBox.isChecked) {
//                    Adapter.itemSelectedCount += 1
//                    Adapter.addSelectedItemToList(journal)
//                    mLayout.setBackgroundResource(R.color.primary_light)
//                } else {
//                    this.mLayout.setBackgroundColor(Color.TRANSPARENT)
//                    Adapter.itemSelectedCount -= 1
//                    Adapter.removeSelectedItemFromList(journal)
//                }
//                // activity.showCheckboxCheckedCount()
//            }
//        }
    }

    fun viewJournalDetails(id: String) {
        val intent = Intent(itemView.context, JournalDetailsActivity::class.java)
        intent.putExtra("id", id)
        itemView.context.startActivity(intent)
    }

    fun bind(journal: Journal) {
        this.journalTitle.text = journal.title
        this.dateText.text = dateFormatter(journal.updatedAt)
    }

    private fun dateFormatter(date: Date): String {
        val now = Date()
        val currentYear = getSpecifiedDateString("dd MMM yyyy", now)
        val currentYearArr = currentYear.split(" ")
        val year = getSpecifiedDateString("dd MMM yyyy", date)
        val yearArr = year.split(" ")
        if (currentYearArr[2] == yearArr[2]) {
            val currentDay = currentYearArr[0].toInt()
            val day = yearArr[0].toInt()
            if (day == currentDay) {
                if (currentYearArr[1].equals(yearArr[1], ignoreCase = true)) {
                    return getSpecifiedDateString("hh:mm a", date)
                }
            }
            return "${yearArr[0]} ${yearArr[1]}"
        }
        return year
    }

    private fun getSpecifiedDateString(formatStr: String, date: Date): String {
        return SimpleDateFormat(formatStr, Locale.getDefault()).format(date)
    }

    companion object {
        private val TAG = RecyclerViewHolder::class.java.simpleName
    }
}