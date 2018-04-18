package shashank.com.githubusersearch

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import rx.Observable
import rx.subjects.PublishSubject

class SearchObservable {
  fun onView(editText: EditText): Observable<String> {
    val publishSubject = PublishSubject.create<String>()
    editText.addTextChangedListener(object: TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }

      override fun afterTextChanged(s: Editable?) {
        publishSubject.onNext(s.toString())
      }

    })

    return publishSubject
  }
}