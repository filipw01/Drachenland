package com.wyimaginowanakrotka.drachenland

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_fullscreen.*
import java.lang.Integer.parseInt

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        text.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var pageNum =1
       /* if (intent.extras != null) {
            pageNum = parseInt(intent.getStringExtra("page"))
        }*/
        mVisible = true
        restart.setOnClickListener{
            val intent = Intent(this, FullscreenActivity::class.java)
            finish()
            startActivity(intent)
        }
        btn.setOnClickListener {
            when (pageNum) {
                1 -> {
                    val nameVal = name.text
                    name.visibility = View.GONE
                    ageInput.visibility = View.VISIBLE
                    text.text = getString(R.string.text1, nameVal)
                }
                2 -> {
                    TextUtils.isDigitsOnly(ageInput.text)
                    ageInput.visibility = View.GONE
                    try {
                        when {
                            Integer.parseInt(ageInput.text.toString()) > 60 -> {
                                text.text = "Byłeś za stary na przygodę po kilku dniach " +
                                        "spędzonych w jaskini bez jedzenia i picia padłeś z " +
                                        "wycieńczenia i nigdy nie wstałeś"
                                btn.visibility = View.GONE
                                restart.visibility = View.VISIBLE
                            }
                            Integer.parseInt(ageInput.text.toString()) < 14 -> {
                                text.text = "Twój młody organizm nie wytrzymał przeciążenia " +
                                        "organizmu i po ostatniego oddechu padłeś na kamienne " +
                                        "dno jaskini"
                                btn.visibility = View.GONE
                                restart.visibility = View.VISIBLE

                            }
                            Integer.parseInt(ageInput.text.toString()) in 14..60 -> {
                                text.text = getString(R.string.text2)
                            }
                        }
                    }catch (e:Exception){
                        text.text = "Zmień wartość domyślną cymbale. Jesteś dumny z tego, że " +
                                "popsułeś naszą grę?. Teraz baw się dobrze restartując"
                        btn.visibility = View.GONE
                        restart.visibility = View.VISIBLE
                    }
                }
                else -> text.text = getString(R.string.test)
            }
            pageNum += 1
        }
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        text.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }
}
