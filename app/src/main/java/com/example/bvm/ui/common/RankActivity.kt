package com.example.bvm.ui.common

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.bvm.R
import com.example.bvm.ui.book.BookRankListFragment
import com.example.bvm.ui.music.MusicRankListFragment
import com.example.bvm.ui.video.VideoRankListFragment
import kotlinx.android.synthetic.main.activity_rank.*


class RankActivity : AppCompatActivity() {

    val titleList = listOf(BookRankListFragment.type, VideoRankListFragment.type, MusicRankListFragment.type)   // 各个页面类型的标题
    val fragmentList = ArrayList<Fragment>()    // 存放各个页面的Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)
        setSupportActionBar(rankToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
//            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        fragmentList.add(BookRankListFragment())    // 添加到viewPaper
        fragmentList.add(VideoRankListFragment())
        fragmentList.add(MusicRankListFragment())

        rankModelViewPager.adapter = MyAdapter(supportFragmentManager, lifecycle)


        // 设置底部点击事件
        rankBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_book -> {
                    rankModelViewPager.currentItem = 0
                }
                R.id.navigation_video -> {
                    rankModelViewPager.currentItem = 1
                }
                R.id.navigation_music -> {
                    rankModelViewPager.currentItem = 2
                }
            }
            true
        }

        // 设置viewPager切换选择
        rankModelViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
//                Toast.makeText(BVMApplication.context, "$position", Toast.LENGTH_SHORT).show()
                when (position) {
                    0 -> rankBottomNavigation.selectedItemId = R.id.navigation_book
                    1 -> rankBottomNavigation.selectedItemId = R.id.navigation_video
                    2 -> rankBottomNavigation.selectedItemId = R.id.navigation_music
                }
            }
        })

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
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }















//    val titleList = listOf(BookRankListFragment.type, VideoRankListFragment.type, MusicRankListFragment.type)
//    val fragmentList = ArrayList<Fragment>()    // 存放各个页面的fragment
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_rank)
//        setSupportActionBar(rankToolbar)
//        supportActionBar?.let {
//            it.setDisplayHomeAsUpEnabled(true)
//        }
//
//        fragmentList.add(BookRankListFragment())
//        fragmentList.add(VideoRankListFragment())
//        fragmentList.add(MusicRankListFragment())
//
//        rankModelViewPager.adapter = MyAdapter(supportFragmentManager, lifecycle)
//
//        // 设置底部点击事件
//        rankModelBottomNavigation.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.navigation_book -> {
//                    rankModelViewPager.currentItem = 0
//                }
//                R.id.navigation_video -> {
//                    rankModelViewPager.currentItem = 1
//                }
//                R.id.navigation_music -> {
//                    rankModelViewPager.currentItem = 2
//                }
//            }
//            true
//        }
//
//        rankModelViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                when (position) {
//                    0 -> rankModelBottomNavigation.selectedItemId = R.id.navigation_book
//                    1 -> rankModelBottomNavigation.selectedItemId = R.id.navigation_video
//                    2 -> rankModelBottomNavigation.selectedItemId = R.id.navigation_music
//                }
//            }
//        })
//    }
//
//    /**
//     * viewPaper滑动事件监听
//     */
//    inner class MyAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
//
//        override fun getItemCount() = titleList.size
//
//        override fun createFragment(position: Int) = fragmentList[position]
//
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

}