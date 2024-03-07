package com.example.tealdd.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tealdd.AboutUs
import com.example.tealdd.R

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Find the about_us button by id
        val aboutUsButton: Button = view.findViewById(R.id.about_us)

        // Set click listener for the button
        aboutUsButton.setOnClickListener {
            // Start AboutUsActivity to play the YouTube video
            val intent = Intent(activity, AboutUs::class.java)
            startActivity(intent)
        }

        return view
    }
}
