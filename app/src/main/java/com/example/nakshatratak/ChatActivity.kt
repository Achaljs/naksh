package com.example.nakshatratak

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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
import io.agora.chat.CmdMessageBody
import io.agora.chat.TextMessageBody


data class Message(val content: String, val isSent: Boolean)

class ChatActivity : AppCompatActivity() {

    private lateinit var agoraChatClient: ChatClient
    private lateinit var recyclerViewMessages: RecyclerView
    private lateinit var messagesAdapter: MessageAdapter
    private var messagesList: MutableList<Message> = mutableListOf()

    private var isJoined = false
    private lateinit var progressBar: ProgressBar
    private lateinit var typingIndicator: TextView
    private var typingHandler = Handler(Looper.getMainLooper())
    private var typingRunnable = Runnable {
        typingIndicator.visibility = View.GONE
    }
    var editMessage: EditText? = null

    private val appKey = "611107516#1374384"
    private val token = "007eJxTYDj9c5LAzG0KGn/+Xbuf5Xvl+TKf9i0zc3z3yFYcMTu19YucAoOhoWWahUWqkZmZkalJklFKkqGBiaWpqbmBoVmiuYmZienmDWkNgYwMMX8SWRkZWBkYgRDEV2EwSjWwTE1MNNA1STFP1jU0TE3TtbC0NNc1MTdPTUpKNDM1N0oGAIDEKZk="
    private val userId = "user1" // Replace with a unique user ID

    private fun showLog(text: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
        Log.d("AgoraChatQuickStart", text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        setupChatClient() // Initialize the ChatClient
        setupListeners() // Add event listeners

        progressBar = findViewById(R.id.progressBar)
        typingIndicator = findViewById(R.id.typingIndicator)
        editMessage = findViewById(R.id.etMessageText)

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages)
        messagesAdapter = MessageAdapter(messagesList)
        recyclerViewMessages.layoutManager = LinearLayoutManager(this)
        recyclerViewMessages.adapter = messagesAdapter

        editMessage?.setOnKeyListener { _, _, _ ->
            sendTypingIndicator()
            false
        }
    }

    private fun setupChatClient() {
        val options = ChatOptions()
        if (appKey.isEmpty()) {
            showLog("You need to set your AppKey")
            return
        }
        options.appKey = appKey
        agoraChatClient = ChatClient.getInstance()
        agoraChatClient.init(this, options)
        agoraChatClient.setDebugMode(true)
    }

    private fun setupListeners() {
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

            override fun onCmdMessageReceived(messages: MutableList<ChatMessage>?) {
                if (messages != null) {
                    for (message in messages) {
                        if (message.type == ChatMessage.Type.CMD) {
                            val body = message.body as CmdMessageBody
                            if (body.action() == "typing") {
                                runOnUiThread {
                                    typingIndicator.visibility = View.VISIBLE
                                    typingHandler.removeCallbacks(typingRunnable)
                                    typingHandler.postDelayed(typingRunnable, 3000) // Hide after 3 seconds of inactivity
                                }
                            }
                        }
                    }
                }
            }
        })

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

            override fun onTokenExpired() {}
            override fun onTokenWillExpire() {}
        })
    }

    fun joinLeave(view: View) {
        val button: Button = findViewById(R.id.btnJoinLeave)
        progressBar.visibility = View.VISIBLE
        button.visibility = View.GONE
        if (isJoined) {
            agoraChatClient.logout(true, object : CallBack {
                override fun onSuccess() {
                    showLog("Sign out success!")
                    runOnUiThread {
                        button.text = "Join"
                        progressBar.visibility = View.GONE
                        button.visibility = View.GONE
                    }
                    isJoined = false
                }

                override fun onError(code: Int, error: String) {
                    showLog(error)
                    runOnUiThread {
                        progressBar.visibility = View.GONE
                    }
                }
            })
        } else {
            agoraChatClient.loginWithAgoraToken(userId, token, object : CallBack {
                override fun onSuccess() {
                    showLog("Signed in")
                    isJoined = true
                    runOnUiThread {
                        button.text = "Leave"
                        progressBar.visibility = View.GONE
                        button.visibility = View.GONE
                    }
                }

                override fun onError(code: Int, error: String) {
                    if (code == 200) { // Already joined
                        isJoined = true
                        runOnUiThread {
                            button.text = "Leave"
                            progressBar.visibility = View.GONE
                        }
                    } else {
                        showLog(error)
                        runOnUiThread {
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    fun sendMessage(view: View) {
        val toSendName = "user1"
        val content = editMessage?.text.toString().trim()

        if (toSendName.isEmpty() || content.isEmpty()) {
            showLog("Enter a recipient name and a message")
            return
        }

        val message = ChatMessage.createTextSendMessage(content, toSendName)

        message.setMessageStatusCallback(object : CallBack {
            override fun onSuccess() {
                showLog("Message sent")
                runOnUiThread {
                    displayMessage(content, true)
                    editMessage?.setText("")
                    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(editMessage?.windowToken, 0)
                }
            }

            override fun onError(code: Int, error: String) {
                showLog(error)
            }
        })

        agoraChatClient.chatManager().sendMessage(message)
    }

    private fun sendTypingIndicator() {
        val toSendName = "user2"
        if (toSendName.isEmpty()) return

        val typingCmdMessage = ChatMessage.createSendMessage(ChatMessage.Type.CMD)
        typingCmdMessage.to = toSendName
        typingCmdMessage.body = CmdMessageBody("typing......")

        agoraChatClient.chatManager().sendMessage(typingCmdMessage)
    }

    fun displayMessage(messageText: String, isSentMessage: Boolean) {
        val message = Message(messageText, isSentMessage)
        messagesList.add(message)
        messagesAdapter.notifyItemInserted(messagesList.size - 1)
        recyclerViewMessages.scrollToPosition(messagesList.size - 1)
    }


    override fun onDestroy() {
        super.onDestroy()
        agoraChatClient.chatManager().removeMessageListener {  }
        agoraChatClient.removeLogListener {  }
        typingHandler.removeCallbacks(typingRunnable)
        if (isJoined) {
            agoraChatClient.logout(true, object : CallBack {
                override fun onSuccess() {
                    showLog("Signed out onDestroy")
                }

                override fun onError(code: Int, error: String) {
                    showLog("Error on sign out onDestroy: $error")
                }
            })
        }
    }


}
