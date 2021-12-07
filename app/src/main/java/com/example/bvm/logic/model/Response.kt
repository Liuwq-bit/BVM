package com.example.bvm.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
    数据模型，定义数据库信息信息
 */

/**
 * 用户信息
 */
@Entity
data class User(var user_name: String,
                var pic: String,
                var register_time: String) {
    @PrimaryKey(autoGenerate = true) var user_id: Long = 0
}


@Entity
data class Book(var book_name: String,
                var label: String,
                var info: String,
                var add_time: String,
                var publish_time: String,
                var pic: String) {
    @PrimaryKey(autoGenerate = true) var book_id: Long = 0
}

/**
 * 图书作者信息
 */
@Entity
data class Author(var author_name: String,
                  var info: String) {
    @PrimaryKey(autoGenerate = true) var author_id: Long = 0
}

/**
 * 影视信息
 */
@Entity
data class Video(var video_name: String,
                 var video_type: String,
                 var label: String,
                 var info: String,
                 var add_time: String,
                 var publish_time: String,
                 var pic: String) {
    @PrimaryKey(autoGenerate = true) var video_id: Long = 0
}

/**
 * 影视参演信息
 */
@Entity
data class Actor(var actor_name: String,
                 var info: String) {
    @PrimaryKey(autoGenerate = true) var actor_id: Long = 0
}


/**
 * 音乐信息
 */
@Entity
data class Music(var music_name: String,
                 var label: String,
                 var info: String,
                 var add_time: String,
                 var publish_time: String,
                 var pic: String) {
    @PrimaryKey(autoGenerate = true) var music_id: Long = 0
}


/**
 * 音乐歌手信息
 */
@Entity
data class Singer(var singer_name: String,
                  var info: String) {
    @PrimaryKey(autoGenerate = true) var singer_id: Long = 0
}

/**
 * 标记图书表
 */
@Entity
data class BookMark(@PrimaryKey var user_id: Long,
                    @PrimaryKey var book_id: Long,
                    var type: Int,
                    var change_time: String)


/**
 * 标记影视表
 */
@Entity
data class VideoMark(@PrimaryKey var user_id: Long,
                    @PrimaryKey var video_id: Long,
                    var type: Int,
                    var change_time: String)


/**
 * 标记音乐表
 */
@Entity
data class MusicMark(@PrimaryKey var user_id: Long,
                    @PrimaryKey var music_id: Long,
                    var type: Int,
                    var change_time: String)


/**
 * 图书点评表
 */
@Entity
data class BookComment(@PrimaryKey var book_id: Long,
                        @PrimaryKey var user_id: Long,
                        var comment: String,
                        var grade: Double,
                        var change_time: String)


/**
 * 影视点评表
 */
@Entity
data class VideoComment(@PrimaryKey var video_id: Long,
                        @PrimaryKey var user_id: Long,
                        var comment: String,
                        var grade: Double,
                        var change_time: String)


/**
 * 音乐点评表
 */
@Entity
data class MusicComment(@PrimaryKey var music_id: Long,
                        @PrimaryKey var user_id: Long,
                        var comment: String,
                        var grade: Double,
                        var change_time: String)


/**
 * 图书作者映射表
 */
@Entity
data class AuthorOfBook(@PrimaryKey var book_id: Long,
                        @PrimaryKey var author_id: Long)


/**
 * 影视参演映射表
 */
@Entity
data class ActorOfVideo(@PrimaryKey var video_id: Long,
                        @PrimaryKey var actor_id: Long)

/**
 * 音乐歌手映射表
 */
@Entity
data class SingerOfMusic(@PrimaryKey var music_id: Long,
                        @PrimaryKey var singer_id: Long)