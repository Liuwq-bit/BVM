package com.example.bvm.logic.book.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    数据模型，定义网络返回的书籍信息
 */
data class BookResponse(val message: String, val books: List<Book>)

@Entity
data class Book(val title: String,
                val createTime: String,
//                val isbn: String,
                val bookInfo: String,
//                val author: Author,
//                val text: originalText,
                val label: String,
                val coverUrl: String,
                val url: String,
                val rating: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

data class BookInfo(val author: Author,
                    val publisher: String,
                    val year: String,
                    val isbn: String,
                    val intro: String)

data class Author(val author: String,
                  val intro: String)

data class BookRating(val count: String,
                      val value: String,
                      val star_count: String)