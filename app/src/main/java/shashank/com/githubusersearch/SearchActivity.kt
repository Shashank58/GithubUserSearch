package shashank.com.githubusersearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity(), SearchContract.View {
  lateinit var presenter: SearchContract.Presenter
  private var searchAdapter: SearchAdapter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    users_list.layoutManager = LinearLayoutManager(this)
    users_list.setHasFixedSize(true)

    presenter = SearchPresenter(UserRepository(), this)

    SearchObservable().onView(user_search)
        .debounce(500, TimeUnit.MILLISECONDS)
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Subscriber<String>() {
          override fun onNext(query: String?) {
            if (query == null || query == "") return

            toggleLoading(true)
            presenter.getUsers(query)
          }

          override fun onCompleted() {
          }

          override fun onError(e: Throwable?) {
          }

        })
  }

  override fun displayUsers(users: List<User>) {
    if (searchAdapter == null) {
      searchAdapter = SearchAdapter(users, this)
      users_list.adapter = searchAdapter
    } else {
      searchAdapter?.updateUserList(users)
    }
    toggleLoading(false)
  }

  override fun showNoUsersFound() {
    toggleLoading(false)
    no_users_found.visibility = View.VISIBLE
    searchAdapter?.updateUserList(Collections.emptyList())
  }

  override fun showToast(message: Int) {
    toggleLoading(false)
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  private fun toggleLoading(isShow: Boolean) {
    if (isShow) {
      loading_indicator.visibility = View.VISIBLE
      users_list.visibility = View.GONE
      no_users_found.visibility = View.GONE
    } else {
      loading_indicator.visibility = View.GONE
      users_list.visibility = View.VISIBLE
    }
  }
}
