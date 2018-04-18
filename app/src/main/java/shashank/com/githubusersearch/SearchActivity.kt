package shashank.com.githubusersearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity(), SearchContract.View {
  lateinit var presenter: SearchContract.Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    users_list.layoutManager = LinearLayoutManager(this)
    users_list.setHasFixedSize(true)

    presenter = SearchPresenter(UserRepository(), this)

    SearchObservable().onView(user_search)
        .debounce(1, TimeUnit.SECONDS)
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Subscriber<String>() {
          override fun onNext(query: String?) {
            if (query == null || query == "") return
            presenter.getUsers(query)
          }

          override fun onCompleted() {
          }

          override fun onError(e: Throwable?) {
          }

        })
  }

  override fun displayUsers(users: List<User>) {
    val searchAdapter = SearchAdapter(users, this)
    users_list.adapter = searchAdapter
  }

  override fun showNoUsersFound() {

  }

  override fun showToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }
}
