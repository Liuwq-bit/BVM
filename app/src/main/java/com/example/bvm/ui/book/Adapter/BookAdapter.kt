package com.example.bvm.ui.book.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.logic.model.Book
import com.example.bvm.ui.book.BookInfoActivity
import com.example.bvm.ui.book.ViewModel.BookViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.book_list.*
import kotlin.concurrent.thread

class BookAdapter(private val fragment: Fragment, private val bookList: List<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    val viewModel by lazy { ViewModelProviders.of(fragment).get(BookViewModel::class.java) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView = view.findViewById(R.id.itemTitle)
        val bookInfo: TextView = view.findViewById(R.id.itemInfo)
        val bookAuthor: TextView = view.findViewById(R.id.authorText)
        val bookImage: ImageView = view.findViewById(R.id.itemImage)
        val deleteBtn: MaterialButton = view.findViewById(R.id.deleteItemBtn)
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
                putExtra(BookInfoActivity.BOOK_AUTHOR, book.author)
                putExtra(BookInfoActivity.BOOK_AUTHOR_INFO, book.authorInfo)
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
        holder.bookAuthor.text = book.author

        Glide.with(context).load(book.pic).into(holder.bookImage)   // 加载图片
//        holder.bookImage.setImageResource(Glide.with(context).load(book.pic).into(holder.bookImage))

        holder.deleteBtn.setOnClickListener {
            viewModel.deleteBookById(book.book_id)
            viewModel.bookLiveData.observe(fragment.viewLifecycleOwner, Observer { result -> // 动态查询数据
                val books = result.getOrNull()
                if (books != null) {
//                bookRecyclerView.visibility = View.VISIBLE
                    viewModel.bookList.clear()
                    viewModel.bookList.addAll(books)
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "未能查询到任何书籍", Toast.LENGTH_SHORT).show()
                    result.exceptionOrNull()?.printStackTrace()
                }
            })

            reFreshBooks()
        }
    }

    override fun getItemCount() = bookList.size

    private fun reFreshBooks() {
        thread {
            Thread.sleep(500)
            fragment.activity?.runOnUiThread {

                viewModel.searchAllBooks()  // 显示所有书籍

                notifyDataSetChanged()
            }
        }
    }

}