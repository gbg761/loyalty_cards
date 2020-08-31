package ru.example.cards.ui.card.cards.animated

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.animated_row_1.*
import kotlinx.android.synthetic.main.animated_row_1.view.*
import kotlinx.android.synthetic.main.animated_row_2.*
import kotlinx.android.synthetic.main.animated_row_2.view.*
import kotlinx.android.synthetic.main.animated_row_3.*
import kotlinx.android.synthetic.main.animated_row_3.view.*
import kotlinx.android.synthetic.main.card.view.*
import kotlinx.android.synthetic.main.layout_animated_cards.*
import ru.example.cards.R
import ru.example.cards.constants.Constants
import ru.example.cards.utils.dpToPx

class AnimatedCardsActivity() : AppCompatActivity() {

    private val DEFAULT_ANIMATION_DURATION = 4000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animated_cards)

        initUI()

        // ширина animated_row_x, где x - номер строки на экране
        // animated_row содержит 3 карточки шириной 150dp + расстояния между карточками (2 * 10dp)
        // т.о. задавая фиксированную ширину карточкам мы добиваемся того, что они выходят за границы экрана
        // как того и требует UI
        val layoutWidth = 475 // in dp

        // x1 и x2 задают положения по x, между которыми будет происходить анимация
        // для 1ой и 3ей строк x1 задает начальное положение, x2 задает конечное положение
        // для 2ой строки наоборот
        // вычисляем насколько нужно сместить строчку, чтобы 1ая и 3ая карта могли помещаться на экране
        // при перемещнии строки
        val x1 = (layoutWidth.dpToPx(resources.displayMetrics) - getScreenWidth()).toFloat()
        val x2 = 5.dpToPx(resources.displayMetrics).toFloat() // задает отступ от края экрана
        startAnimation(x1, x2)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.title = getString(R.string.cards)
    }

    private fun initUI() {

        initToolbar()

        addNewCardButton.setOnClickListener {
            // запускаем создание карты с нуля
            startActivity(Intent(this, AddCardActivity::class.java))
        }

        val cardsArray = resources.getStringArray(R.array.stub_card)
        initCard(card_1, 1, cardsArray[0])
        initCard(card_2, 2, cardsArray[1])
        initCard(card_3, 3, cardsArray[2])
        initCard(card_4, 4, cardsArray[3])
        initCard(card_5, 5, cardsArray[4])
        initCard(card_6, 6, cardsArray[5])
        initCard(card_7, 7, cardsArray[6])
        initCard(card_8, 8, cardsArray[7])
        initCard(card_9, 9, cardsArray[8])

    }

    private fun initCard(view: View, position: Int, shop: String) {

        val cardImageName = "card_$position"
        view.imageCard.setImageResource(
            resources.getIdentifier(
                cardImageName,
                "drawable",
                packageName
            )
        )
        view.textCard.text = shop
    }

    fun onCardClick(view: View) {

        val shopName = view.textCard.text.toString()

        val args = Bundle().apply {
            putString(Constants.SHOP_NAME_EXTRA, shopName)
            putString(Constants.CARD_IMAGE_NAME_EXTRA, getImageName(shopName))
        }

        // запускаем экран сканирования штрих-кода карты
        val intent = Intent(this, BarcodeScannerActivity::class.java).apply {
            putExtras(args)
        }
        startActivity(intent)
    }

    private fun startAnimation(x1: Float, x2: Float) {

        /////////////////////////////////////////////////////////////////////
        // анимация 1ой и 3ей строчек
        val firstAndThirdRowAnimator = ValueAnimator.ofFloat(-x1, x2)
        firstAndThirdRowAnimator.addUpdateListener {

            val value = it.animatedValue as Float
            animateFirstRow(value)
            animateThirdRow(value)
        }

        firstAndThirdRowAnimator.repeatCount = ValueAnimator.INFINITE
        firstAndThirdRowAnimator.repeatMode = ValueAnimator.REVERSE
        /////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////
        // анимация 2 ой строчки
        val secondRowAnimator = ValueAnimator.ofFloat(x2, -x1)
        secondRowAnimator.addUpdateListener {

            val value = it.animatedValue as Float
            animateSecondRow(value)
        }

        secondRowAnimator.repeatCount = ValueAnimator.INFINITE
        secondRowAnimator.repeatMode = ValueAnimator.REVERSE
        /////////////////////////////////////////////////////////////////////

        val animatorSet = AnimatorSet()
        animatorSet.play(firstAndThirdRowAnimator).with(secondRowAnimator)
        animatorSet.duration = DEFAULT_ANIMATION_DURATION
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        animatorSet.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_animated_cards_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // обрабатываем нажатие кнопок меню в toolbar
        when (item.itemId) {

            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    // анимация перемещения карт первой строки
    private fun animateFirstRow(value: Float) {
        first_row.card_1.translationX = value
        first_row.card_2.translationX = value
        first_row.card_3.translationX = value
    }

    // анимация перемещения карт второй строки
    private fun animateSecondRow(value: Float) {
        second_row.card_4.translationX = value
        second_row.card_5.translationX = value
        second_row.card_6.translationX = value
    }

    // анимация перемещения карт третьей строки
    private fun animateThirdRow(value: Float) {
        third_row.card_7.translationX = value
        third_row.card_8.translationX = value
        third_row.card_9.translationX = value
    }

    private fun getScreenWidth(): Int {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    private fun getImageName(shopName: String) =
        when (shopName) {
            getString(R.string.colins) -> getString(R.string.colins_image)
            getString(R.string.calipso) -> getString(R.string.calipso_image)
            getString(R.string.beefree) -> getString(R.string.beefree_image)
            getString(R.string.mvideo) -> getString(R.string.mvideo_image)
            getString(R.string.acoola) -> getString(R.string.acoola_image)
            getString(R.string.carpisa) -> getString(R.string.carpisa_image)
            getString(R.string.cats_dogs) -> getString(R.string.cats_dogs_image)
            getString(R.string.belwest) -> getString(R.string.belwest_image)
            else -> getString(R.string.bystronom_image)
        }
}




















