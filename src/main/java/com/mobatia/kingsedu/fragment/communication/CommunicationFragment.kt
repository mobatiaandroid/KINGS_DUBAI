package com.mobatia.kingsedu.fragment.communication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.common.util.Strings
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.activity.absence.AbsenceDetailActivity
import com.mobatia.kingsedu.activity.communication.letter.LetterListActvity
import com.mobatia.kingsedu.activity.communication.magazine.MagazineListActivity
import com.mobatia.kingsedu.activity.communication.newsletter.NewsLetterActivity
import com.mobatia.kingsedu.activity.communication.newsletter.NewsLetterDetailActivity
import com.mobatia.kingsedu.activity.communication.newsletter.NewsLetterListActivity
import com.mobatia.kingsedu.activity.communication.newsletter.adapter.NewsLetterRecyclerAdapter
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewLetterListDetailModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterDetailModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterListAPiModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterListModel
import com.mobatia.kingsedu.activity.home.PageView
import com.mobatia.kingsedu.activity.settings.termsofservice.TermsOfServiceActivity
import com.mobatia.kingsedu.activity.settings.tutorial.TutorialActivity
import com.mobatia.kingsedu.activity.social_media.SocialMediaDetailActivity
import com.mobatia.kingsedu.constants.InternetCheckClass
import com.mobatia.kingsedu.constants.JsonConstants
import com.mobatia.kingsedu.fragment.communication.adapter.CommunicationRecyclerAdapter
import com.mobatia.kingsedu.fragment.home.mContext
import com.mobatia.kingsedu.fragment.home.model.BannerModel
import com.mobatia.kingsedu.fragment.home.pager
import com.mobatia.kingsedu.fragment.settings.adapter.SettingsRecyclerAdapter
import com.mobatia.kingsedu.fragment.socialmedia.adapter.SocialMediaRecyclerAdapter
import com.mobatia.kingsedu.fragment.socialmedia.model.SocialMediaDetailModel
import com.mobatia.kingsedu.fragment.socialmedia.model.SocialMediaListModel
import com.mobatia.kingsedu.manager.PreferenceData
import com.mobatia.kingsedu.recyclermanager.OnItemClickListener
import com.mobatia.kingsedu.recyclermanager.addOnItemClickListener
import com.mobatia.kingsedu.rest.AccessTokenClass
import com.mobatia.kingsedu.rest.ApiClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class CommunicationFragment : Fragment(){
    lateinit var mContext:Context
    lateinit var jsonConstans: JsonConstants
    lateinit var sharedprefs: PreferenceData
    lateinit var communicationArrayList :ArrayList<String>
    lateinit var progressDialog: RelativeLayout
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var newsLetterRecycler: RecyclerView
    var apiCall:Int=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_communication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jsonConstans = JsonConstants()
        sharedprefs = PreferenceData()
        mContext = requireContext()
        initializeUI()
    }

    private fun initializeUI()
    {
        linearLayoutManager = LinearLayoutManager(mContext)
        newsLetterRecycler = view!!.findViewById(R.id.newsLetterRecycler) as RecyclerView
        newsLetterRecycler.layoutManager = linearLayoutManager
        newsLetterRecycler.itemAnimator = DefaultItemAnimator()

        communicationArrayList= ArrayList()
//        communicationArrayList.add("Newsletters")
        communicationArrayList.add("Letters")
        communicationArrayList.add("Magazine")
        val settingsAdapter = SettingsRecyclerAdapter(mContext,communicationArrayList)
        newsLetterRecycler.adapter = settingsAdapter
        newsLetterRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
//                if (position==0)
//                {
//                    val intent =Intent(mContext, NewsLetterListActivity::class.java)
//                    startActivity(intent)
//                }
//                else
                    if(position==0)
                {
                    val intent =Intent(mContext, LetterListActvity::class.java)
                    startActivity(intent)
                }
                else if(position==1)
                {
                    val intent =Intent(mContext, MagazineListActivity::class.java)
                    startActivity(intent)
                }

            }
        })

    }


}




