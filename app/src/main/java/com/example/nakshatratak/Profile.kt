package com.example.nakshatratak

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.RenderEffect.createShaderEffect
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.CollapsingToolbarLayout

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        window.statusBarColor = ContextCompat.getColor(this, R.color.buttonColorOrenge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);

        val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.collapsingToolbar)
        collapsingToolbar.title = "Aditya Dhar Dwivedi"

        collapsingToolbar.setCollapsedTitleTypeface(Typeface.DEFAULT_BOLD);
      //  collapsingToolbar.setCollapsedTitleTypeface(Typeface.DEFAULT_BOLD);

        val image:ImageView = findViewById(R.id.profile)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            image.setRenderEffect(
                RenderEffect.createBlurEffect(
                    95.0f, 95.0f, Shader.TileMode.MIRROR
                )
            )
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()  // Or handle your custom logic here
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}