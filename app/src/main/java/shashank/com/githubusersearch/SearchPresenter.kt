package shashank.com.githubusersearch

import android.util.Log
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchPresenter(private val userRepository: UserRepository, private val view: SearchContract.View) : SearchContract.Presenter {

  override fun getUsers(query: String) {
    userRepository.getUsers(query, "followers", "desc")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Subscriber<ResponseBody>() {
          override fun onNext(users: ResponseBody?) {
            val jsonObj = JSONObject(users?.string())
            val items: JSONArray = jsonObj.getJSONArray("items")

            if (items.length() == 0) {
              view.showNoUsersFound()
              return
            }

            view.displayUsers(getUsersList(items))
          }

          override fun onCompleted() {
          }

          override fun onError(e: Throwable?) {
            Log.e("Search Presenter", e?.message)
            view.showToast(R.string.something_wrong)
          }

        })
  }

  fun getUsersList(items: JSONArray): List<User> {
    val gson = Gson()
    val usersList: MutableList<User> = ArrayList()
    for (i in 0 until items.length()) {
      usersList.add(gson.fromJson(items[i].toString(), User::class.java))
    }
    return usersList
  }
}