package com.mobatia.kingsedu.fragment.teacher_contact

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.mobatia.kingsedu.R
import com.mobatia.kingsedu.constants.InternetCheckClass
import com.mobatia.kingsedu.constants.JsonConstants
import com.mobatia.kingsedu.fragment.student_information.adapter.StudentListAdapter
import com.mobatia.kingsedu.fragment.student_information.model.StudentList
import com.mobatia.kingsedu.fragment.student_information.model.StudentListModel
import com.mobatia.kingsedu.fragment.teacher_contact.adapter.StaffListAdapter
import com.mobatia.kingsedu.fragment.teacher_contact.model.SendStaffMailApiModel
import com.mobatia.kingsedu.fragment.teacher_contact.model.StaffInfoDetail
import com.mobatia.kingsedu.fragment.teacher_contact.model.StaffListApiModel
import com.mobatia.kingsedu.fragment.teacher_contact.model.StaffListModel
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

class TeacherContactFragment : Fragment() {
    lateinit var jsonConstans: JsonConstants
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var staffName: String
    lateinit var staffrole: String
    lateinit var staffId: String
    lateinit var staffImg: String
    lateinit var staffEmail: String
    lateinit var sharedprefs: PreferenceData
    var studentListArrayList = ArrayList<StudentList>()
    lateinit var staffListArray: ArrayList<StaffInfoDetail>
    lateinit var selectStudentImgView: ImageView
    lateinit var selectStaffImgView: ImageView
    lateinit var studentNameTV: TextView
    lateinit var staffNameTV: TextView
    lateinit var staffroleTV: TextView
    lateinit var contactStaffBtn: Button
    lateinit var staffRelative: RelativeLayout
    private lateinit var loader: RelativeLayout
    lateinit var mContext: Context
    private var lastClickTime: Long = 0


    var apiCall: Int = 0
    var apiCallDetail: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_teacher_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jsonConstans = JsonConstants()
        sharedprefs = PreferenceData()
        mContext = requireContext()
        initializeUI()
        var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
        if (internetCheck) {
            callStudentListApi()
        } else {
            InternetCheckClass.showSuccessInternetAlert(com.mobatia.kingsedu.fragment.home.mContext)
        }

    }

    private fun initializeUI() {
        studentName = ""
        studentImg = ""
        studentId = ""
        staffEmail = ""
        selectStudentImgView = view!!.findViewById(R.id.selectStudentImgView) as ImageView
        selectStaffImgView = view!!.findViewById(R.id.selectStaffImgView) as ImageView
        studentNameTV = view!!.findViewById(R.id.studentNameTV) as TextView
        staffNameTV = view!!.findViewById(R.id.staffNameTV) as TextView
        staffroleTV = view!!.findViewById(R.id.staffrole) as TextView
        contactStaffBtn = view!!.findViewById(R.id.contactStaffBtn) as Button
        staffRelative = view!!.findViewById(R.id.staffRelative) as RelativeLayout
        loader = view!!.findViewById(R.id.loader) as RelativeLayout
        contactStaffBtn.visibility = View.GONE
        selectStudentImgView.setOnClickListener(View.OnClickListener {
            loader.visibility = View.VISIBLE

            if (studentListArrayList.size > 0) {
                showStudentList(mContext, studentListArrayList)
                loader.visibility = View.GONE


            } else {
                callStudentListApi()
                loader.visibility = View.GONE

            }
        })
        selectStaffImgView.setOnClickListener(View.OnClickListener {

            if (SystemClock.elapsedRealtime()-lastClickTime<1000){
                return@OnClickListener

            }
            lastClickTime = SystemClock.elapsedRealtime()
            if (staffListArray.size > 0) {
                showStaffList(mContext, staffListArray)
                //  callStaffListApi(studentId)
            } else {
                callStaffListApi(studentId)
                // Toast.makeText(activity,"No Staff Found", Toast.LENGTH_SHORT).show()
            }
        })
        contactStaffBtn.setOnClickListener(View.OnClickListener {

            val dialog = Dialog(mContext)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_send_email_dialog)
            var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
            var cancelButton = dialog.findViewById(R.id.cancelButton) as Button
            var submitButton = dialog.findViewById(R.id.submitButton) as Button
            var text_dialog = dialog.findViewById(R.id.text_dialog) as EditText
            var text_content = dialog.findViewById(R.id.text_content) as EditText
            var progressDialog = dialog.findViewById(R.id.progressDialog) as RelativeLayout
            iconImageView.setImageResource(R.drawable.roundemail)
            text_dialog.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    text_dialog.hint = ""
                    text_dialog.gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
                    text_dialog.setPadding(5, 5, 0, 0)
                } else {
                    text_dialog.hint = "Enter your subject here..."
                    text_dialog.gravity = Gravity.CENTER
                }
            }
            text_content.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    text_content.gravity = Gravity.LEFT
                } else {
                    text_content.gravity = Gravity.CENTER
                }
            }

            cancelButton.setOnClickListener()
            {
                dialog.dismiss()
            }
            submitButton.setOnClickListener()
            {
                if (text_dialog.text.toString().trim().equals("")) {
                    InternetCheckClass.showErrorAlert(
                        mContext,
                        "Please enter your subject",
                        "Alert"
                    )

                } else {
                    if (text_content.text.toString().trim().equals("")) {
                        InternetCheckClass.showErrorAlert(
                            mContext,
                            "Please enter your content",
                            "Alert"
                        )

                    } else {
                        progressDialog.visibility = View.VISIBLE
                        val aniRotate: Animation =
                            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
                        progressDialog.startAnimation(aniRotate)
                        var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
                        if (internetCheck) {
                            callSendEmailToStaffApi(
                                text_dialog.text.toString().trim(),
                                text_content.text.toString().trim(),
                                studentId,
                                staffEmail,
                                dialog,
                                progressDialog
                            )

                        } else {
                            InternetCheckClass.showSuccessInternetAlert(com.mobatia.kingsedu.fragment.home.mContext)
                        }
                    }
                }
            }
            dialog.show()
        })

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

                    Log.e("Empty Img", "Empty")
                    if (response.body()!!.responseArray.studentList.size > 0) {
                        studentListArrayList.addAll(response.body()!!.responseArray.studentList)
                    }
                } else if (response.body()!!.status == 116) {
                    if (apiCall != 4) {
                        apiCall = apiCall + 1
                        AccessTokenClass.getAccessToken(mContext)
                        callStudentListApi()
                    } else {

                        showSuccessAlertnew(
                            mContext,
                            "Something went wrong.Please try again later",
                            "Alert"
                        )

                    }
                } else {
                    if (response.body()!!.status == 103) {
                        //validation check error
                    } else {
                        //check status code checks
                        InternetCheckClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                }
            }
        })
    }

    fun callStaffListApi(studentID: String) {
        staffListArray = ArrayList<StaffInfoDetail>()
        val token = sharedprefs.getaccesstoken(mContext)
        var staffBody = StaffListApiModel(studentID)
        val call: Call<StaffListModel> = ApiClient.getClient.staffList(staffBody, "Bearer " + token)
        call.enqueue(object : Callback<StaffListModel> {
            override fun onFailure(call: Call<StaffListModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<StaffListModel>,
                response: Response<StaffListModel>
            ) {
        //        val arraySize: Int = response.body()!!.responseArray.staff_info.size
                if (response.body()!!.status == 100) {
                    if (response.body()!!.responseArray.staff_info.size > 0) {
                        staffListArray.addAll(response.body()!!.responseArray.staff_info)


                    } else {
                        Toast.makeText(activity, "No Staffs Found", Toast.LENGTH_SHORT).show()
                    }
                } else if (response.body()!!.status == 116) {
                    if (apiCallDetail != 4) {
                        apiCallDetail = apiCallDetail + 1
                        AccessTokenClass.getAccessToken(mContext)
                        callStaffListApi(studentID)
                    } else {

                        showSuccessAlertnew(
                            mContext,
                            "Something went wrong.Please try again later",
                            "Alert"
                        )
                    }
                } else {
                    if (response.body()!!.status == 103) {
                        //validation check error
                    } else {
                        //check status code checks
                        InternetCheckClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                }
            }
        })
    }


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
        val studentAdapter = StudentListAdapter(mContext, mStudentList)
        studentListRecycler.adapter = studentAdapter
        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                studentName = studentListArrayList.get(position).name
                studentImg = studentListArrayList.get(position).photo
                studentId = studentListArrayList.get(position).id
                studentNameTV.text = studentName
                if (!studentImg.equals("")) {
                    Glide.with(mContext) //1
                        .load(studentImg)
                        .placeholder(R.drawable.student)
                        .error(R.drawable.student)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(selectStudentImgView)
                } else {
                    selectStudentImgView.setImageResource(R.drawable.student)
                }
                staffRelative.visibility = View.VISIBLE
                var internetCheck = InternetCheckClass.isInternetAvailable(mContext)
                if (internetCheck) {
                    callStaffListApi(studentId)
                } else {
                    InternetCheckClass.showSuccessInternetAlert(com.mobatia.kingsedu.fragment.home.mContext)
                }

                // callStudentInfoApi()
                //  Toast.makeText(activity, mStudentList.get(position).name, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        })
        dialog.show()
    }

    fun showStaffList(context: Context, mStaffList: ArrayList<StaffInfoDetail>) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_student_list)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var studentListRecycler = dialog.findViewById(R.id.studentListRecycler) as RecyclerView
        iconImageView.setImageResource(R.drawable.staff)
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
        val staffAdapter = StaffListAdapter(mStaffList, mContext)
        studentListRecycler.adapter = staffAdapter
        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        studentListRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // Your logic
                dialog.dismiss()
                staffName = mStaffList.get(position).name
                staffrole = mStaffList.get(position).role
                staffImg = mStaffList.get(position).staff_photo
                staffId = mStaffList.get(position).id
                staffEmail = mStaffList.get(position).email
                staffNameTV.text = staffName
                staffroleTV.text = staffrole
                if (!staffImg.equals("")) {
                    Glide.with(mContext) //1
                        .load(staffImg)
                        .placeholder(R.drawable.staff)
                        .error(R.drawable.staff)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(selectStaffImgView)
                    dialog.dismiss()
                } else {
                    selectStaffImgView.setImageResource(R.drawable.staff)
                    dialog.dismiss()
                }
                contactStaffBtn.visibility = View.VISIBLE
                dialog.dismiss()
            }
        })
        dialog.show()
    }


    fun callSendEmailToStaffApi(
        title: String,
        message: String,
        studentID: String,
        staffEmail: String,
        dialog: Dialog,
        progressDialog: RelativeLayout
    ) {
        val token = sharedprefs.getaccesstoken(mContext)
        val sendMailBody = SendStaffMailApiModel(studentID, staffEmail, title, message)
        val call: Call<ResponseBody> =
            ApiClient.getClient.sendStaffMail(sendMailBody, "Bearer " + token)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialog.visibility = View.GONE
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val responsedata = response.body()
                progressDialog.visibility = View.GONE
                Log.e("Response Signup", responsedata.toString())
                if (responsedata != null) {
                    try {

                        val jsonObject = JSONObject(responsedata.string())
                        if (jsonObject.has(jsonConstans.STATUS)) {
                            val status: Int = jsonObject.optInt(jsonConstans.STATUS)
                            Log.e("STATUS LOGIN", status.toString())
                            if (status == 100) {
                                showSuccessAlert(
                                    mContext,
                                    "Successfully submitted your leave request.",
                                    "Success",
                                    dialog
                                )
                                //dialog.dismiss()

                            } else {
                                if (status == 116) {
                                    //call Token Expired
                                    AccessTokenClass.getAccessToken(com.mobatia.kingsedu.fragment.home.mContext)
                                    callSendEmailToStaffApi(
                                        title,
                                        message,
                                        studentID,
                                        staffEmail,
                                        dialog,
                                        progressDialog
                                    )
                                } else {
                                    if (status == 103) {
                                        //validation check error
                                    } else {
                                        //check status code checks
                                        InternetCheckClass.checkApiStatusError(status, mContext)
                                    }
                                }

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }


    fun showSuccessAlert(context: Context, message: String, msgHead: String, mdialog: Dialog) {
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
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            mdialog.dismiss()
        }
        dialog.show()
    }

    fun showSuccessAlertnew(context: Context, message: String, msgHead: String) {
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
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }
}




