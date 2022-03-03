package com.mobatia.kingsedu.fragment.attendance

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
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.utils.DateUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.constants.InternetCheckClass
import com.mobatia.kingsedu.fragment.attendance.model.AttendanceApiModel
import com.mobatia.kingsedu.fragment.attendance.model.AttendanceListDetailModel
import com.mobatia.kingsedu.fragment.attendance.model.AttendanceListModel
import com.mobatia.kingsedu.fragment.student_information.adapter.StudentListAdapter
import com.mobatia.kingsedu.fragment.student_information.model.StudentList
import com.mobatia.kingsedu.fragment.student_information.model.StudentListModel
import com.mobatia.kingsedu.manager.PreferenceData
import com.mobatia.kingsedu.recyclermanager.OnItemClickListener
import com.mobatia.kingsedu.recyclermanager.addOnItemClickListener
import com.mobatia.kingsedu.rest.AccessTokenClass
import com.mobatia.kingsedu.rest.ApiClient
import kotlinx.android.synthetic.main.fragment_attendance.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class AttendanceFragment : Fragment() ,View.OnClickListener{
    lateinit var progressDialog: RelativeLayout
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var titleTextView: TextView
    lateinit var studentImg: String
    lateinit var studentClass: String
    lateinit var studentSpinner: LinearLayout
    lateinit var calendarViewbg:LinearLayout
    lateinit var calcalendarViewendarViewbg:LinearLayout
    lateinit var studImg: ImageView
    lateinit var studentNameTxt: TextView
    lateinit var calendarView: CalendarView
    lateinit var sharedprefs: PreferenceData
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var mContext: Context
    var dateList = ArrayList<String>()
    var calendarlist = ArrayList<AttendanceListDetailModel>()
    var calendarplot = ArrayList<Calendar>()
    var apiCall:Int=0
    var apiCallDetail:Int=0
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_attendance, container, false)
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
            InternetCheckClass.showSuccessInternetAlert(com.mobatia.kingsedu.fragment.home.mContext)
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
                    var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
                    if (internetCheck)
                    {
                        getattendancedetails()
                    }
                    else{
                        InternetCheckClass.showSuccessInternetAlert(com.mobatia.kingsedu.fragment.home.mContext)
                    }

                }

                if (response.body()!!.status == 116) {
                    if(apiCall!=4)
                    {
                        apiCall=apiCall+1
                        AccessTokenClass.getAccessToken(mContext)
                        callStudentListApi()
                    }
                    else{
                        progressDialog.visibility=View.GONE
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
        calendarViewbg = view!!.findViewById(R.id.calendarViewbg) as LinearLayout

        calendarViewbg.isClickable  = false

        studImg = view!!.findViewById(R.id.studImg) as ImageView
        studentNameTxt = view!!.findViewById(R.id.studentName) as TextView
        titleTextView = view!!.findViewById(R.id.titleTextView) as TextView
        calendarView = view!!.findViewById(R.id.calendarView) as CalendarView
        calendarView.isClickable=false
        titleTextView.text = "Attendance"

        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)

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
        if(mStudentList.size>0)
        {
            val studentAdapter = StudentListAdapter(mContext,mStudentList)
            studentListRecycler.adapter = studentAdapter
        }
//        val studentAdapter = StudentListAdapter(mContext,mStudentList)
//        studentListRecycler.adapter = studentAdapter
        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        calendarView.isClickable=false

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
                progressDialog.visibility = View.VISIBLE
                getattendancedetails()

                dialog.dismiss()
            }
        })
        dialog.show()
    }

    fun getattendancedetails() {
        progressDialog.visibility = View.VISIBLE
        val token = sharedprefs.getaccesstoken(mContext)
        val attendancerBody = AttendanceApiModel(sharedprefs.getStudentID(mContext)!!)

        val call: Call<AttendanceListModel> =
            ApiClient.getClient.attendanceList(attendancerBody, "Bearer " + token)
        call.enqueue(object : Callback<AttendanceListModel> {
            override fun onFailure(call: Call<AttendanceListModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
                Log.e("Error", t.localizedMessage)
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<AttendanceListModel>,
                response: Response<AttendanceListModel>
            ) {
                progressDialog.visibility = View.GONE
                when (response.body()!!.status) {
                    100 -> {

                        val array: List<AttendanceListDetailModel> =
                            response.body()!!.responseArray.attandance_details


                        for (i in array.indices) {
                            dateList.add(array[i].date)

                            println("DATES: ${dateList[i]}")
                        }

                        calendarView.selectedDates = getSelectedDays()
                        calendarView.setDate(Calendar.getInstance())
                        calendarView.isClickable=false

                    }
                    116 -> {
                        if (apiCallDetail!=4)
                        {
                            apiCallDetail=apiCallDetail+1
                            AccessTokenClass.getAccessToken(mContext)
                            getattendancedetails()
                        }
                        else{
                            progressDialog.visibility=View.GONE
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

    private fun getSelectedDays(): MutableList<Calendar>? {
        val calendars: MutableList<Calendar> = ArrayList()
        calendarView.isContextClickable=false
        calendarView.isLongClickable=false
        calendarView.isFocusable=false
        calendarView.isEnabled=false

        for (i in dateList.indices) {
            Log.e("DATELIST",dateList.get(i).toString())
            val FullDate: String = dateList[i]
            val parts = FullDate.split("-".toRegex()).toTypedArray()
            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val date = parts[2].toInt()

            try {
                val calendar = DateUtils.getCalendar()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month-1
                calendar[Calendar.DAY_OF_MONTH] = date
                calendarView.setDate(calendar)

                calendars.add(calendar)
            } catch (e: OutOfDateRangeException) {
                e.printStackTrace()
            }

        }


        return calendars

    }
    fun showSuccessAlert(context: Context, message : String, msgHead : String)
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

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
