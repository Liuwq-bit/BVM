package com.example.bvm.ui.book

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.bvm.R
import kotlinx.android.synthetic.main.activity_book_info.*
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication.Companion.context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bvm.BVMApplication
import com.example.bvm.ui.book.ViewModel.BookViewModel
import kotlinx.android.synthetic.main.activity_book_change.*
import kotlin.concurrent.thread

/**
 * 图书信息展示页面
 */
class BookInfoActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(BookViewModel::class.java) }

    companion object {
        const val BOOK_ID = "bookId"
        const val BOOK_TITLE = "bookTitle"
        const val BOOK_LABEL = "bookLabel"
        const val BOOK_PUBLISH_TIME = "bookPublishTime"
        const val BOOK_INFO = "bookInfo"
        const val BOOK_PIC = "bookPic"
        const val BOOK_AUTHOR = "bookAuthor"
        const val BOOK_AUTHOR_INFO = "bookAuthorInfo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)

        val bookId = intent.getStringExtra(BOOK_ID) ?: ""
        val bookTitle = intent.getStringExtra(BOOK_TITLE) ?: ""
        val bookInfo = intent.getStringExtra(BOOK_INFO) ?: ""
        val bookPic = intent.getStringExtra(BOOK_PIC) ?: ""
        val bookAuthor = intent.getStringExtra(BOOK_AUTHOR) ?: ""
        val bookAuthorInfo = intent.getStringExtra(BOOK_AUTHOR_INFO) ?: ""
        val bookLabel = intent.getStringExtra(BOOK_LABEL) ?: ""
        val bookPublishTime = intent.getStringExtra(BOOK_PUBLISH_TIME) ?: ""
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

        bookInfoCommit.setOnClickListener {
//            Toast.makeText(BVMApplication.context, bookId, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, BookCommentActivity::class.java).apply {
                putExtra(BOOK_ID, bookId)
            }
            startActivity(intent)
        }

        bookInfoChangeFab.setOnClickListener {
            val intent = Intent(context, BookChangeActivity::class.java).apply {
                putExtra(BookInfoActivity.BOOK_TITLE, bookTitle)
                putExtra(BookInfoActivity.BOOK_INFO, bookInfo)
                putExtra(BookInfoActivity.BOOK_PIC, bookPic)
                putExtra(BookInfoActivity.BOOK_AUTHOR, bookAuthor)
                putExtra(BookInfoActivity.BOOK_AUTHOR_INFO, bookAuthorInfo)
                putExtra(BookInfoActivity.BOOK_ID, bookId)
                putExtra(BookInfoActivity.BOOK_LABEL, bookLabel)
                putExtra(BookInfoActivity.BOOK_PUBLISH_TIME, bookPublishTime)
            }
            startActivity(intent)
        }

        if (BVMApplication.USER?.user_id != 1L)
            bookInfoChangeFab.isVisible = false

        viewModel.commentLiveData.observe(this, Observer { result ->
            val comments = result.getOrNull()
            if (comments != null) {
                viewModel.commentList.clear()
                viewModel.commentList.addAll(comments)
            } else {
                result.exceptionOrNull()?.printStackTrace()
            }
        })

        viewModel.searchBookCommentByBookId(bookId)

        thread {
            Thread.sleep(100)
            var totalRating = 0F
            for (i in 0 until viewModel.commentList.size) {
                totalRating += viewModel.commentList[i].rating
            }
            totalRating /= viewModel.commentList.size
            bookTotalRatingBar.rating = totalRating

//            Toast.makeText(context, "${viewModel.commentList.size}", Toast.LENGTH_SHORT).show()
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