package com.example.nakshatratak

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nakshatratak.recyclerviews.MessageAdapter
import io.agora.rtm.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private lateinit var rtmClient: RtmClient
    private lateinit var rtmChannel: RtmChannel
    private lateinit var messageAdapter: MessageAdapter
    private val messages: MutableList<String> = mutableListOf()
    private val appId = "2e6ee38668cb468a8cc3650900073607"
    private val token = "YOUR_TEMP_TOKEN"
    private val channelName = "testChannel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initUI()
        initializeAgoraRtm()
        joinChannel()
    }

    private fun initUI() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMessages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        messageAdapter = MessageAdapter(messages)
        recyclerView.adapter = messageAdapter

        val buttonSend: Button = findViewById(R.id.buttonSend)
        val editTextMessage: EditText = findViewById(R.id.editTextMessage)

        buttonSend.setOnClickListener {
            val message = editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message)
                editTextMessage.text.clear()
            }
        }
    }

    private fun initializeAgoraRtm() {
        try {
            rtmClient = RtmClient.createInstance(this, appId, object : RtmClientListener {
                override fun onConnectionStateChanged(state: Int, reason: Int) {
                    Log.d("RTM", "Connection state changed: $state, Reason: $reason")
                }

                override fun onMessageReceived(message: RtmMessage, peerId: String) {
                    runOnUiThread {
                        messages.add("Received from $peerId: ${message.text}")
                        messageAdapter.notifyDataSetChanged()
                    }
                }

                override fun onImageMessageReceivedFromPeer(p0: RtmImageMessage?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onFileMessageReceivedFromPeer(p0: RtmFileMessage?, p1: String?) {
                    TODO("Not yet implemented")
                }

                override fun onMediaUploadingProgress(p0: RtmMediaOperationProgress?, p1: Long) {
                    TODO("Not yet implemented")
                }

                override fun onMediaDownloadingProgress(p0: RtmMediaOperationProgress?, p1: Long) {
                    TODO("Not yet implemented")
                }

                override fun onTokenExpired() {
                    Log.d("RTM", "Token expired")
                }

                override fun onPeersOnlineStatusChanged(status: MutableMap<String, Int>?) {
                    Log.d("RTM", "Peers online status changed: $status")
                }

                override fun onTokenPrivilegeWillExpire() {
                    Log.d("RTM", "Token privilege will expire soon")
                }
            })
        } catch (e: Exception) {
            throw RuntimeException("RTM SDK init failed: ${Log.getStackTraceString(e)}")
        }
    }

    private fun joinChannel() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                rtmChannel = rtmClient.createChannel(channelName, object : RtmChannelListener {
                    override fun onMemberCountUpdated(count: Int) {}
                    override fun onAttributesUpdated(attribute: List<RtmChannelAttribute>?) {}
                    override fun onMessageReceived(message: RtmMessage, member: RtmChannelMember) {
                        runOnUiThread {
                            messages.add("Received from ${member.userId}: ${message.text}")
                            messageAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onImageMessageReceived(
                        p0: RtmImageMessage?,
                        p1: RtmChannelMember?
                    ) {
                        TODO("Not yet implemented")
                    }

                    override fun onFileMessageReceived(p0: RtmFileMessage?, p1: RtmChannelMember?) {
                        TODO("Not yet implemented")
                    }

                    override fun onMemberJoined(member: RtmChannelMember) {
                        Log.d("RTM", "${member.userId} joined the channel")
                    }

                    override fun onMemberLeft(member: RtmChannelMember) {
                        Log.d("RTM", "${member.userId} left the channel")
                    }
                })

                rtmChannel.join(object : ResultCallback<Void> {
                    override fun onSuccess(responseInfo: Void?) {
                        Log.d("RTM", "Joined channel successfully")
                    }

                    override fun onFailure(errorInfo: ErrorInfo) {
                        Log.d("RTM", "Failed to join channel: ${errorInfo.errorCode}")
                    }
                })
            } catch (e: Exception) {
                Log.e("RTM", "Error joining channel: ${e.message}")
            }
        }
    }

    private fun sendMessage(message: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val rtmMessage = rtmClient.createMessage()
            rtmMessage.text = message
            rtmChannel.sendMessage(rtmMessage, object : ResultCallback<Void> {
                override fun onSuccess(responseInfo: Void?) {
                    runOnUiThread {
                        messages.add("Sent: $message")
                        messageAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(errorInfo: ErrorInfo) {
                    Log.d("RTM", "Failed to send message: ${errorInfo.errorCode}")
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch(Dispatchers.IO) {
            rtmChannel.leave(null)
            rtmChannel.release()
            rtmClient.release()
        }
    }
}
