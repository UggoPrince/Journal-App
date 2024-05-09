package com.journal.util

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class CheckboxAppearAnimator(
    private val checkboxViewId: Int, private val constraintLayoutId: Int
) : DefaultItemAnimator() {
    override fun animateMove(
        holder: RecyclerView.ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        val deltaX = toX - fromX
        if (deltaX != 0) {
            val translationAnimator =
                ObjectAnimator.ofFloat(holder.itemView.findViewById(checkboxViewId), "translationX", 0f, deltaX.toFloat())
            translationAnimator.duration = 1000
            translationAnimator.interpolator = AccelerateDecelerateInterpolator()
            translationAnimator.start()
        }
        return true
    }

//    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
//        return super.animateAdd(holder)
//    }
    fun animateAdd(holder: RecyclerViewHolder): Boolean {
        val view = holder.itemView.findViewById<View>(checkboxViewId)
        view.alpha = 0f
        // view.visibility = View.VISIBLE
        val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        animator.duration = 1000
        animator.start()
        return true
    }
}
