package com.example.bvm.ui.video.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.bvm.R
import com.example.bvm.logic.model.VideoComment

class VideoCommentAdapter(private val fragment: Fragment, private val commentList: List<VideoComment>):
    RecyclerView.Adapter<VideoCommentAdapter.ViewHolder>(){

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoCommentText: TextView = view.findViewById(R.id.commentText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        val holder = ViewHolder(view)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]

        holder.videoCommentText.text = comment.comment
    }

    override fun getItemCount() = commentList.size
}