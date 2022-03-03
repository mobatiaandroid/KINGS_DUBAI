package com.mobatia.kingsedu.fragment.termdates

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.*
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.activity.absence.AbsenceDetailActivity
import com.mobatia.kingsedu.activity.apps.AppsDetailActivity
import com.mobatia.kingsedu.activity.term_dates.TermDatesDetailActivity
import com.mobatia.kingsedu.activity.term_dates.model.TermDatesDetailModel
import com.mobatia.kingsedu.constants.InternetCheckClass
import com.mobatia.kingsedu.constants.JsonConstants
import com.mobatia.kingsedu.fragment.home.mContext
import com.mobatia.kingsedu.fragment.messages.adapter.MessageListRecyclerAdapter
import com.mobatia.kingsedu.fragment.messages.model.MessageListApiModel
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel
import com.mobatia.kingsedu.fragment.messages.model.MessageListModel
import com.mobatia.kingsedu.fragment.report_absence.adapter.RequestAbsenceRecyclerAdapter
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceLeaveApiModel
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceListModel
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceRequestListDetailModel
import com.mobatia.kingsedu.fragment.student_information.adapter.StudentListAdapter
import com.mobatia.kingsedu.fragment.student_information.model.StudentList
import com.mobatia.kingsedu.fragment.student_information.model.StudentListModel
import com.mobatia.kingsedu.fragment.termdates.adapter.TermDatesRecyclerAdapter
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesApiModel
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesListDetailModel
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesListModel
import com.mobatia.kingsedu.manager.PreferenceData
import com.mobatia.kingsedu.recyclermanager.OnItemClickListener
import com.mobatia.kingsedu.recyclermanager.addOnItemClickListener
import com.mobatia.kingsedu.rest.AccessTokenClass
import com.mobatia.kingsedu.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermDatesFragment : Fragment(){
    lateinit var jsonConstans: JsonConstants
    lateinit var sharedprefs: PreferenceData
    lateinit var progressDialog: RelativeLayout
    lateinit var mContext: Context
     lateinit var webView: WebView
    var apiCall:Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_term_dates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jsonConstans = JsonConstants()
        sharedprefs = PreferenceData()
        mContext = requireContext()
        initializeUI()
        var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
        if (internetCheck)
        {
            callTermDatesListAPI()
        }
        else{
            InternetCheckClass.showSuccessInternetAlert(com.mobatia.kingsedu.fragment.home.mContext)
        }

        getSettings()

    }

    private fun initializeUI() {

        progressDialog = view!!.findViewById(R.id.progressDialog) as RelativeLayout
        webView =  view!!.findViewById(R.id.webView)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
    }



    fun callTermDatesListAPI()
    {
        progressDialog.visibility = View.VISIBLE
        val token = sharedprefs.getaccesstoken(mContext)
        val call: Call<TermDatesDetailModel> = ApiClient.getClient.termdates("Bearer "+token)
        call.enqueue(object : Callback<TermDatesDetailModel>{
            override fun onFailure(call: Call<TermDatesDetailModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
                Log.e("Error", t.localizedMessage)
            }
            override fun onResponse(call: Call<TermDatesDetailModel>, response: Response<TermDatesDetailModel>) {
                progressDialog.visibility = View.GONE
                if (response.body()!!.status==100)
                {

                  var title:String = response.body()!!.responseArray.title
                  var description = response.body()!!.responseArray.description
                  var image = response.body()!!.responseArray.image
                    var pushNotificationDetail="<!DOCTYPE html>\n"+
                            "<html>\n" +
                            "<head>\n" +
                            "<style>\n" +
                            "\n" +
                            "@font-face {\n" +
                            "font-family: SourceSansPro-Semibold;" +
                            "src: url(SourceSansPro-Semibold.ttf);" +

                            "font-family: SourceSansPro-Regular;" +
                            "src: url(SourceSansPro-Regular.ttf);" +
                            "}" +
                            ".title {" +
                            "font-family: SourceSansPro-Regular;" +
                            "font-size:16px;" +
                            "text-align:left;" +
                            "color:	#46C1D0;" +
                            "text-align: ####TEXT_ALIGN####;" +
                            "}" +
                            ".description {" +
                            "font-family: SourceSansPro-Semibold;" +
                            "text-align:justify;" +
                            "font-size:14px;" +
                            "color: #000000;" +
                            "text-align: ####TEXT_ALIGN####;" +
                            "}" +
                            ".externalLink {" +
                            "font-family: SourceSansPro-Light;" +
                            "text-align:left;" +
                            "font-size:15px;"  +
                            "color: #000000;"+
                            "text-align: ####TEXT_ALIGN####;"+
                            "}" +
                            ".a {"+
                            " color: #46C1D0;"+
                            "}"+
                            "</style>\n" + "</head>" +
                            "<body>" +
                            "<p class='title'>"+title

                    pushNotificationDetail=pushNotificationDetail+ "<p class='description'>" +description+ "</p>"
                    if (!image.equals(""))
                    {
                        pushNotificationDetail=pushNotificationDetail+"<center><img src='" + image + "'width='100%', height='auto'>"
                    }
                    pushNotificationDetail=pushNotificationDetail+"</body>\n</html>"

                    var htmlData=pushNotificationDetail
                    Log.e("HTML DATA",htmlData)
                    //  webView.loadData(htmlData,"text/html; charset=utf-8","utf-8")
                    webView.loadDataWithBaseURL("file:///android_asset/fonts/",htmlData,"text/html; charset=utf-8", "utf-8", "about:blank")



                }
                else if(response.body()!!.status==116)
                {
                    if (apiCall!=4)
                    {
                        apiCall=apiCall+1
                        AccessTokenClass.getAccessToken(mContext)
                        callTermDatesListAPI()
                    }
                    else{
                        progressDialog.visibility = View.GONE
                        showSuccessAlert(mContext,"Something went wrong.Please try again later","Alert")
                    }


                }
                else
                {
                    InternetCheckClass.checkApiStatusError(response.body()!!.status,mContext)
                }
            }

        })
    }
    fun showSuccessAlert(context: Context,message : String,msgHead : String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog.text = message
        alertHead.text = msgHead
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok?.setOnClickListener()
        {
            dialog.dismiss()

        }
        dialog.show()
    }

    fun getSettings()
    {
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(false)
        webView.settings.setAppCacheEnabled(false)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.loadsImagesAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.allowFileAccess = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)


        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressDialog.visibility = View.VISIBLE
                println("testing2")
                if (newProgress == 100)
                {
                    println("testing1")
                    progressDialog.visibility = View.GONE

                }
            }
        }
    }
}




