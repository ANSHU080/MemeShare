package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    var currentImageUrl : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMemes()


    }
    private fun loadMemes(){
        progress_bar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = " https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Method.GET, url, null,
            Response.Listener { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean { progress_bar.visibility = View.GONE
                        return false
                    }






                }).into(imageView)
            },
            Response.ErrorListener {
                Toast.makeText(this, " something went wrong", Toast.LENGTH_SHORT).show()}
        )
        queue.add(jsonObjectRequest)



    }

    fun share_memes(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Look at This $currentImageUrl")
        val chooser = Intent.createChooser(intent, "share this memes using  ")
        startActivity(chooser)
    }
    fun next_image(view: android.view.View) {
        loadMemes()

    }


}