package com.example.nakshatratak

import android.Manifest
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.nakshatratak.utility.NotificationUtil
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.*
import io.agora.rtc2.RtcEngineConfig
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


class VedioCallActivity : AppCompatActivity() {

    private val TAG = "SendNotificationActivity"
    private lateinit var remoteView: FrameLayout
    private lateinit var joinButton: Button
    private lateinit var localView: FrameLayout

    private lateinit var text:TextView

    private val appId = "4a6a09e91c0e4c488512b3f1cd7ff880"
    private val channelName = "testchannel"
    private var token = "007eJxTYOh8q9ww2TlmX4rWXluxZSGvD376nvmO1W3yVVe/aRJxNpcVGAwNLdMsLFKNzMyMTE2SjFKSDA1MLE1NzQ0MzRLNTcxM1p7amNYQyMiwcRkzCyMDBIL43AwlqcUlyRmJeXmpOQwMADK0Igw="
    var appCertificate = "4a6a09e91c0e4c488512b3f1cd7ff880"
    var expirationTimeInSeconds = 3600

//    private val token =
//        "007eJxTYDDp3SWzb5uimNhB/V+mL5lvLmFi/xjmUq06+8V/76bHa+YoMJimJVkamZlaWiamJpkkJRpbGiYbWRqZmCcnGyeZpZkbKnYuTG4IZGRgnyjPxMgAgSA+D0NBYkFiZWJyfkpqUTEDAwAi+yGx"


    private val uid = 0
    private var isJoined = false
    private var agoraEngine: RtcEngine? = null
    private var localSurfaceView: SurfaceView? = null
    private var remoteSurfaceView: SurfaceView? = null
    private val PERMISSION_REQ_ID = 22
    private val REQUESTED_PERMISSIONS = arrayOf<String>(
Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )





    private fun checkSelfPermission(): Boolean {
        return !(ContextCompat.checkSelfPermission(
            this,
            REQUESTED_PERMISSIONS[0]
        ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    this,
                    REQUESTED_PERMISSIONS[1]
                ) != PackageManager.PERMISSION_GRANTED)
    }

    fun showMessage(message: String?) {
        runOnUiThread {
            Toast.makeText(
                applicationContext,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun setupVideoSDKEngine() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            agoraEngine = RtcEngine.create(config)
            // By default, the video module is disabled, call enableVideo to enable it.
            agoraEngine!!.enableVideo()
        } catch (e: Exception) {
            showMessage(e.toString())
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedio_call)

        remoteView = findViewById(R.id.remote_video_view_container)
        joinButton = findViewById(R.id.btn_start_call)
        localView = findViewById(R.id.local_video_view_container)
        text= findViewById<TextView>(R.id.waiting)

        if (!checkSelfPermission()) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        setupVideoSDKEngine();
    }



    override fun onDestroy() {
        super.onDestroy()
        agoraEngine!!.stopPreview()
        agoraEngine!!.leaveChannel()

        Thread {
            RtcEngine.destroy()
            agoraEngine = null
        }.start()
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
            showMessage("Remote user joined $uid")

            // Set the remote video view
            runOnUiThread { setupRemoteVideo(uid)
            text.visibility= View.GONE}
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            isJoined = true
            showMessage("Joined Channel $channel")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            showMessage("Remote user offline $uid $reason")
            runOnUiThread { remoteSurfaceView!!.visibility = View.GONE }
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        remoteSurfaceView = SurfaceView(baseContext)
        remoteSurfaceView!!.setZOrderMediaOverlay(true)
        remoteView.addView(remoteSurfaceView)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        remoteSurfaceView!!.visibility = View.VISIBLE
    }

    private fun setupLocalVideo() {
        localSurfaceView = SurfaceView(baseContext)
        localView.addView(localSurfaceView)
        agoraEngine!!.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }


    fun joinChannel(view: View) {

//        if (checkSelfPermission()) {
//            val options = ChannelMediaOptions()
//
//            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION
//            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
//            setupLocalVideo()
//            localSurfaceView!!.visibility = View.VISIBLE
//
//            agoraEngine!!.startPreview()
//            agoraEngine!!.joinChannel(token, channelName, uid, options)
//            joinButton.visibility=View.GONE
//            text.visibility= View.VISIBLE

        sendNotification("dqJz-R6fQNy_sAKhoqDNc3:APA91bEprKlLkJsbjvumVZlFKWwMXoy-Pkh4RbB74vg2UJdE0FVKfKwRj-_ZeX2U0f3u2mXKFVIj7pZS2ntg_MqbqEe6vFvqoQNdo8DI7Dy5c-WSPBL1N5vrN61i5Rb21MJqZk4mGYIq", "Join Video Call", "You have an invitation to join a video call.")

//        } else {
//            Toast.makeText(applicationContext, "Permissions was not granted", Toast.LENGTH_SHORT)
//                .show()
//        }
    }

    fun leaveChannel(view: View) {
        if (!isJoined) {
            showMessage("Join a channel first")
            finish()
        } else {
            agoraEngine!!.leaveChannel()
            showMessage("You left the channel")
            if (remoteSurfaceView != null) remoteSurfaceView!!.visibility = View.GONE
            if (localSurfaceView != null) localSurfaceView!!.visibility = View.GONE
            isJoined = false
            finish()
        }

    }

    private fun sendNotification(token: String, title: String, body: String) {

        NotificationUtil.sendNotification(
            token = token,
            title = title,
            body = body,
            targetActivity = "com.example.nakshatratakastrologer.VedioCallActivity"
        )


    }

}





