@file:JvmName("RxTextView")
@file:JvmMultifileClass

package com.tenten.ui.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.annotation.CheckResult
import com.tenten.ui.TEditText
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

/**
 * Create an observable of after text change events for `view`.
 *
 * *Warning:* The created observable keeps a strong reference to `view`. Unsubscribe
 * to free this reference.
 *
 * *Note:* A value will be emitted immediately on subscribe using
 * [TextView.getEditableText].
 */
@CheckResult
fun TextView.afterTextChangeEvents(): InitialValueObservable<Editable> {
    return TextViewAfterTextChangeEventObservable(this)
}

@CheckResult
fun TEditText.afterTextChangeEvents(): InitialValueObservable<Editable> {
    return TextViewAfterTextChangeEventObservable(this.getEditText())
}

private class TextViewAfterTextChangeEventObservable(
    private val view: TextView
) : InitialValueObservable<Editable>() {

    override fun subscribeListener(observer: Observer<in Editable>) {
        val listener = Listener(view, observer)
        observer.onSubscribe(listener)
        view.addTextChangedListener(listener)
    }

    override val initialValue get() = view.editableText

    private class Listener(
        private val view: TextView,
        private val observer: Observer<in Editable>
    ) : MainThreadDisposable(), TextWatcher {

        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
            observer.onNext(s)
        }

        override fun onDispose() {
            view.removeTextChangedListener(this)
        }
    }
}
