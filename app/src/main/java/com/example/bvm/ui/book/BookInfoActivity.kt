package com.example.bvm.ui.book

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.bvm.R
import kotlinx.android.synthetic.main.activity_book_info.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication.Companion.context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * 图书信息展示页面
 */
class BookInfoActivity : AppCompatActivity() {

    companion object {
        const val BOOK_TITLE = "bookTitle"
        const val BOOK_INFO = "bookInfo"
        const val BOOK_PIC = "bookPic"
        const val BOOK_AUTHOR = "bookAuthor"
        const val BOOK_AUTHOR_INFO = "bookAuthorInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)

        val bookTitle = intent.getStringExtra(BOOK_TITLE) ?: ""
        val bookInfo = intent.getStringExtra(BOOK_INFO) ?: ""
        val bookPic = intent.getStringExtra(BOOK_PIC) ?: ""
        val bookAuthor = intent.getStringExtra(BOOK_AUTHOR) ?: ""
        val bookAuthorInfo = intent.getStringExtra(BOOK_AUTHOR_INFO) ?: ""
        setSupportActionBar(bookInfoToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookInfoCollapsingToolbar.title = bookTitle
        Glide.with(this).load(bookPic).into(bookInfoImageView)
        bookInfoContextText.text = bookInfo
        bookAuthorContextText.text = bookAuthor
        bookAuthorInfoContextText.text = bookAuthorInfo

        bookInfoImageView.setOnClickListener {
//            val tmp = bookInfoImageView.drawable as BitmapDrawable
//            val bitmap = tmp.bitmap
//            bigImageLoader(bitmap)
            load(bookPic) { bitmap ->
                bigImageLoader(bitmap)
            }
        }

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

    private fun bigImageLoader(bitmap: Bitmap) {
        val dialog = Dialog(this)
        val image = ImageView(context)
        if (bitmap != null) {
            image.setImageBitmap(bitmap)
            dialog.setContentView(image)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()
            image.setOnClickListener { dialog.cancel() }
        }
    }

    /**
     * 加载网络地址 [url] 图片返回 Bitmap
     */
    private fun load(url: String, success: (Bitmap) -> Unit) {
        Glide.with(context) // context，可添加到参数中
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // 成功返回 Bitmap
                    success.invoke(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}