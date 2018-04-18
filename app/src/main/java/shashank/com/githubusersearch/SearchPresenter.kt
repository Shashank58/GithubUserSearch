package shashank.com.githubusersearch

import android.util.Log
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchPresenter(private val userRepository: UserRepository, private val view: SearchContract.View): SearchContract.Presenter {

  override fun getUsers(query: String) {
    userRepository.getUsers(query, "followers", "desc")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object: Subscriber<List<User>>() {
          override fun onNext(users: List<User>?) {
            if (users == null || users.isEmpty()) {
              view.showNoUsersFound()
              return
            }
            view.displayUsers(users)
          }

          override fun onCompleted() {
          }

          override fun onError(e: Throwable?) {
            Log.e("Search Presenter", e?.message)
            view.showToast(R.string.something_wrong)
          }

        })
  }
}