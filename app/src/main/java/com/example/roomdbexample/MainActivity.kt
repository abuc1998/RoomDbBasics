package com.example.roomdbexample

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        val adapter = TabPagerAdapter(supportFragmentManager)
        adapter.addFragment(AddDataFragment(), "Add Data")
        adapter.addFragment(FetchDataFragment(), "Fetch Data")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    }
