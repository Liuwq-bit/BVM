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
import com.example.bvm.ui.book.MarkBookListFragment
import com.example.bvm.ui.music.MarkMusicListFragment
import com.example.bvm.ui.video.MarkVideoListFragment
import kotlinx.android.synthetic.main.activity_mark.*


class MarkActivity : AppCompatActivity() {

    val titleList = listOf(MarkBookListFragment.type, MarkVideoListFragment.type, MarkMusicListFragment.type)   // 各个页面类型的标题
    val fragmentList = ArrayList<Fragment>()    // 存放各个页面的Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark)
        setSupportActionBar(markToolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
//            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        fragmentList.add(MarkBookListFragment())    // 添加到viewPaper
        fragmentList.add(MarkVideoListFragment())
        fragmentList.add(MarkMusicListFragment())

        markModelViewPager.adapter = MyAdapter(supportFragmentManager, lifecycle)


        // 设置底部点击事件
        markModelBottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_book -> {
                    markModelViewPager.currentItem = 0
                }
                R.id.navigation_video -> {
                    markModelViewPager.currentItem = 1
                }
                R.id.navigation_music -> {
                    markModelViewPager.currentItem = 2
                }
            }
            true
        }

        // 设置viewPager切换选择
        markModelViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
//                Toast.makeText(BVMApplication.context, "$position", Toast.LENGTH_SHORT).show()
                when (position) {
                    0 -> markModelBottomNavigation.selectedItemId = R.id.navigation_book
                    1 -> markModelBottomNavigation.selectedItemId = R.id.navigation_video
                    2 -> markModelBottomNavigation.selectedItemId = R.id.navigation_music
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

}