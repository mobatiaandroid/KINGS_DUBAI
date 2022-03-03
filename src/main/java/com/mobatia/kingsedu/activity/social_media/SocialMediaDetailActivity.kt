package com.mobatia.kingsedu.activity.social_media

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.activity.home.HomeActivity
import com.mobatia.kingsedu.manager.HeaderManagerNoColorSpace
import com.mobatia.kingsedu.manager.PreferenceData
lateinit var progressDialog: RelativeLayout
class SocialMediaDetailActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private lateinit var webView: WebView
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var backRelative: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView

    var url:String?=""
    var title:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_media_detail)
        mContext=this
        url=intent.getStringExtra("url")
        title=intent.getStringExtra("title")
        System.out.println("CLICKEDDATA:"+"URL:"+url+"TITLE:"+title)
        initializeUI()
        getWebViewSettings()


    }
    private fun getWebViewSettings() {
        //progressDialog.visibility
    }
    private fun initializeUI() {
       // headermanager=HeaderManagerNoColorSpace(SocialMediaDetailActivity.this, "FACEBOOK");
        webView = findViewById(R.id.webView)
        relativeHeader = findViewById(R.id.relativeHeader)
        backRelative = findViewById(R.id.backRelative)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)

        logoClickImgView = findViewById(R.id.logoClickImgView)
        progressDialog = findViewById<RelativeLayout>(R.id.progressDialog)
        heading.text = title
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(com.mobatia.kingsedu.fragment.home.mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient(this)

        webView.loadUrl(url)

        progressDialog.visibility = View.GONE
    }



    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url: String = request?.url.toString()
            progressDialog.visibility = View.GONE
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            progressDialog.visibility = View.GONE
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
          //  progressDialog.visibility = View.GONE
            Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()
        }
    }
}