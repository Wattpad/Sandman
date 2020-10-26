package com.wattpad.sandman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sandman.*

class SandmanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sandman)

        show_ironsource_interstitial.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.interstitial_container,
                    SandmanFragment.newInstance(),
                    SandmanFragment.javaClass.name
                )
                .addToBackStack(null)
                .commit()
        }
    }
}