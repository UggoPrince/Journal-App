package com.journal.util

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.journal.JournalDetailsActivity
import com.journal.MainActivity
import com.journal.R
import com.journal.model.Journal


class Adapter(
    private val journals: List<Journal>,
    private val activity: MainActivity,
) :
    RecyclerView.Adapter<RecyclerViewHolder>() {
    var closeCheckBoxes = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val layoutView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.journal_list, parent, false)
        return RecyclerViewHolder(layoutView, activity)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(journals[position])
        holder.checkBox.visibility = if (showCheckboxes) View.VISIBLE else View.GONE
        if (selectAllButtonChecked) {
            checkABox(holder, position, true)
        } else if (itemSelectedPosition.contains(position)) {
            checkABox(holder, position, true)
        } else {
            checkABox(holder, position, false)
        }

        holder.mLayout.setOnLongClickListener {
            showCheckboxes = true
            selectItem(position)
            true
        }

        holder.mLayout.setOnClickListener {
            handleClick(holder, position)
        }

        holder.checkBox.setOnClickListener {
            journals[position].isChecked = holder.checkBox.isChecked
            handleItemSelectedCount(holder.checkBox.isChecked, position)
            activity.showCheckboxCheckedCount()
        }
    }

    override fun getItemCount(): Int {
        return journals.size
    }

    private fun selectItem(position: Int) {
        journals[position].isChecked = true
        itemSelectedCount += 1
        itemSelectedPosition.add(position)
        activity.showSelectAllCheckbox(true)
        notifyDataSetChanged()
    }

    private fun handleItemSelectedCount(checked: Boolean, position: Int) {
        if (checked) {
            itemSelectedCount += 1
            itemSelectedPosition.add(position)
        } else {
            itemSelectedCount -= 1
            itemSelectedPosition.remove(position)
        }
    }

    private fun handleClick(holder: RecyclerViewHolder, position: Int) {
        if (showCheckboxes) {
            holder.checkBox.isChecked = !holder.checkBox.isChecked
            journals[position].isChecked = !holder.checkBox.isChecked
            handleItemSelectedCount(holder.checkBox.isChecked, position)
            activity.showCheckboxCheckedCount()
        } else {
            holder.viewJournalDetails(journals[position].id.toString())
        }
    }

    private fun checkABox(holder: RecyclerViewHolder, position: Int, check: Boolean) {
        holder.checkBox.isChecked = check
        journals[position].isChecked = check
    }

    private fun toggleViewVisibility(
        linearLayout: LinearLayout,
        constraintLayout: ConstraintLayout, checkbox: CheckBox) {
        if (showCheckboxes) {
            val animation: Animation =
                TranslateAnimation(
                    0f,
                    linearLayout.width.toFloat(), 0f, 0f)

            checkbox.animate()
                .alpha(1f)
                .setDuration(1200) // Adjust duration as needed
                .start()

            animation.duration = 1200 // Set the duration of the animation (in milliseconds)
            animation.fillAfter =
                true // Ensure that the view stays in its final position after the animation
            constraintLayout.startAnimation(animation)

            // Update the visibility of view1 after the animation completes
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    // checkbox.visibility = View.VISIBLE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        } else {
            val animation: Animation =
                TranslateAnimation(linearLayout.width.toFloat(), 0f, 0f, 0f)


            checkbox.animate()
                .alpha(0f)
                .setDuration(1200) // Adjust duration as needed
                .start()

            animation.duration = 1200 // Set the duration of the animation (in milliseconds)
            animation.fillAfter =
                true // Ensure that the view stays in its final position after the animation
            constraintLayout.startAnimation(animation)

            // Update the visibility of view1 after the animation completes
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    // checkbox.visibility = View.GONE
                    closeCheckBoxes = false
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }


    fun toggleAllCheckboxes(checkAll: Boolean) {
        selectAllButtonChecked = checkAll
        if (!checkAll) resetItemSelectPosition()
        notifyDataSetChanged()
    }

    fun hideCheckBoxes() {
        showCheckboxes = false
        selectAllButtonChecked = false
        closeCheckBoxes = true
        resetItemSelectPosition()
        notifyDataSetChanged()
    }

    fun getJournal(position: Int) = journals[position]

    companion object {
        var showCheckboxes = false
        var selectAllButtonChecked = false
        var itemSelectedCount = 0
        var itemSelectedPosition = mutableListOf<Int>()

        fun resetItemSelectPosition() {
            itemSelectedPosition = mutableListOf<Int>()
        }

        fun getSelectedItemList(): MutableList<Int> {
            return itemSelectedPosition
        }

        fun reset() {
            showCheckboxes = false
            selectAllButtonChecked = false
            itemSelectedCount = 0
            resetItemSelectPosition()
        }
    }
}