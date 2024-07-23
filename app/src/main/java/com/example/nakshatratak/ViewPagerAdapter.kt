package com.example.nakshatratak

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class ViewPagerAdapter(private val context: Context?) : PagerAdapter() {

    private val images = arrayOf(R.drawable.icon002, R.drawable.maching01, R.drawable.icon003, R.drawable.icon003)
    private val text = arrayOf("Talk/Chat with our Astrologers", "Free Kundali/Shaadi Matching", "Free Daily Horoscope", "Book a Pooja")
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(@NonNull view: View, @NonNull obj: Any): Boolean {
        return view == obj
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.viewpager, container, false)
        val imageView: ImageView = view.findViewById(R.id.idIVImage)
        imageView.setImageResource(images[position])
        val textView: TextView = view.findViewById(R.id.heading)
        textView.text= text[position]
        val viewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull obj: Any) {
        val viewPager = container as ViewPager
        val view = obj as View
        viewPager.removeView(view)
    }
}
