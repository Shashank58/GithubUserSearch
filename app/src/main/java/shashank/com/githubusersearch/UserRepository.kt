package shashank.com.githubusersearch

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable

class UserRepository {
  private val baseUrl: String = "https://api.github.com/"

  fun getUsers(query: String, sort: String, order: String ): Observable<ResponseBody> {
    return Retrofit.Builder().baseUrl(baseUrl)
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build().create(ApiRoutes::class.java)
        .getUsers(query, sort, order)
  }
}