package ru.example.cards.ui.card.cards.list.presentation.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.example.cards.R
import ru.example.cards.model.cards.models.Card
import ru.example.cards.ui.card.cards.list.presentation.view.OnCardClickListener
import kotlin.collections.ArrayList

class CardsListAdapter(
    private val onCardClickListener: OnCardClickListener,
    private val context: Context

) : RecyclerView.Adapter<CardsListAdapter.ViewHolder>(), Filterable {

    private var cardsList: List<Card> = arrayListOf()
    private var cardsListFiltered: List<Card>

    init {
        cardsListFiltered = cardsList
    }

    inner class ViewHolder(val cardView: View) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.cardView
        onBind(cardView, position)
    }

    private fun onBind(cardView: View, position: Int) {

        val cardImageView: ImageView = cardView.findViewById(R.id.cardImageView)

        val imageName: String? = cardsListFiltered[position].imageName
        // выводим изображение карты в ImageView если оно имеется

        if (imageName != null) {
            val drawableId = context.resources.getIdentifier(
                imageName,
                "drawable",
                context.packageName
            )
            cardImageView.setImageResource(drawableId)

        } else {

            val bitmapSizePoint = getBitmapSize()
            val bitmap =
                Bitmap.createBitmap(bitmapSizePoint.x, bitmapSizePoint.y, Bitmap.Config.RGB_565)

            bitmap.eraseColor(cardsListFiltered[position].imageColor!!)

            cardImageView.setImageBitmap(bitmap)

            val shopNameTextView: TextView = cardView.findViewById(R.id.shopNameTextView)
            shopNameTextView.visibility = View.VISIBLE
            shopNameTextView.text = cardsListFiltered[position].shopName
        }

        cardImageView.setOnClickListener {
            onCardClickListener.onClick(cardsListFiltered[position].id)
        }
    }

    override fun getItemCount(): Int = cardsListFiltered.size

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val searchQuery = constraint.toString()
                cardsListFiltered = if (searchQuery.isEmpty()) {
                    cardsList
                } else {
                    val searchList = arrayListOf<Card>()
                    for (row in cardsList) {

                        if ((row.shopName.toLowerCase()).contains(searchQuery.toLowerCase()))
                            searchList.add(row)
                    }
                    searchList
                }

                val filterResults = FilterResults()
                filterResults.values = cardsListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                cardsListFiltered = results?.values as ArrayList<Card>
                notifyDataSetChanged()
            }
        }
    }

    fun refresh(cards: List<Card>) {

        cardsList = cards
        cardsListFiltered = cardsList
        notifyDataSetChanged()
    }

    //
    private fun getBitmapSize(): Point {

        val drawableId = context.resources.getIdentifier(
            "abk",
            "drawable",
            context.packageName
        )
        val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)
        val point = Point(bitmap.width, bitmap.height)
        bitmap.recycle()
        return point
    }
}