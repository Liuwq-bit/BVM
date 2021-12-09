package com.example.bvm.logic

import androidx.lifecycle.liveData
import com.bumptech.glide.util.ExceptionPassthroughInputStream
import com.example.bvm.BVMApplication
import com.example.bvm.logic.db.*
import com.example.bvm.logic.model.*
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 *  仓库类，为viewModel提供数据库调用接口
 */

object Repository {

    /**
     *   插入用户数据并返回id
     */
    fun insertUser(user: User): Long {
        return UserDatabase.getDatabase(BVMApplication.context).userDao().insertUser(user)
    }

    /**
     * 搜索全部用户数据
     */
    fun searchAllUser() = liveData(Dispatchers.IO) {
        val result = try {
            val userResponse = UserDatabase.getDatabase(BVMApplication.context).userDao().loadAllUsers()
//            Log.d("MainActivity", bookResponse[0].title)
            Result.success(userResponse)
        } catch (e: Exception) {
            Result.failure<List<User>>(e)
        }
        emit(result)
    }

    /**
     * 按用户名搜索用户数据
     */
    fun searchUserByName(user_name: String) = liveData(Dispatchers.IO) {
        val result = try {
            val userResponse = UserDatabase.getDatabase(BVMApplication.context).userDao().searchUserByName(user_name)
            Result.success(userResponse)
        } catch (e: Exception) {
            Result.failure<List<User>>(e)
        }
        emit(result)
    }

    /**
     * 按用户名搜索用户数据
     */
    fun searchUserById(user_id: String) = liveData(Dispatchers.IO) {
        val result = try {
            val userResponse = UserDatabase.getDatabase(BVMApplication.context).userDao().searchUserById(user_id.toLong())
            Result.success(userResponse)
        } catch (e: Exception) {
            Result.failure<List<User>>(e)
        }
        emit(result)
    }



//    fun searchIsbn(isbn: String) = liveData(Dispatchers.IO) {
//        val result = try {
//            val isbnResponse = BookNetwork.searchIsbn(isbn)
//            if (isbnResponse != null) {
//                val books = isbnResponse.books
//                Result.success(books)
//            } else {
//                Result.failure(RuntimeException("response message is ${isbnResponse.message}"))
//            }
//        } catch (e: Exception) {
//            Result.failure<List<Book>>(e)
//        }
//        emit(result)
//    }



    /**
     *   插入图书数据并返回id
     */
    fun insertBook(book: Book): Long {
        return BookDatabase.getDatabase(BVMApplication.context).bookDao().insertBook(book)
    }

    /**
     * 搜索全部图书数据
     */
    fun searchAllBook() = liveData(Dispatchers.IO) {
        val result = try {
            val bookResponse = BookDatabase.getDatabase(BVMApplication.context).bookDao().loadAllBooks()
//            Log.d("MainActivity", bookResponse[0].title)
            Result.success(bookResponse)
        } catch (e: Exception) {
            Result.failure<List<Book>>(e)
        }
        emit(result)
    }

    /**
     * 按标题搜索图书数据
     */
    fun searchBookByName(book_name: String) = liveData(Dispatchers.IO) {
        val result = try {
            val bookResponse = BookDatabase.getDatabase(BVMApplication.context).bookDao().searchBooks(book_name)
            Result.success(bookResponse)
        } catch (e: Exception) {
            Result.failure<List<Book>>(e)
        }
        emit(result)
    }

    /**
     * 插入影视数据并返回id
     */
    fun insertVideo(video: Video): Long {
        return VideoDatabase.getDatabase(BVMApplication.context).videoDao().insertVideo(video)
    }

    /**
     * 搜索全部影视数据
     */
    fun searchAllVideo() = liveData(Dispatchers.IO) {
        val result = try {
            val videoResponse = VideoDatabase.getDatabase(BVMApplication.context).videoDao().loadAllVideos()
            Result.success(videoResponse)
        } catch (e: Exception) {
            Result.failure<List<Video>>(e)
        }
        emit(result)
    }

    /**
     * 按标题搜索影视数据
     */
    fun searchVideoByName(video_name: String) = liveData(Dispatchers.IO) {
        val result = try {
            val videoResponse = VideoDatabase.getDatabase(BVMApplication.context).videoDao().searchVideos(video_name)
            Result.success(videoResponse)
        } catch (e: Exception) {
            Result.failure<List<Video>>(e)
        }
        emit(result)
    }

    /**
     * 插入音乐数据并返回id
     */
    fun insertMusic(music: Music): Long {
        return MusicDatabase.getDatabase(BVMApplication.context).musicDao().insertMusic(music)
    }

    /**
     * 搜索全部音乐数据
     */
    fun searchAllMusic() = liveData(Dispatchers.IO) {
        val result = try {
            val musicResponse = MusicDatabase.getDatabase(BVMApplication.context).musicDao().loadAllMusics()
            Result.success(musicResponse)
        } catch (e: Exception) {
            Result.failure<List<Music>>(e)
        }
        emit(result)
    }

    /**
     * 按标题搜索音乐数据
     */
    fun searchMusicByName(music_name: String) = liveData(Dispatchers.IO) {
        val result = try {
            val musicResponse = MusicDatabase.getDatabase(BVMApplication.context).musicDao().searchMusics(music_name)
            Result.success(musicResponse)
        } catch (e: Exception) {
            Result.failure<List<Music>>(e)
        }
        emit(result)
    }

    /**
     * 插入作者数据并返回id
     */
    fun insertAuthor(author: Author): Long {
        return AuthorDatabase.getDatabase(BVMApplication.context).authorDao().insertAuthor(author)
    }

    /**
     * 搜索全部作者数据
     */
    fun searchAllAuthor() = liveData(Dispatchers.IO) {
        val result = try {
            val authorResponse = AuthorDatabase.getDatabase(BVMApplication.context).authorDao().loadAllAuthor()
            Result.success(authorResponse)
        } catch (e: Exception) {
            Result.failure<List<Author>>(e)
        }
        emit(result)
    }

    /**
     * 按作者姓名搜索作者数据
     */
    fun searchAuthorByName(authorName: String) = liveData(Dispatchers.IO) {
        val result = try {
            val authorResponse = AuthorDatabase.getDatabase(BVMApplication.context).authorDao().searchAuthor(authorName)
            Result.success(authorName)
        } catch (e: Exception) {
            Result.failure<List<Author>>(e)
        }
        emit(result)
    }
}