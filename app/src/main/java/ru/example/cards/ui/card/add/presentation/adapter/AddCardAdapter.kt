package ru.example.cards.ui.card.add.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import ru.example.cards.R
import ru.example.cards.constants.Constants
import ru.example.cards.model.cards.models.InputCardWithType
import ru.example.cards.ui.card.add.presentation.view.OnCardClickListener
import ru.example.cards.utils.countWords
import java.util.*

class AddCardAdapter(
    private val context: Context,
    private val onCardClickListener: OnCardClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private val TAG = "AddCardAdapter"

    private var inputCardList: List<InputCardWithType> = arrayListOf()
    private var inputCardListFiltered: List<InputCardWithType>

    init {
        inputCardListFiltered = inputCardList
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val listItem = inputCardListFiltered[position]

        when (holder) {

            is GroupViewHolder -> {
                holder.groupTitleTextView.text = listItem.itemName.toUpperCase()
            }

            is CardViewHolder -> {

                holder.shopNameTextView.text = listItem.itemName

                Log.d(TAG, "itemName = ${listItem.itemName}")
                Log.d(TAG, "color = ${listItem.color}")
                val drawable = TextDrawable.builder()
                    .beginConfig()
                    .bold()
                    .endConfig()
                    .buildRound(
                        getFirstLettersFromShopName(listItem.itemName),
                        Color.parseColor(listItem.color)
                    )
                holder.cardImageView.setImageDrawable(drawable)

                // передаем в AddCardActivityKt название магазина
                holder.view.setOnClickListener {
                    onCardClickListener.onCardClick(listItem.itemName, listItem.cardName)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(context)

        return when (viewType) {

            Constants.VIEWTYPE_GROUP -> {
                val viewGroup = inflater.inflate(R.layout.group_layout, parent, false) as ViewGroup
                GroupViewHolder(viewGroup)
            }

            else -> {
                val viewGroup = inflater.inflate(R.layout.card_layout, parent, false) as ViewGroup
                CardViewHolder(viewGroup)
            }
        }
    }

    override fun getItemViewType(position: Int) = inputCardListFiltered[position].viewType

    override fun getItemCount() = inputCardListFiltered.size

    inner class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val groupTitleTextView = itemView.findViewById(R.id.groupTitleTextView) as TextView
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val view = itemView
        val shopNameTextView = itemView.findViewById(R.id.shopNameTextView) as TextView
        val cardImageView = itemView.findViewById(R.id.cardRoundImageView) as ImageView
    }

    override fun getFilter(): Filter {

        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val searchQuery = constraint.toString()
                if (searchQuery.isEmpty()) {
                    inputCardListFiltered = inputCardList
                } else {
                    val searchList = arrayListOf<InputCardWithType>()
                    for (row in inputCardList) {

                        if ((row.itemName.toLowerCase()).contains(searchQuery.toLowerCase()))
                            searchList.add(row)
                    }
                    inputCardListFiltered = searchList
                }

                val filterResults = FilterResults()
                filterResults.values = inputCardListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                @Suppress("UNCHECKED_CAST")
                inputCardListFiltered = results?.values as ArrayList<InputCardWithType>
                notifyDataSetChanged()
            }
        }
    }

    fun refresh(cards: List<InputCardWithType>) {

        inputCardList = cards
        inputCardListFiltered = inputCardList
        notifyDataSetChanged()
    }

    // возвращает строку для помещения в аватар карты
    // если название магазина состоит из 2х и более слов,
    // берем первые буквы первых двух слов,
    // если название магазина состоит из 1 слова,
    // возаращем 1ую букву названия
    private fun getFirstLettersFromShopName(shopName: String): String {

        // создает строку из букв, содержащихся в названии магазина
        // для отображения в "аватаре" карты
        val strBuilder = StringBuilder(2)

        // определяем количество слов в названии магазина
        val cnt = shopName.countWords()

        strBuilder.append(shopName[0])

        // Если слов в названии магазина > 1, то берем первые буквы
        // первых 2х слов
        // иначе берем первые 2 буквы названия
        var i = 1

        if (cnt > 1) {

            while (shopName[i] != ' ')
                i++

            strBuilder.append(shopName[i + 1])
        } else {

            // пока не наткнемся на цифру или букву
            while (!(shopName[i].isLetterOrDigit()))
                i++

            strBuilder.append(shopName[i])
        }

        return strBuilder.toString()
    }

}