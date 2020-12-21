package com.example.nettitesti2020

import android.os.Bundle
import android.util.Log.d
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface UserService {
   @GET("users")
    fun fetchAllUsers( /*  @Query("lat") lat: String  */ ): Call<List<User>>
}
//interface UserService {
 //   @GET("pivot/prod/api/epirapo/covid19case.json")
   // fun fetchAllUsers( /*  @Query("lat") lat: String  */ ): Call<List<User>>
//}
class MainActivity : AppCompatActivity() {

   // private lateinit var mRecyclerView: RecyclerView
   // private var mAdapter: UsersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

            val retrofit = Retrofit.Builder()
                //.baseUrl ( "https://sampo.thl.fi/")
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())   // json -> object
                .build()
        // Request
        val service = retrofit.create(UserService::class.java)
        val call = service.fetchAllUsers( /* parametrit */)
        // Response
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>){
                showData(response.body()!!)
          //    d("Daniel","onResponse: ${response.body()!![0].email}")
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            d("Daniel", "onFailure")
           }
        })
    }

    private fun showData(users: List<User>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter =  UsersAdapter(users)
        }

    }
}

