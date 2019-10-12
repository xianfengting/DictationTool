package com.src_resources.dictationtool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnResourcePackageMarket: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnResourcePackageMarket = findViewById(R.id.btnResourcePackageMarket)
        btnResourcePackageMarket.setOnClickListener {
            startActivity(Intent(this, ResourcePackageMarketActivity::class.java))
        }
    }
}
