package com.example.bvm.ui.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bvm.R
import com.example.bvm.ui.book.BookAddFragment
import com.example.bvm.ui.music.MusicAddFragment
import com.example.bvm.ui.video.VideoAddFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_add_model.*

/**
 * 添加信息页面
 */
class AddModelActivity : AppCompatActivity() {

    val titleList = listOf("图书", "影视", "音乐")
    val fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_model)

        setSupportActionBar(bookInputToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookInputToolbar.title = "信息录入"

        init()
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

    private fun init() {
        fragmentList.add(BookAddFragment())
        fragmentList.add(VideoAddFragment())
        fragmentList.add(MusicAddFragment())

        modelAddPager.adapter = MyAdapter(supportFragmentManager, lifecycle)


        TabLayoutMediator(modelAddTabLayout, modelAddPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = titleList[position]  // 设置文字内容
        }.attach()

        // 设置TabLayout点击事件
        modelAddTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                modelAddPager.currentItem = tab?.position!!
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        modelAddPager.offscreenPageLimit = 3    // 预加载3个fragment

    }

    /**
     * viewPaper滑动事件监听
     */
    inner class MyAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount() = titleList.size

        override fun createFragment(position: Int) = fragmentList[position]

    }


}