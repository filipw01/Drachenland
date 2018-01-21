package com.wyimaginowanakrotka.drachenland

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startGame.setOnClickListener{
            val intent = Intent(this, FullscreenActivity::class.java)
            startActivity(intent)
        }
        settings.setOnClickListener{
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
        like.setOnClickListener{
                try {
                   intent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/tkgfilm/"))
                    startActivity(intent)
                }
                catch (e:Exception){
                    intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tkgfilm/"))
                    // intent.putExtra("page", 3)
                    startActivity(intent)
            }
        }
        continueGame.setOnClickListener{

            val intent = Intent(this, FullscreenActivity::class.java)
            startActivity(intent)
        }
    }}
