package com.example.bvm.ui.book

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.bvm.R
import kotlinx.android.synthetic.main.activity_book_info.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication
import com.example.bvm.BVMApplication.Companion.context


class BookInfoActivity : AppCompatActivity() {

    companion object {
        const val BOOK_TITLE = "bookTitle"
        const val BOOK_INFO = "bookInfo"
        const val BOOK_PIC = "bookPic"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_info)

        val bookTitle = intent.getStringExtra(BOOK_TITLE) ?: ""
        val bookInfo = intent.getStringExtra(BOOK_INFO) ?: ""
        val bookPic = intent.getStringExtra(BOOK_PIC) ?: ""
        setSupportActionBar(bookInfoToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bookInfoCollapsingToolbar.title = bookTitle
        Glide.with(this).load(bookPic).into(bookInfoImageView)
        bookInfoContextText.text = bookInfo

        bookInfoImageView.setOnClickListener {
            val tmp = bookInfoImageView.drawable as BitmapDrawable
            val bitmap = tmp.bitmap
            bigImageLoader(bitmap)
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
        image.setImageBitmap(bitmap)
        dialog.setContentView(image)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
        image.setOnClickListener { dialog.cancel() }
    }

}