package ru.example.cards.ui.card.card.activity.presentation.view.impl

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_card.*
import ru.example.cards.R
import ru.example.cards.constants.Constants
import ru.example.cards.ui.card.card.activity.presentation.presenter.CardPresenter
import ru.example.cards.ui.card.card.activity.presentation.presenter.factory.CardPresenterFactory
import ru.example.cards.ui.card.card.activity.presentation.view.CardView
import ru.example.cards.ui.card.card.fragments.presentation.adapter.SectionsPagerAdapter
import ru.example.cards.ui.card.card.fragments.presentation.additionally.view.impl.CardAdditionallyFragmentImpl.OnTakeCardPhotoClickListener
import ru.example.cards.ui.card.cards.animated.AnimatedCardsActivity
import ru.example.cards.ui.card.cards.list.presentation.view.impl.CardsListActivity
import ru.example.cards.ui.widget.CardWidgetProvider

class CardActivity : AppCompatActivity(),
    CardView,
    OnTakeCardPhotoClickListener {

    private lateinit var presenter: CardPresenter
    private lateinit var pagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        presenter = CardPresenterFactory.createCardPresenter(this)
        presenter.onAttachView(this)

        initToolbar()

        pagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, intent)

        pager.adapter = pagerAdapter
        tabs.setupWithViewPager(pager)
    }

    private fun initToolbar() {
        setSupportActionBar(card_toolbar as Toolbar?)
        supportActionBar?.title = getString(R.string.card_toolbar_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_card_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_edit -> {
                // запускам активити редактирования карты
                presenter.onEditCardButtonClick()
                return true
            }

            R.id.action_delete -> {
                // удаляем карту
                presenter.onDeleteCardButtonClick()
                return true
            }

            android.R.id.home -> {
                onBackPressed()
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onTakeCardPhotoClick(args: Bundle) {
        val intent = Intent(this, CardPhotoActivity::class.java)
        // помещаем в интент название стороны карты для которой вызывается фотографирование
        intent.putExtra(Constants.CARD_SIDE_EXTRA, args)
        startActivityForResult(intent, Constants.REQUEST_TAKE_PICTURE)
    }

    override fun startEditCardActivity() {
        val intent = Intent(this, EditCardActivity::class.java).apply {
            putExtra(Constants.OPEN_CARD_EXTRA, getCardId())
        }
        startActivityForResult(intent, Constants.REQUEST_EDIT_CARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                when (requestCode) {
                    Constants.REQUEST_EDIT_CARD -> {
                        pagerAdapter.updateBarcode(data.getStringExtra(Constants.BARCODE_EXTRA))
                    }

                    Constants.REQUEST_TAKE_PICTURE -> {
                        pagerAdapter.updateCardPhoto(data)
                    }
                }
            }
        }
    }

    override fun startAnimatedCardsListActivity() {
        val intent = Intent(this, AnimatedCardsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    override fun startCardsListActivity() {
        val intent = Intent(this, CardsListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    override fun getIntent(): Intent = intent

    override fun updateWidget() {
        val intent = Intent(this, CardWidgetProvider::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        }
        sendBroadcast(intent)
    }

    override fun close() = finish()

    override fun getCardId() = intent.extras?.get(Constants.OPEN_CARD_EXTRA) as Long?

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetachView()
    }
}