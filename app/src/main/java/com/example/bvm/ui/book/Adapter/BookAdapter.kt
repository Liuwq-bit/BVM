package com.example.bvm.ui.book.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.logic.model.Book
import com.example.bvm.ui.book.BookInfoActivity

class BookAdapter(private val fragment: Fragment, private val bookList: List<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView = view.findViewById(R.id.itemTitle)
        val bookInfo: TextView = view.findViewById(R.id.itemInfo)
        val bookAuthor: TextView = view.findViewById(R.id.authorText)
        val bookImage: ImageView = view.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val book = bookList[position]
            val intent = Intent(context, BookInfoActivity::class.java).apply {
                putExtra(BookInfoActivity.BOOK_TITLE, book.book_name)
                putExtra(BookInfoActivity.BOOK_INFO, book.info)
                putExtra(BookInfoActivity.BOOK_PIC, book.pic)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK // 在新的task中开启activity
            }
            context.startActivity(intent)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookTitle.text = book.book_name
        holder.bookInfo.text = book.info.substring(0, 60)+ "..."
//        if (book.book_name.length > 9) {
//            val shortTitle = book.book_name.substring(0, 9) + " ..."
//            holder.bookTitle.text = shortTitle
//        } else
//            holder.bookTitle.text = book.book_name
//
//        if (book.info.length > 30) {
//            val shortInfo = book.info.substring(0, 29) + " ..."
//            holder.bookInfo.text = shortInfo
//        } else
//            holder.bookInfo.text = book.info
        Glide.with(context).load(book.pic).into(holder.bookImage)   // 加载图片
//        holder.bookImage.setImageResource(Glide.with(context).load(book.pic).into(holder.bookImage))
    }

    override fun getItemCount() = bookList.size
}