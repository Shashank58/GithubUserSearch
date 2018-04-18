package shashank.com.githubusersearch

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApiRoutes {

  @GET("search/users")
  fun getUsers(@Query("q") query: String, @Query("sort") sort: String, @Query("order") order: String): Observable<List<User>>
}