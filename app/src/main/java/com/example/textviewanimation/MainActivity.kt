package com.example.textviewanimation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.TextViewCompat


class MainActivity : AppCompatActivity() {
    private lateinit var container: FrameLayout
    private lateinit var helloText: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container = findViewById(R.id.container)
        helloText = findViewById(R.id.helloText)

        container.setOnTouchListener { _, event ->
            onTouchEvent(event)
            true
        }
        helloText.setOnClickListener {
            helloText.clearAnimation()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_UP -> {
                val x = event.x
                val y = event.y

                helloText.x = x
                helloText.y = y

                TextViewCompat.setTextAppearance(
                    helloText, R.style.Widget_TextViewAnimation_TextView
                )

                val moveUp = TranslateAnimation(
                    Animation.ABSOLUTE,
                    0f,
                    Animation.ABSOLUTE,
                    0f,
                    Animation.ABSOLUTE,
                    0f,
                    Animation.ABSOLUTE,
                    -y
                )
                val moveDown = TranslateAnimation(
                    Animation.ABSOLUTE,
                    0f,
                    Animation.ABSOLUTE,
                    0f,
                    Animation.ABSOLUTE,
                    0f,
                    Animation.RELATIVE_TO_PARENT,
                    0.97f
                )
                with(moveDown) {
                    repeatCount = Animation.INFINITE
                    repeatMode = Animation.REVERSE
                }

                AnimationSet(true).apply {
                    fillAfter = true
                    interpolator = AccelerateInterpolator()
                    duration = 3000
                    //startOffset=5000 // unexpected behavior
                    addAnimation(moveDown)
                    addAnimation(moveUp)
                    helloText.startAnimation(this)
                }

                true
            }
            else -> super.onTouchEvent(event)
        }
    }


}