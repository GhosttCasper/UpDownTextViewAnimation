package com.example.textviewanimation

import android.R.attr.*
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat


class MainActivity : AppCompatActivity() {
    private val DEBUG_TAG: String? = "ACTION"
    private lateinit var container: FrameLayout
    private lateinit var helloText: TextView

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action: Int = event.action

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                /*Log.d(DEBUG_TAG, "Action was DOWN")
                Log.d(DEBUG_TAG, "Action was DOWN}")
                Toast.makeText(
                    applicationContext, "Action was DOWN", Toast.LENGTH_SHORT
                ).show()*/
                true
            }
            MotionEvent.ACTION_MOVE -> {
                /*Log.d(DEBUG_TAG, "Action was MOVE")
                Toast.makeText(
                    applicationContext, "Action was MOVE", Toast.LENGTH_SHORT
                ).show()*/
                true
            }
            MotionEvent.ACTION_UP -> {
                val x = event.x
                val y = event.y
                Log.d(DEBUG_TAG, "Action was UP x=$x y=$y")
                /*Toast.makeText(
                    applicationContext, "Action was UP x=$x y=$y", Toast.LENGTH_SHORT
                ).show()*/
                helloText.x = x
                helloText.y = y
                TextViewCompat.setTextAppearance(
                    helloText,
                    R.style.Widget_TextViewAnimation_TextView
                )
                val yRawYDelta = event.rawY - y

                val screenHeight: Int = this.resources.displayMetrics.heightPixels
                val parentHeight = screenHeight - yRawYDelta
                Log.d(DEBUG_TAG, "Height=$parentHeight")
                val yDelta: Float = (parentHeight - y) / parentHeight
                Log.d(DEBUG_TAG, "yDelta=$yDelta")

                val moveDownThenUp = TranslateAnimation(
                    Animation.ABSOLUTE,
                    0f,
                    Animation.ABSOLUTE,
                    0f,
                    Animation.ABSOLUTE,
                    0f,
                    Animation.RELATIVE_TO_PARENT,
                    yDelta
                )
                with(moveDownThenUp) {
                    duration = 2000
                    repeatCount = Animation.INFINITE
                    repeatMode = Animation.REVERSE
                }
                helloText.startAnimation(moveDownThenUp)


                /*val fallingAnimation: Animation = AnimationUtils.loadAnimation(
                    this,
                    R.anim.move_down
                )
                fallingAnimation.fillBefore = true
                helloText.startAnimation(fallingAnimation)*/

                true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(DEBUG_TAG, "Action was CANCEL")
                Toast.makeText(
                    applicationContext, "Action was CANCEL", Toast.LENGTH_SHORT
                ).show()
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.d(DEBUG_TAG, "Movement occurred outside bounds of current screen element")
                Toast.makeText(
                    applicationContext,
                    "Movement occurred outside bounds of current screen element",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.container)
        helloText = findViewById(R.id.helloText)

        container.setOnTouchListener { v, event ->
            onTouchEvent(event)
            // ... Respond to touch events
            true
        }

        //button = findViewById<View>(R.id.button2) as Button

        //val button: TextView = findViewById<View>(R.id.helloText) as TextView
        /*button.setOnTouchListener({ v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                Toast.makeText(
                    applicationContext,
                    "Молодой человек, не прикасайтесь ко мне!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            false
        })*/

        //val helloText: TextView = findViewById(R.id.helloText)
        //helloText.setOnTouchListener(touchListener);
    }

    private var xDelta = 0
    private var yDelta = 0

    private val touchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            val x = event.rawX.toInt()
            val y = event.rawY.toInt()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = view.layoutParams as FrameLayout.LayoutParams
                    xDelta = x - lParams.leftMargin
                    yDelta = y - lParams.topMargin
                }
                MotionEvent.ACTION_UP -> {
                    Toast.makeText(applicationContext, "Объект перемещён", Toast.LENGTH_SHORT)
                        .show()
                }
                MotionEvent.ACTION_MOVE -> {
                    if (x - xDelta + view.getWidth() <= container!!.width && y - yDelta + view.getHeight() <= container!!.height && x - xDelta >= 0 && y - yDelta >= 0) {
                        val layoutParams = view.getLayoutParams() as FrameLayout.LayoutParams
                        layoutParams.leftMargin = x - xDelta
                        layoutParams.topMargin = y - yDelta
                        layoutParams.rightMargin = 0
                        layoutParams.bottomMargin = 0
                        view.setLayoutParams(layoutParams)
                    }
                }
            }
            container!!.invalidate()
            return true
        }
    }

    fun onDisplayClick(view: View) {

    }
}