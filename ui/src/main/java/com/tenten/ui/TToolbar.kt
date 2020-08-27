package com.tenten.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class TToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var ivIconLeft: AppCompatImageView
    private var ivIconRight: AppCompatImageView
    private var tvTText: TText

    init {
        inflate(getContext(), R.layout.ubx_toolbar, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.TToolbar)

        try {
            val title = a.getString(R.styleable.TToolbar_title)
            val imageLeft = a.getDrawable(R.styleable.TToolbar_imageLeft)
            val imageRight = a.getDrawable(R.styleable.TToolbar_imageRight)

            ivIconLeft = findViewById(R.id.iv_icon_left)
            ivIconRight = findViewById(R.id.iv_icon_right)
            tvTText = findViewById(R.id.ubx_tv_text)

            setTitle(title)
            setIconLeft(imageLeft)
            setIconRight(imageRight)

        } finally {
            a.recycle()
        }
    }

    fun setTitle(title: String?) {
        tvTText.text = title
    }

    fun setIconLeftClickListener(listener: OnClickListener) {
        ivIconLeft.setOnClickListener(listener)
    }

    fun setIconRightClickListener(listener: OnClickListener) {
        ivIconRight.setOnClickListener(listener)
    }

    fun setIconLeft(resId: Int) {
        ivIconLeft.setBackgroundResource(resId)
    }

    fun setIconLeft(drawable: Drawable?) {
        ivIconLeft.setImageDrawable(drawable)
    }

    fun setIconRight(resId: Int) {
        ivIconRight.setBackgroundResource(resId)
    }

    fun setIconRight(drawable: Drawable?) {
        ivIconRight.setImageDrawable(drawable)
    }
}
