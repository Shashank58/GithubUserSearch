package shashank.com.githubusersearch

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_card.view.*

class SearchAdapter(private val users: List<User>, private val activity: Activity) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false))
  }

  override fun getItemCount(): Int = users.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(holder.itemView, users[position])
  }

  inner class ViewHolder(item: View?) : RecyclerView.ViewHolder(item) {
    fun bind(item: View, user: User) {
      item.user_name.text = user.login
      item.user_score.text = user.score.toString()
      Glide.with(activity)
          .load(user.avatarUrl)
          .into(item.user_avatar)
    }
  }
}