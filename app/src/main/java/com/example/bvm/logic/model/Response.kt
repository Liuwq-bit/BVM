package com.example.bvm.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
    数据模型，定义数据库信息
 */

/**
 * 用户信息
 */
@Entity
data class User(
    var user_name: String,
    var user_pwd: String,
    var pic: String,
    var register_time: String) {
    @PrimaryKey(autoGenerate = true) var user_id: Long = 0
}


@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    var book_id: Long?,
    var book_name: String,
    var label: String,
    var info: String,
    var author: String,
    var authorInfo: String,
    var add_time: String,
    var publish_time: String,
    var pic: String)

/**
 * 图书作者信息
 */
@Entity
data class Author(
    var author_name: String,
    var info: String) {
    @PrimaryKey(autoGenerate = true) var author_id: Long = 0
}


/**
 * 影视信息
 */
@Entity
data class Video(
    @PrimaryKey(autoGenerate = true)
    var video_id: Long?,
    var video_name: String,
    var video_type: String,
    var label: String,
    var info: String,
    var actor: String,
    var actorInfo: String,
    var add_time: String,
    var publish_time: String,
    var pic: String)

/**
 * 影视参演信息
 */
@Entity
data class Actor(
    var actor_name: String,
    var info: String) {
    @PrimaryKey(autoGenerate = true) var actor_id: Long = 0
}


/**
 * 音乐信息
 */
@Entity
data class Music(
    @PrimaryKey(autoGenerate = true)
    var music_id: Long?,
    var music_name: String,
    var label: String,
    var info: String,
    var singer: String,
    var singerInfo: String,
    var add_time: String,
    var publish_time: String,
    var pic: String)


/**
 * 音乐歌手信息
 */
@Entity
data class Singer(
    var singer_name: String,
    var info: String) {
    @PrimaryKey(autoGenerate = true) var singer_id: Long = 0
}

/**
 * 标记图书表， type：0，1，2分别对应想看、在看、看过
 */
@Entity(primaryKeys = ["user_id", "book_id"])
data class BookMark(
    var user_id: Long,
    var book_id: Long,
    var type: Int,
    var change_time: String)


/**
 * 标记影视表
 */
@Entity(primaryKeys = ["user_id", "video_id"])
data class VideoMark(
    var user_id: Long,
    var video_id: Long,
    var type: Int,
    var change_time: String)


/**
 * 标记音乐表
 */
@Entity(primaryKeys = ["user_id", "music_id"])
data class MusicMark(
    var user_id: Long,
    var music_id: Long,
    var type: Int,
    var change_time: String)


/**
 * 图书点评表
 */
@Entity(primaryKeys = ["book_id", "user_id"])
data class BookComment(
    var book_id: Long,
    var user_id: Long,
    var comment: String,
    var rating: Float,
    var change_time: String)


/**
 * 影视点评表
 */
@Entity(primaryKeys = ["video_id", "user_id"])
data class VideoComment(
    var video_id: Long,
    var user_id: Long,
    var comment: String,
    var rating: Float,
    var change_time: String)


/**
 * 音乐点评表
 */
@Entity(primaryKeys = ["music_id", "user_id"])
data class MusicComment(
    var music_id: Long,
    var user_id: Long,
    var comment: String,
    var rating: Float,
    var change_time: String)


/**
 * 图书作者映射表
 */
@Entity(primaryKeys = ["book_id", "author_id"])
data class AuthorOfBook(
    var book_id: Long,
    var author_id: Long)


/**
 * 影视参演映射表
 */
@Entity(primaryKeys = ["video_id", "actor_id"])
data class ActorOfVideo(
    var video_id: Long,
    var actor_id: Long)

/**
 * 音乐歌手映射表
 */
@Entity(primaryKeys = ["music_id", "singer_id"])
data class SingerOfMusic(
    var music_id: Long,
    var singer_id: Long)