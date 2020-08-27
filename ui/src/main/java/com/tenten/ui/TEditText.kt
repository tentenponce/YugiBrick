package com.tenten.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.android.material.textfield.TextInputLayout
import com.tenten.ui.util.afterTextChangeEvents
import io.reactivex.rxkotlin.subscribeBy

/**
 *
 * Created by Exequiel Egbert V. Ponce on 8/27/2020.
 */
class TEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    private var tvTitle: AppCompatTextView
    private var ivRight: AppCompatImageView
    private var tlInput: TextInputLayout
    private var etInput: AppCompatEditText
    private var tvError: AppCompatTextView

    init {
        inflate(getContext(), R.layout.tedit_text, this)

        val a = context.obtainStyledAttributes(attrs, R.styleable.TEditText)

        try {
            val title = a.getString(R.styleable.TEditText_android_text)
            val hint = a.getString(R.styleable.TEditText_android_hint)
            val contentDescription = a.getString(R.styleable.TEditText_android_contentDescription)
            val textSize = a.getDimensionPixelSize(R.styleable.TEditText_android_textSize, -1)
            @Suppress("DEPRECATION") val textColor = a.getColor(
                R.styleable.TEditText_android_textColor,
                context.resources.getColor(R.color.veryGray)
            )

            val errorText = a.getString(R.styleable.TEditText_error)
            val inputType =
                a.getInt(R.styleable.TEditText_android_inputType, EditorInfo.TYPE_CLASS_TEXT)
            val drawableRight = a.getDrawable(R.styleable.TEditText_android_drawableEnd)

            tvTitle = findViewById(R.id.tv_title)
            ivRight = findViewById(R.id.iv_right)
            tlInput = findViewById(R.id.tl_input)
            etInput = findViewById(R.id.et_input)
            tvError = findViewById(R.id.tv_error)

            etInput.contentDescription = contentDescription
            etInput.afterTextChangeEvents()
                .skipInitialValue()
                .subscribeBy(onNext = { editable ->
                    if (editable.toString() != "")
                        setErrorText("")
                })

            setTitle(title)
            setHint(hint)
            setContentDescription(contentDescription)
            setTextSize(textSize)
            setTextColor(textColor)
            setErrorText(errorText)
            setInputType(inputType)
            setDrawableRight(drawableRight)
        } finally {
            a.recycle()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setInputType(inputType: Int) {
        etInput.inputType = inputType
    }

    fun setTitle(title: String?) {
        if (title != null && title.isNotBlank() && title.isNotEmpty()) {
            tvTitle.visibility = View.VISIBLE
            tvTitle.text = title
        } else {
            tvTitle.visibility = View.GONE
        }
    }


    fun getTitle(): String {
        return tvTitle.text?.toString() ?: ""
    }

    fun setHint(hint: String?) {
        etInput.hint = hint
    }

    fun setTextSize(textSize: Int) {
        if (textSize >= 0) {
            etInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        }
    }

    fun setErrorText(error: String?) {
        if (error != null && error.isNotBlank() && error.isNotEmpty()) {
            tvError.visibility = View.VISIBLE
            tvError.text = error
            etInput.setBackgroundResource(R.drawable.input_bg_error)
        } else {
            etInput.setBackgroundResource(R.drawable.input_selector)
            tvError.visibility = View.GONE
        }
    }

    fun getEditText(): AppCompatEditText {
        return etInput
    }

    fun setTextColor(color: Int) {
        etInput.setTextColor(color)
    }

    fun getErrorTextView(): AppCompatTextView {
        return tvError
    }

    fun setDrawableRight(drawable: Drawable?) {
        ivRight.setImageDrawable(drawable)
    }

    fun setLeftPadding(value: Int) {
        etInput.setPadding(
            value,
            0,
            0,
            0
        )
    }

    override fun setClickable(clickable: Boolean) {
        if (clickable) {
            etInput.isFocusable = false
            etInput.isLongClickable = false
        } else {
            etInput.isFocusable = true
            etInput.isLongClickable = true
        }
    }

}
