package com.example.nakshatratak

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var viewPager: ViewPager
        lateinit var dot1: DotsIndicator


        lateinit var viewPagerAdapter: ViewPagerAdapter

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



       val button: FloatingActionButton = findViewById(R.id.floatingActionButton)

        button.setOnClickListener {

            startActivity(Intent(this,LoginActivity::class.java))
finish()
        }

        viewPager = findViewById(R.id.view_pager)


        dot1 = findViewById(R.id.dot1)


        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        dot1.setViewPager(viewPager)


//        viewPager = findViewById(R.id.idViewPager)
//
//        // on below line we are initializing
//        // our image list and adding data to it.
//        imageList = ArrayList<objectslider>()
//        imageList.add(objectslider(R.drawable.icon002, "","Discription"))
//        imageList.add(objectslider(R.drawable.maching01, "Book a Pooja","Discription"))
//        imageList.add(objectslider(R.drawable.icon003, "Free Daily Horoscope","Discription"))
//        imageList.add(objectslider(R.drawable.icon004, "Free Kundali Matching","Discription"))
//
//        // on below line we are initializing our view
//        // pager adapter and adding image list to it.
//        viewPagerAdapter = ViewPagerAdapter(this@MainActivity, imageList)
//
//        // on below line we are setting
//        // adapter to our view pager.
//        viewPager.adapter = viewPagerAdapter

    }
}