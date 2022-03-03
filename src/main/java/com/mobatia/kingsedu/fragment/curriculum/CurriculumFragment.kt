package com.mobatia.kingsedu.fragment.curriculum

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
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
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.constants.InternetCheckClass
import com.mobatia.kingsedu.fragment.curriculum.adapter.CurriculumRecyclerAdapter
import com.mobatia.kingsedu.fragment.curriculum.model.CuriculumListModel
import com.mobatia.kingsedu.fragment.curriculum.model.CuriculumResponseArray
import com.mobatia.kingsedu.fragment.curriculum.model.CurriculumStudentApiModel
import com.mobatia.kingsedu.fragment.reports.ReportListRecyclerAdapter
import com.mobatia.kingsedu.fragment.reports.model.ReportApiModel
import com.mobatia.kingsedu.fragment.reports.model.ReportListDetailModel
import com.mobatia.kingsedu.fragment.reports.model.ReportResponseArray
import com.mobatia.kingsedu.fragment.student_information.adapter.StudentListAdapter
import com.mobatia.kingsedu.fragment.student_information.model.StudentList
import com.mobatia.kingsedu.fragment.student_information.model.StudentListModel
import com.mobatia.kingsedu.manager.PreferenceData
import com.mobatia.kingsedu.recyclermanager.OnItemClickListener
import com.mobatia.kingsedu.recyclermanager.addOnItemClickListener
import com.mobatia.kingsedu.rest.AccessTokenClass
import com.mobatia.kingsedu.rest.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurriculumFragment : Fragment() {
    lateinit var progressDialog: RelativeLayout
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var titleTextView: TextView
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentSpinner: LinearLayout
    lateinit var studImg: ImageView
    lateinit var studentNameTxt: TextView
    lateinit var curriculumRecycler: RecyclerView
    lateinit var sharedprefs: PreferenceData
    lateinit var mContext: Context
    private lateinit var linearLayoutManager: LinearLayoutManager
    var studentListArrayList = ArrayList<StudentList>()
    var reportArrayList = ArrayList<ReportListDetailModel>()
    var apiCall:Int=0
    var apiCallDetail:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_curriculum, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        sharedprefs = PreferenceData()
        initializeUI()
        var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
        if (internetCheck)
        {
            callStudentListApi()
        }
        else{
            InternetCheckClass.showSuccessInternetAlert(mContext)
        }

    }

    fun callStudentListApi() {
        val token = sharedprefs.getaccesstoken(mContext)
        val call: Call<StudentListModel> = ApiClient.getClient.studentList("Bearer " + token)
        call.enqueue(object : Callback<StudentListModel> {
            override fun onFailure(call: Call<StudentListModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<StudentListModel>,
                response: Response<StudentListModel>
            ) {
                if (response.body()!!.status == 100) {
                    studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    println("CalendarResoponse" + response.body())
                    if (sharedprefs.getStudentID(mContext).equals("")) {
                        Log.e("Empty Img", "Empty")
                        studentName = studentListArrayList.get(0).name
                        studentImg = studentListArrayList.get(0).photo
                        studentId = studentListArrayList.get(0).id
                        studentClass = studentListArrayList.get(0).section
                        sharedprefs.setStudentID(mContext, studentId)
                        sharedprefs.setStudentName(mContext, studentName)
                        sharedprefs.setStudentPhoto(mContext, studentImg)
                        sharedprefs.setStudentClass(mContext, studentClass)
                        studentNameTxt.text = studentName
                        if (!studentImg.equals("")) {
                            Glide.with(mContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.student)
                                .error(R.drawable.student)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(studImg)
                        } else {
                            studImg.setImageResource(R.drawable.student)

                        }

                    } else {
                        studentName = sharedprefs.getStudentName(mContext)!!
                        studentImg = sharedprefs.getStudentPhoto(mContext)!!
                        studentId = sharedprefs.getStudentID(mContext)!!
                        studentClass = sharedprefs.getStudentClass(mContext)!!
                        studentNameTxt.text = studentName
                        if (studentImg != "") {
                            Glide.with(mContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.student)
                                .error(R.drawable.student)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(studImg)
                        } else {
                            studImg.setImageResource(R.drawable.student)
                        }


                    }
                    getreportsdetails()

                }

                if (response.body()!!.status == 116) {
                    if (apiCall!=4)
                    {
                        apiCall=apiCall+1
                        AccessTokenClass.getAccessToken(mContext)
                        callStudentListApi()
                    }
                    else{
                        showSuccessAlert(mContext,"Something went wrong.Please try again later","Alert")
                    }

                } else {
                    InternetCheckClass.checkApiStatusError(response.body()!!.status, mContext)

                }

            }

        })
    }

    private fun initializeUI() {
        progressDialog = view!!.findViewById(R.id.progressDialog) as RelativeLayout
        studentSpinner = view!!.findViewById(R.id.studentSpinner) as LinearLayout
        studImg = view!!.findViewById(R.id.studImg) as ImageView
        studentNameTxt = view!!.findViewById(R.id.studentName) as TextView
        titleTextView = view!!.findViewById(R.id.titleTextView) as TextView
        curriculumRecycler = view!!.findViewById(R.id.curriculumRecycler) as RecyclerView
        titleTextView.text = "Curriculum"
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)

        linearLayoutManager = LinearLayoutManager(mContext)
        curriculumRecycler.layoutManager = linearLayoutManager
        curriculumRecycler.itemAnimator = DefaultItemAnimator()

        curriculumRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

            }

        })
        studentSpinner.setOnClickListener {

            showStudentList(mContext, studentListArrayList)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun showStudentList(context: Context, mStudentList: ArrayList<StudentList>) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.boy)
        //if(mSocialMediaArray.get())
        val sdk = Build.VERSION.SDK_INT
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            btn_dismiss.setBackgroundDrawable(
                mContext.resources.getDrawable(R.drawable.button_new)
            )
        } else {
            btn_dismiss.background = mContext.resources.getDrawable(R.drawable.button_new)
        }

        studentListRecycler.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        studentListRecycler.layoutManager = llm
        if (mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(mContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }

        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                studentName = studentListArrayList[position].name
                studentImg = studentListArrayList[position].photo
                studentId = studentListArrayList[position].id
                studentClass = studentListArrayList[position].section
                sharedprefs.setStudentID(mContext, studentId)
                sharedprefs.setStudentName(mContext, studentName)
                sharedprefs.setStudentPhoto(mContext, studentImg)
                sharedprefs.setStudentClass(mContext, studentClass)
                studentNameTxt.text = studentName
                if (studentImg != "") {
                    Glide.with(mContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(studImg)
                } else {
                    studImg.setImageResource(R.drawable.student)
                }
                getreportsdetails()
                Toast.makeText(activity, mStudentList[position].name, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
        dialog.show()
    }

    private fun getreportsdetails() {
        progressDialog.visibility = View.VISIBLE
        val token = sharedprefs.getaccesstoken(mContext)
        val studentid = CurriculumStudentApiModel(sharedprefs.getStudentID(mContext)!!)
        val call: Call<CuriculumListModel> =
            ApiClient.getClient.curriculumList(studentid, "Bearer " + token)
        call.enqueue(object : Callback<CuriculumListModel> {
            override fun onFailure(call: Call<CuriculumListModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
                Log.e("Error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<CuriculumListModel>,
                response: Response<CuriculumListModel>
            ) {
                progressDialog.visibility = View.GONE
                when (response.body()!!.status) {
                    100 -> {

//                        val reportlist: List<ReportResponseArray> = response.body()!!.data
                        val datas: ArrayList<CuriculumResponseArray> = response.body()!!.data as ArrayList<CuriculumResponseArray>
                        if(datas.size>0)
                        {
                            curriculumRecycler.visibility=View.VISIBLE
                            val rAdapter: CurriculumRecyclerAdapter = CurriculumRecyclerAdapter(mContext,datas)
                            curriculumRecycler.adapter = rAdapter
                        }
                        else
                        {
                            curriculumRecycler.visibility=View.GONE
                            showSuccessAlert(mContext,"Curriculum details is not available.","Alert")
                        }

                    }
                    132->
                    {
                        showSuccessAlert(mContext,"Curriculum details is not available.","Alert")
                    }
                    116 -> {
                        if (apiCallDetail!=4)
                        {
                            apiCallDetail=apiCallDetail+1
                            AccessTokenClass.getAccessToken(mContext)
                            getreportsdetails()
                        }
                        else{
                            progressDialog.visibility = View.GONE
                            showSuccessAlert(mContext,"Something went wrong.Please try again later","Alert")
                        }

                    }
                    else -> {
                        InternetCheckClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
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
}