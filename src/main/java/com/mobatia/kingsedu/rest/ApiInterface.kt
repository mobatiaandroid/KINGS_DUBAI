package com.mobatia.kingsedu.rest

import com.mobatia.kingsedu.activity.absence.model.RequestAbsenceApiModel
import com.mobatia.kingsedu.activity.common.model.DeviceRegModel
import com.mobatia.kingsedu.activity.communication.letter.model.LetterResponseModel
import com.mobatia.kingsedu.activity.communication.magazine.model.MagazineResponseModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterDetailApiModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterDetailModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterListAPiModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterListModel
import com.mobatia.kingsedu.activity.home.model.DataCollectionSubmissionModel
import com.mobatia.kingsedu.activity.message.model.MessageDetailApiModel
import com.mobatia.kingsedu.activity.message.model.MessageDetailModel
import com.mobatia.kingsedu.activity.settings.termsofservice.model.TermsOfServiceModel
import com.mobatia.kingsedu.activity.term_dates.model.TermDatesDetailApiModel
import com.mobatia.kingsedu.activity.term_dates.model.TermDatesDetailModel
import com.mobatia.kingsedu.fragment.apps.model.AppsApiModel
import com.mobatia.kingsedu.fragment.apps.model.AppsListModel
import com.mobatia.kingsedu.fragment.attendance.model.AttendanceApiModel
import com.mobatia.kingsedu.fragment.attendance.model.AttendanceListModel
import com.mobatia.kingsedu.fragment.calendar.model.CalendarApiModel
import com.mobatia.kingsedu.fragment.calendar.model.CalendarListModel
import com.mobatia.kingsedu.fragment.calendar_new.model.CalendarModel
import com.mobatia.kingsedu.fragment.contact_us.model.ContactusModel
import com.mobatia.kingsedu.fragment.curriculum.model.CuriculumListModel
import com.mobatia.kingsedu.fragment.curriculum.model.CurriculumStudentApiModel
import com.mobatia.kingsedu.fragment.forms.model.FormsResponseModel
import com.mobatia.kingsedu.fragment.home.model.BannerModel
import com.mobatia.kingsedu.fragment.home.model.CountryListModel
import com.mobatia.kingsedu.fragment.home.model.RelationShipListModel
import com.mobatia.kingsedu.fragment.home.model.TilesListModel
import com.mobatia.kingsedu.fragment.home.model.datacollection.DataCollectionModel
import com.mobatia.kingsedu.fragment.messages.model.MessageListApiModel
import com.mobatia.kingsedu.fragment.messages.model.MessageListModel
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceLeaveApiModel
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceListModel
import com.mobatia.kingsedu.fragment.reports.model.ReportApiModel
import com.mobatia.kingsedu.fragment.reports.model.ReportListModel
import com.mobatia.kingsedu.fragment.settings.model.ChangePasswordApiModel
import com.mobatia.kingsedu.fragment.settings.model.TriggerUSer
import com.mobatia.kingsedu.fragment.socialmedia.model.SocialMediaListModel
import com.mobatia.kingsedu.fragment.student_information.model.StudentInfoApiModel
import com.mobatia.kingsedu.fragment.student_information.model.StudentInfoModel
import com.mobatia.kingsedu.fragment.student_information.model.StudentListModel
import com.mobatia.kingsedu.fragment.teacher_contact.model.SendStaffMailApiModel
import com.mobatia.kingsedu.fragment.teacher_contact.model.StaffListApiModel
import com.mobatia.kingsedu.fragment.teacher_contact.model.StaffListModel
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesApiModel
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesListModel
import com.mobatia.kingsedu.fragment.time_table.model.apimodel.TimeTableApiDataModel
import com.mobatia.kingsedu.fragment.time_table.model.apimodel.TimeTableApiListModel
import com.mobatia.kingsedu.fragment.time_table.model.apimodel.TimeTableApiModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
  /*************HOME_BANNER_IMAGE****************/
    @POST("api/v1/banner_images")
    @Headers("Content-Type: application/json")
    fun bannerimages(
        @Body  bannerModel: BannerModel,
        @Header("Authorization") token:String
    ):
     Call<ResponseBody>
    /*************SETTINGS USER DETAIL****************/
    @POST("api/v1/settings_userdetails")
    @Headers("Content-Type: application/json")
    fun settingsUserDetail(
        @Body  bannerModel: BannerModel,
        @Header("Authorization") token:String
    ):
     Call<ResponseBody>
    /*************COMMUNICATION_BANNER_IMAGE****************/
    @POST("api/v1/communication/banner_images")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun communication(
        @Header("Authorization") token:String
    ):
     Call<ResponseBody>

    /*************ACCESS TOKEN ****************/
    @POST("api/v1/user/token")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun access_token(
        @Header("authorization-user") usercode:String?
    ):
     Call<ResponseBody>

    /*************SIGN UP****************/
    @POST("api/v1/parent_signup")
    @FormUrlEncoded
    fun signup(
        @Field("email") email: String,
        @Field("devicetype") devicetype: Int,
        @Field("deviceid") deviceid: String
    ): Call<ResponseBody>
    /*************Forget Password****************/
    @POST("api/v1/forgot_password")
    @FormUrlEncoded
    fun forgetPassword(
        @Field("email") email: String
    ): Call<ResponseBody>

    /*************LOGIN****************/
    @POST("api/v1/login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("devicetype") devicetype: Int,
        @Field("deviceid") deviceid: String,
        @Field("fcm_id") fcmid: String
    ): Call<ResponseBody>

    /*************STUDENT_LIST****************/
    @POST("api/v1/student/list")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun studentList(
        @Header("Authorization") token:String
    ): Call<StudentListModel>
 /*************STAFF_LIST****************/
    @POST("api/v1/staff/list")
    @Headers("Content-Type: application/json")
    fun staffList(
     @Body  staffBody: StaffListApiModel,
     @Header("Authorization") token:String
    ): Call<StaffListModel>

    /*************STUDENT_INFO****************/
    @POST("api/v1/student/info")
    @Headers("Content-Type: application/json")
    fun studentInfo(
        @Body  studentbody: StudentInfoApiModel,
        @Header("Authorization") token:String
    ): Call<StudentInfoModel>

    /*************NOTIFICATION_LIST****************/
    @POST("api/v1/notification/list")
    @Headers("Content-Type: application/json")
    fun notificationList(
        @Body  messageListApiModel: MessageListApiModel,
        @Header("Authorization") token:String
    ): Call<MessageListModel>

      /*************Calendar List****************/
    @POST("api/v1/calendar")
    @Headers("Content-Type: application/json")
    fun calendarList(): Call<CalendarModel>
    /*************ABSENCE List****************/
    @POST("api/v1/getcalendar_detail")
    @Headers("Content-Type: application/json")
    fun calendarDetail(
        @Body  calendarApi: CalendarApiModel,
        @Header("Authorization") token:String
    ): Call<CalendarListModel>
    /*************ABSENCE List****************/
    @POST("api/v1/leave/request")
    @Headers("Content-Type: application/json")
    fun absenceList(
        @Body  studentInfoModel: AbsenceLeaveApiModel,
        @Header("Authorization") token:String
    ): Call<AbsenceListModel>

    @POST("api/v1/social_media")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun socialMedia(
        @Header("Authorization") token:String
    ): Call<SocialMediaListModel>

/*Leave Request*/
   @POST("api/v1/request/leave")
   @Headers("Content-Type: application/json")
    fun leaveRequest(
    @Body  requestLeave: RequestAbsenceApiModel,
    @Header("Authorization") token:String
    ): Call<ResponseBody>

   /*SEND EMAIL TO STAFF*/
   @POST("api/v1/sendemail")
   @Headers("Content-Type: application/json")
    fun sendStaffMail(
    @Body  sendMail: SendStaffMailApiModel,
    @Header("Authorization") token:String
    ): Call<ResponseBody>

    /*************TERM_DATES LIST****************/
    @POST("api/v1/termdates")
    @Headers("Content-Type: application/json")
    fun termdates(
        @Header("Authorization") token:String
    ): Call<TermDatesDetailModel>

    @POST("api/v1/terms_of_service")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun termsOfService(
        @Header("Authorization") token:String
    ): Call<TermsOfServiceModel>


    @POST("api/v1/parent/logout")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun logout(
        @Header("Authorization") token:String
    ): Call<ResponseBody>

    /*CHANGE PASSWORD*/
    @POST("api/v1/changepassword")
    @Headers("Content-Type: application/json")
    fun changePassword(
        @Body  changePassword: ChangePasswordApiModel,
        @Header("Authorization") token:String
    ): Call<ResponseBody>

    /*************APPS LIST****************/
    @POST("api/v1/apps")
    @Headers("Content-Type: application/json")
    fun appsList(
        @Body  appsList: AppsApiModel,
        @Header("Authorization") token:String
    ): Call<AppsListModel>

    /*************TERM_DATES DETAIL****************/
    @POST("api/v1/termdate/details")
    @Headers("Content-Type: application/json")
    fun termDatesDetails(
        @Body  termDates: TermDatesDetailApiModel,
        @Header("Authorization") token:String
    ): Call<TermDatesDetailModel>


    /*************NEWSLETTER List****************/
    @POST("api/v1/newsletters")
    @Headers("Content-Type: application/json")
    fun newsletters(
        @Header("Authorization") token:String
    ): Call<NewsLetterListModel>

    /*************NEWSLETTER DETAIL****************/
    @POST("api/v1/newsletters/details")
    @Headers("Content-Type: application/json")
    fun newsletterdetail(
        @Body  newsLetterDetailApi: NewsLetterDetailApiModel,
        @Header("Authorization") token:String
    ): Call<NewsLetterDetailModel>

    /*************NOTIFICATION DETAIL****************/
    @POST("api/v1/notification/details")
    @Headers("Content-Type: application/json")
    fun notifictaionDetail(
        @Body  newsLetterDetailApi: MessageDetailApiModel,
        @Header("Authorization") token:String
    ): Call<MessageDetailModel>

    /*************TIME TABLE DATA****************/
    @POST("api/v1/timetable")
    @Headers("Content-Type: application/json")
    fun timetable(
        @Body  timeTableApi: TimeTableApiModel,
        @Header("Authorization") token:String
    ): Call<TimeTableApiDataModel>

    /*************TITLES_LIST****************/
    @POST("api/v1/titles")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun titlesList(
        @Header("Authorization") token:String
    ): Call<TilesListModel>
 /*************COUNTRY_LIST****************/
    @POST("api/v1/countries")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun countryList(
        @Header("Authorization") token:String
    ): Call<CountryListModel>
 /*************RELATIONSHIP_LIST****************/
    @POST("api/v1/contact_types")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun relationshipList(
        @Header("Authorization") token:String
    ): Call<RelationShipListModel>

    /*************DATA_COLLECTION_DETAIL****************/
    @POST("api/v1/data_collection_details")
    @Headers("Content-Type: application/x-www-form-urlencode","Accept: application/json")
    fun dataCollectionDetail(
        @Header("Authorization") token:String
    ): Call<DataCollectionModel>
    /*************Contact Us****************/
    @POST("api/v1/contact_us")
    fun contactus(
        @Header("Authorization") token:String
    ): Call<ContactusModel>

    /*************Attendance List****************/
    @POST("api/v1/attendance_record")
    @Headers("Content-Type: application/json")
    fun attendanceList(
        @Body attendanceListModel: AttendanceApiModel,
        @Header("Authorization") token:String
    ): Call<AttendanceListModel>

    /*************Report List****************/
    @POST("api/v1/progressreport")
    @Headers("Content-Type: application/json")
    fun reportList(
        @Body reportListModel: ReportApiModel,
        @Header("Authorization") token:String
    ): Call<ReportListModel>
    /*Leave Request*/
    @POST("api/v1/submit_datacollection")
    @Headers("Content-Type: application/json")
    fun dataCollectionSubmittion(
        @Body  dataLeave: DataCollectionSubmissionModel,
        @Header("Authorization") token:String
    ): Call<ResponseBody>
 @POST("api/v1/trigger_user")
    @Headers("Content-Type: application/json")
    fun triggerUser(
        @Body  trigger: TriggerUSer,
        @Header("Authorization") token:String
    ): Call<ResponseBody>
    /*************Report List****************/
    @POST("api/v1/curriculm_guides")
    @Headers("Content-Type: application/json")
    fun curriculumList(
        @Body reportListModel: CurriculumStudentApiModel,
        @Header("Authorization") token:String
    ): Call<CuriculumListModel>

    /*************FORMS_LIST****************/
    @POST("api/v1/forms")
    @Headers("Content-Type: application/json")
    fun formslist(
        @Body  messageListApiModel: MessageListApiModel,
        @Header("Authorization") token:String
    ): Call<FormsResponseModel>


    /*************LOGIN****************/
    @POST("api/v1/device_registration")
    @Headers("Content-Type: application/json")
    fun deviceregistration(
        @Body  messageListApiModel: DeviceRegModel,
        @Header("Authorization") token:String
    ): Call<ResponseBody>


    /*************LETTER LIST****************/
    @POST("api/v1/letters")
    @Headers("Content-Type: application/json")
    fun letterList(
        @Body  messageListApiModel: MessageListApiModel,
        @Header("Authorization") token:String
    ): Call<LetterResponseModel>

    /*************LETTER LIST****************/
    @POST("api/v1/magazines")
    @Headers("Content-Type: application/json")
    fun magazineList(
        @Body  messageListApiModel: MessageListApiModel,
        @Header("Authorization") token:String
    ): Call<MagazineResponseModel>
}