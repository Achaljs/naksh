package com.example.nakshatratak

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nakshatratak.recyclerviews.MessageAdapter
import io.agora.CallBack
import io.agora.ConnectionListener
import io.agora.MessageListener
import io.agora.chat.ChatClient
import io.agora.chat.ChatMessage
import io.agora.chat.ChatOptions
import io.agora.chat.TextMessageBody
import java.util.List


class ChatActivity : AppCompatActivity() {

    private lateinit var agoraChatClient: ChatClient


    private var isJoined = false
    var editMessage: EditText? = null

    private val appKey = "611107516#1374384"
    private val token = "007eJxTYNi0sKt2QRbjzC3sC7/t/rq0XE1ee0Ia90GdDrHP66v5LoUoMBgaWqZZWKQamZkZmZokGaUkGRqYWJqamhsYmiWam5iZsLouT2sIZGTI+H2dgZGBFYgZGUB8FQajVAPL1MREA12TFPNkXUPD1DRdC0tLc10Tc/PUpKREM1Nzo2QAGxEmXg=="
    private val userId = "user1" // Replace with a unique user ID
    //private val channelName = "testChannel"

    private fun showLog(text: String) {

        runOnUiThread {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }

        // Write log
        Log.d("AgoraChatQuickStart", text)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setupChatClient() // Initialize the ChatClient
        setupListeners() // Add event listeners

        // Set up UI elements for code access
        editMessage = findViewById(R.id.etMessageText)
    }


    private fun setupChatClient() {
        val options = ChatOptions()
        if (appKey.isEmpty()) {
            showLog("You need to set your AppKey")
            return
        }
        options.appKey = appKey // Set your app key in options
        agoraChatClient = ChatClient.getInstance()
        agoraChatClient.init(this, options) // Initialize the ChatClient
        agoraChatClient.setDebugMode(true) // Enable debug info output
    }


//    private fun initUI() {
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMessages)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        messageAdapter = MessageAdapter(messages)
//        recyclerView.adapter = messageAdapter
//
//        val sendButton: Button = findViewById(R.id.buttonSend)
//        val messageEditText: EditText = findViewById(R.id.editTextMessage)
//
//        sendButton.setOnClickListener {
//            val messageText = messageEditText.text.toString()
//            if (messageText.isNotEmpty()) {
//                sendMessage(messageText)
//                messageEditText.text.clear()
//            }
//        }
//    }


    private fun setupListeners() {
        // Add message event callbacks
        agoraChatClient.chatManager().addMessageListener(object : MessageListener {


            override fun onMessageReceived(messages: MutableList<ChatMessage>?) {
                if (messages != null) {
                    for (message in messages) {
                        runOnUiThread {
                            displayMessage((message.body as TextMessageBody).message, false)
                        }
                        showLog("Received a ${message.type.name} message from ${message.from}")
                    }
                }
            }
        })

        // Add connection event callbacks
        agoraChatClient.addConnectionListener(object : ConnectionListener {
            override fun onConnected() {
                showLog("Connected")
            }

            override fun onDisconnected(error: Int) {
                if (isJoined) {
                    showLog("Disconnected: $error")
                    isJoined = false
                }
            }

            override fun onLogout(errorCode: Int) {
                showLog("User logging out: $errorCode")
            }

            override fun onTokenExpired() {
                // The token has expired
            }

            override fun onTokenWillExpire() {
                // The token is about to expire. Get a new token
                // from the token server and renew the token.
            }
        })
    }


    fun joinLeave(view: View) {
        val button: Button = findViewById(R.id.btnJoinLeave)

        if (isJoined) {
            agoraChatClient.logout(true, object : CallBack {
                override fun onSuccess() {
                    showLog("Sign out success!")
                    runOnUiThread {
                        button.text = "Join"
                    }
                    isJoined = false
                }

                override fun onError(code: Int, error: String) {
                    showLog(error)
                }
            })
        } else {
            agoraChatClient.loginWithAgoraToken(userId, token, object : CallBack {
                override fun onSuccess() {
                    showLog("Signed in")
                    isJoined = true
                    runOnUiThread {
                        button.text = "Leave"
                    }
                }

                override fun onError(code: Int, error: String) {
                    if (code == 200) { // Already joined
                        isJoined = true
                        runOnUiThread {
                            button.text = "Leave"
                        }
                    } else {
                        showLog(error)
                    }
                }
            })
        }
    }



    fun sendMessage(view: View) {
        // Read the recipient name from the EditText box
        val toSendName = findViewById<EditText>(R.id.etRecipient).text.toString().trim()
        val content = editMessage?.text.toString().trim()

        if (toSendName.isEmpty() || content.isEmpty()) {
            showLog("Enter a recipient name and a message")
            return
        }

        // Create a ChatMessage
        val message = ChatMessage.createTextSendMessage(content, toSendName)

        // Set the message callback before sending the message
        message.setMessageStatusCallback(object : CallBack {
            override fun onSuccess() {
                showLog("Message sent")
                runOnUiThread {
                    displayMessage(content, true)
                    // Clear the box and hide the keyboard after sending the message
                    editMessage?.setText("")
                    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(editMessage?.windowToken, 0)
                }
            }

            override fun onError(code: Int, error: String) {
                showLog(error)
            }
        })

        // Send the message
        agoraChatClient.chatManager().sendMessage(message)
    }

    fun displayMessage(messageText: String, isSentMessage: Boolean) {
        // Create a new TextView
        val messageTextView = TextView(this).apply {
            text = messageText
            setPadding(10, 10, 10, 10)
        }

        // Set formatting
        val messageList = findViewById<LinearLayout>(R.id.messageList)
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ).apply {
            if (isSentMessage) {
                gravity = Gravity.END
                messageTextView.setBackgroundColor(Color.parseColor("#DCF8C6"))
                setMargins(100, 25, 15, 5)
            } else {
                messageTextView.setBackgroundColor(Color.WHITE)
                setMargins(15, 25, 100, 5)
            }
        }

        // Add the message TextView to the LinearLayout
        messageList.addView(messageTextView, params)
    }



}














