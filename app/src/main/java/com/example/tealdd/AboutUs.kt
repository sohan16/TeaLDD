package com.example.tealdd

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class AboutUs : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)



        // Initialize YouTube WebView
        // Initialize YouTube WebView
        val youtubeWebView: WebView = findViewById(R.id.youtubeWebView)
        youtubeWebView.settings.javaScriptEnabled = true
        youtubeWebView.settings.loadWithOverviewMode = true
        youtubeWebView.settings.useWideViewPort = true
        WebView.setWebContentsDebuggingEnabled(true)

        youtubeWebView.webChromeClient = WebChromeClient()
        youtubeWebView.clearCache(true)
        youtubeWebView.reload()
        youtubeWebView.loadDataWithBaseURL("https://www.youtube.com/embed/sA8gUU2NJoc", "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/sA8gUU2NJoc\" frameborder=\"0\" allowfullscreen></iframe>", "text/html", "utf-8", null)

        // Initialize Google Map
       // val mapFragment = supportFragmentManager.findFragmentById(R.id.googleMapFragment) as SupportMapFragment
        // Further setup for Google Map can be done here
    }
}


