package ru.example.cards.ui.card.add.presentation.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import ru.example.cards.utils.dpToPx

class AddCardDividerItemDecoration(private val context: Context, private var divider: Drawable) :
    RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // отступы от родителя
        val marginLeft = 90
        val marginRight = 25

        // координаты рисования разделителя элементов в списке
        val dividerLeft = marginLeft.dpToPx(context.resources.displayMetrics)
        val dividerRight = parent.width - marginRight.dpToPx(context.resources.displayMetrics)

        for (i in 0..parent.childCount) {

            if (i != parent.childCount - 1) {

                val child = parent.getChildAt(i)
                if (child != null) {

                    if (child !is LinearLayout) {

                        val params = child.layoutParams as RecyclerView.LayoutParams

                        // координаты рисования разделителя элементов в списке
                        val dividerTop = child.bottom + params.bottomMargin
                        val dividerBottom = dividerTop + divider.intrinsicHeight

                        divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                        divider.draw(c)
                    }
                }
            }
        }
    }
}