package ru.example.cards.ui.card.cards.list.presentation.view.impl

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.layout_cards_list.*
import ru.example.cards.R
import ru.example.cards.constants.Constants
import ru.example.cards.model.cards.models.Card
import ru.example.cards.ui.base.view.MenuActivity
import ru.example.cards.ui.card.cards.list.presentation.adapter.CardsListAdapter
import ru.example.cards.ui.card.cards.list.presentation.decoration.GridSpacingItemDecoration
import ru.example.cards.ui.card.cards.list.presentation.presenter.CardsListPresenter
import ru.example.cards.ui.card.cards.list.presentation.presenter.factory.CardsListPresenterFactory
import ru.example.cards.ui.card.cards.list.presentation.view.CardsListView
import ru.example.cards.ui.card.cards.list.presentation.view.OnCardClickListener

class CardsListActivity() : MenuActivity(),
    CardsListView,
    OnCardClickListener {

    private lateinit var presenter: CardsListPresenter

    private lateinit var adapter: CardsListAdapter

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards_list)

        presenter = CardsListPresenterFactory.createPresenter(this)
        // присоединяем активити к презентеру
        presenter.onAttachView(this)
        initUI()
    }

    private fun initUI() {

        initToolbar()
        fillRecyclerView()

        addNewCardButton.setOnClickListener {
            // открыть экран со встроенным списком карт(экран добавления карты)
            presenter.open(Constants.CREATE_NEW_CARD, this, AddCardActivity::class.java)
        }

        presenter.viewIsReady()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.title = getString(R.string.cards)
    }

    private fun fillRecyclerView() {

        adapter = CardsListAdapter(context = this, onCardClickListener = this)

        val spanCount = 2
        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.adapter = adapter

        // отступы для списка карт
        val spacingInDp = 10

        recyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount,
                Math.round(spacingInDp * resources.displayMetrics.density),
                true
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.activity_cards_list_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView

        // прослушиваем изменения поисковой строки
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            // скрываем иконнки в тулбаре во время поиска
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

            R.id.action_settings -> {
                presenter.open(Constants.OPEN_SETTINGS, this, SettingsActivity::class.java)
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun showCards(cards: List<Card>) = adapter.refresh(cards)

    override fun onClick(id: Long) {
        presenter.open(Constants.OPEN_CARD, this, CardActivity::class.java, id)
    }

    override fun next(intent: Intent) {
        startActivity(intent)
    }

    override fun close() = finish()

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
    }
}