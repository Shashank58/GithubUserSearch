package shashank.com.githubusersearch

import android.support.annotation.StringRes

interface SearchContract {
  interface View {
    fun displayUsers(users: List<User>)

    fun showNoUsersFound()

    fun showToast(@StringRes message: Int)
  }

  interface Presenter {
    fun getUsers(query: String)
  }
}