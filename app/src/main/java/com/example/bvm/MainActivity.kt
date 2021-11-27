package com.example.bvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    val titleList = listOf(BookListFragment.type, VideoListFragment.type, MusicListFragment.type)   // 各个页面类型的标题
    val fragmentList = ArrayList<Fragment>()    // 存放各个页面的Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fragmentList.add(BookListFragment())    // 添加到viewPaper
        fragmentList.add(VideoListFragment())
        fragmentList.add(MusicListFragment())

        modelViewPager.adapter = MyAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(modelTabLayout, modelViewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = titleList[position]  // 设置文字内容
            when (position) {   // 设置图标
                0 -> {
                    tab.icon = getDrawable(R.drawable.ic_carwashing)
                }
                1 -> {
                    tab.icon = getDrawable(R.drawable.ic_clear_day)
                }
                2 -> {
                    tab.icon = getDrawable(R.drawable.ic_clear_night)
                }
            }
        }.attach()

    }

    // viewPaper滑动事件监听
    inner class MyAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount() = titleList.size

        override fun createFragment(position: Int) : Fragment {
            return fragmentList[position]
        }

    }

}