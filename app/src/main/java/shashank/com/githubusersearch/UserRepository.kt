package shashank.com.githubusersearch

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable

class UserRepository {
  private val baseUrl: String = "https://api.github.com/"

  fun getUsers(query: String, sort: String, order: String ): Observable<List<User>> {
    return Retrofit.Builder().baseUrl(baseUrl)
        .client(OkHttpClient())
        .addConverterFactory(getUserConverter())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build().create(ApiRoutes::class.java)
        .getUsers(query, sort, order)
  }

  fun getUserConverter(): Converter.Factory {
    val gsonBuilder = GsonBuilder()
    val listType = object : TypeToken<List<User>>(){}.type
    gsonBuilder.registerTypeAdapter(listType, UserObjectConverter())
    return GsonConverterFactory.create(gsonBuilder.create())
  }
}