package com.nalexand.friendlocation.view

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.network.RetrofitFactory

class AuthActivity : AppCompatActivity() {

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val res = data?.data
        Log.d("keyTAG", res.toString())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val authBtn = findViewById<Button>(R.id.auth_button)

        authBtn.setOnClickListener {
            val url = "https://api.intra.42.fr/oauth/authorize?client_id=6d94ec75f2839e414dcba355566192b67ee05bc2d9a3da66aee8645343a4bffd&redirect_uri=https%3A%2F%2Fwww.google.ru%2F&response_type=code"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
            intent.putExtra("result", "de6ac060116057e0d3129a461d45ace116c3136da2b2e8778db39ab4fd6b53bd")
            setResult(Activity.RESULT_OK, intent)
            Log.d("keyTAG", "finish activity2")
            finish()
        }
    }
}