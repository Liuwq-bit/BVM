package com.example.bvm.ui.book.Adapter

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bvm.BVMApplication
import com.example.bvm.BVMApplication.Companion.context
import com.example.bvm.R
import com.example.bvm.logic.model.Book
import com.example.bvm.logic.model.BookMark
import com.example.bvm.ui.book.BookInfoActivity
import com.example.bvm.ui.book.ViewModel.BookViewModel
import com.google.android.material.button.MaterialButton
import java.util.*
import kotlin.concurrent.thread

class BookAdapter(private val fragment: Fragment, private val bookList: List<Book>, private val markList: List<BookMark>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    val viewModel by lazy { ViewModelProviders.of(fragment).get(BookViewModel::class.java) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookTitle: TextView = view.findViewById(R.id.itemTitle)
        val bookInfo: TextView = view.findViewById(R.id.itemInfo)
        val bookAuthor: TextView = view.findViewById(R.id.authorText)
        val bookImage: ImageView = view.findViewById(R.id.itemImage)
        val deleteBtn: MaterialButton = view.findViewById(R.id.deleteItemBtn)
        val bookTypeBtn0: MaterialButton = view.findViewById(R.id.typeBtn0)
        val bookTypeBtn1: MaterialButton = view.findViewById(R.id.typeBtn1)
        val bookTypeBtn2: MaterialButton = view.findViewById(R.id.typeBtn2)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)
        val holder = ViewHolder(view)

        // 设置窗体点击事件
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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]

        for (i in markList.indices) {
            if (markList[i].book_id == book.book_id) {
                when (markList[i].type) {
                    0 -> holder.bookTypeBtn0.text = "已想看"
                    1 -> holder.bookTypeBtn1.text = "已在看"
                    2 -> holder.bookTypeBtn2.text = "已看过"
                }
                break
            }
        }

        holder.bookTitle.text = book.book_name
        if (holder.bookInfo.text.length > 60)
            holder.bookInfo.text = book.info.substring(0, 60)+ "..."
        holder.bookAuthor.text = book.author

        Glide.with(context).load(book.pic).into(holder.bookImage)   // 加载图片
//        holder.bookImage.setImageResource(Glide.with(context).load(book.pic).into(holder.bookImage))

        val userId = BVMApplication.USER?.user_id ?: 0

        // 设置标记按钮监听事件
        holder.bookTypeBtn0.setOnClickListener {
//            val position = holder.adapterPosition
            val book = bookList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertBookMark(BookMark(userId, book.book_id, 0, dateFormat.format(date)))
            holder.bookTypeBtn0.text = "已想看"
            holder.bookTypeBtn1.text = "在看"
            holder.bookTypeBtn2.text = "看过"
        }
        holder.bookTypeBtn1.setOnClickListener {
//            val position = holder.adapterPosition
            val book = bookList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertBookMark(BookMark(userId, book.book_id, 1, dateFormat.format(date)))
            holder.bookTypeBtn0.text = "想看"
            holder.bookTypeBtn1.text = "已在看"
            holder.bookTypeBtn2.text = "看过"
        }
        holder.bookTypeBtn2.setOnClickListener {
//            val position = holder.adapterPosition
            val book = bookList[position]
            val date = Date()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            viewModel.insertBookMark(BookMark(userId, book.book_id, 2, dateFormat.format(date)))
            holder.bookTypeBtn0.text = "想看"
            holder.bookTypeBtn1.text = "在看"
            holder.bookTypeBtn2.text = "已看过"
        }



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
                viewModel.searchAllMarkById(BVMApplication.USER?.user_id.toString())
                notifyDataSetChanged()
            }
        }
    }

}