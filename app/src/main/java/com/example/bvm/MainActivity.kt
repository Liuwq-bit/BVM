package com.example.bvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bvm.ui.book.BookListFragment
import com.example.bvm.ui.music.MusicListFragment
import com.example.bvm.ui.video.VideoListFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import android.util.DisplayMetrics

import androidx.customview.widget.ViewDragHelper

import androidx.drawerlayout.widget.DrawerLayout

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.Exception
import java.lang.reflect.Field


class MainActivity : AppCompatActivity() {

    val titleList = listOf(BookListFragment.type, VideoListFragment.type, MusicListFragment.type)   // 各个页面类型的标题
    val fragmentList = ArrayList<Fragment>()    // 存放各个页面的Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        // 设置侧滑菜单点击事件
        navView.setCheckedItem(R.id.navCall)
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()
            true
        }

        fragmentList.add(BookListFragment())    // 添加到viewPaper
        fragmentList.add(VideoListFragment())
        fragmentList.add(MusicListFragment())

        modelViewPager.adapter = MyAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(modelTabLayout, modelViewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = titleList[position]  // 设置文字内容
            when (position) {   // 设置图标
                0 -> tab.icon = getDrawable(R.drawable.ic_carwashing)
                1 -> tab.icon = getDrawable(R.drawable.ic_clear_day)
                2 -> tab.icon = getDrawable(R.drawable.ic_clear_night)
            }
        }.attach()

        // 设置TabLayout点击事件
        modelTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                modelViewPager.currentItem = tab?.position!!
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        setDrawerLeftEdgeSize(this, drawerLayout, 0.1f)
    }

    /**
     * viewPaper滑动事件监听
     */
    inner class MyAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount() = titleList.size

        override fun createFragment(position: Int) = fragmentList[position]


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }

    /**
     *  设置DrawerLayout侧滑范围
      */
    fun setDrawerLeftEdgeSize(
        activity: Activity?,
        drawerLayout: DrawerLayout?,
        displayWidthPercentage: Float) {
        if (activity == null || drawerLayout == null) return
        try {
            val leftDraggerField: Field = drawerLayout.javaClass.getDeclaredField("mLeftDragger")
            leftDraggerField.isAccessible = true
            val leftDragger = leftDraggerField.get(drawerLayout) as ViewDragHelper
            val edgeSizeField: Field = leftDragger.javaClass.getDeclaredField("mEdgeSize")
            edgeSizeField.isAccessible = true
            val edgeSize: Int = edgeSizeField.getInt(leftDragger)
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            edgeSizeField.setInt(
                leftDragger,
                edgeSize.coerceAtLeast((dm.widthPixels * displayWidthPercentage).toInt())
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}