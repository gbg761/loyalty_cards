package ru.example.cards.ui.card.add.presentation.view.impl

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import kotlinx.android.synthetic.main.layout_add_card.*
import ru.example.cards.R
import ru.example.cards.constants.Constants
import ru.example.cards.model.cards.models.InputCardWithType
import ru.example.cards.ui.barcode.BarcodeScannerActivityJava
import ru.example.cards.ui.base.view.MenuActivity
import ru.example.cards.ui.card.add.presentation.adapter.AddCardAdapter
import ru.example.cards.ui.card.add.presentation.decoration.AddCardDividerItemDecoration
import ru.example.cards.ui.card.add.presentation.presenter.AddCardPresenter
import ru.example.cards.ui.card.add.presentation.presenter.factory.AddCardPresenterFactory
import ru.example.cards.ui.card.add.presentation.view.AddCardView
import ru.example.cards.ui.card.add.presentation.view.OnCardClickListener

class AddCardActivity() : MenuActivity(),
    AddCardView,
    OnCardClickListener {

    private lateinit var presenter: AddCardPresenter

    private lateinit var cardAdapter: AddCardAdapter

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        presenter = AddCardPresenterFactory.createPresenter(this)
        // присоединяем активити к презентеру
        presenter.onAttachView(this)

        initUI()
    }

    private fun initUI() {

        initToolbar()
        initRecyclerView()

        addNewCardButton.setOnClickListener {
            presenter.onAddNewCardButtonClick()
        }

        presenter.viewIsReady()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.title = getString(R.string.add_card)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {

        cardAdapter = AddCardAdapter(context = this, onCardClickListener = this)

        cardRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cardAdapter
            addItemDecoration(
                AddCardDividerItemDecoration(
                    context,
                    resources.getDrawable(R.drawable.divider)
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.activity_add_card_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.find_card)

        // прослушиваем изменения поисковой строки
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                cardAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                cardAdapter.filter.filter(query)
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            // скрываем иконки в тулбаре во время поиска
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                setItemsVisibility(menu, searchItem, false)
                return true
            }

            // показываем иконки, когда поиск закрыт
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                setItemsVisibility(menu, searchItem, true)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // обрабатываем нажатие кнопок меню в toolbar
        when (item.itemId) {

            android.R.id.home -> {
                onBackPressed()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun showCards(cards: List<InputCardWithType>) {
        cardAdapter.refresh(cards)
        fastScrollerView.setupWithRecyclerView(
            cardRecyclerView,
            { position ->
                cards[position]
                    .takeIf { it.itemName.length == 1 }
                    ?.let { item ->
                        FastScrollItemIndicator.Text(item.itemName.toUpperCase())
                    }
            },
            showIndicator = { _, indicatorPosition, _ ->
                // Hide every other indicator
                indicatorPosition % 3 == 0
            }
        )
    }

    override fun startBarcodeScannerActivity() = startActivity(createIntent())

    override fun startBarcodeScannerActivity(shopName: String, imageName: String) {
        val intent = createIntent()
        val args = Bundle().apply {
            putString(Constants.SHOP_NAME_EXTRA, shopName)
            putString(Constants.CARD_IMAGE_NAME_EXTRA, imageName)
        }

        intent.putExtras(args)
        startActivity(intent)
    }

    // реагируем на выбор карты из вшитого списка карт
    override fun onCardClick(shopName: String, imageName: String) =
        presenter.onCardClick(shopName, imageName)

    override fun close() = finish()

    private fun createIntent(): Intent = Intent(this, BarcodeScannerActivityJava::class.java)

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
    }
}
