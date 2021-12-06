package com.example.bvm.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView


class VerticalScrollView : ScrollView {
    private var mScrollY = 0
    private var mLastMotionY = 0
    private var mClampedY = false

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        mScrollY = scrollY
        mClampedY = clampedY
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val action = ev.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_MOVE -> {
                val y = ev.y.toInt()
                val yDiff = y - mLastMotionY
                // 8为阈值，可自行定义（这里为方便用的魔法值）
                if (yDiff > 8) { // 向下滑动
                    mLastMotionY = y
                    if (mScrollY == 0 && mClampedY) {
                        // ScrollView已经滑到顶部了，再向下滑动，那就不处理了，让父控件决定是否拦截事件
                        parent.requestDisallowInterceptTouchEvent(false)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                } else if (yDiff < -8) { // 向上滑动
                    mLastMotionY = y
                    if (mScrollY > 0 && mClampedY) {
                        parent.requestDisallowInterceptTouchEvent(false)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
            MotionEvent.ACTION_DOWN -> {

                // 先告知父控件不要拦截事件，我先处理再
                parent.requestDisallowInterceptTouchEvent(true)
                mLastMotionY = ev.y.toInt()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP ->                 // 还原
                parent.requestDisallowInterceptTouchEvent(false)
        }
        return super.dispatchTouchEvent(ev)
    }
}
