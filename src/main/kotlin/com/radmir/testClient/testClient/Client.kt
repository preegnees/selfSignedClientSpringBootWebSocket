package com.radmir.testClient.testClient

import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.ssl.SSLContexts
import org.java_websocket.client.DefaultSSLWebSocketClientFactory
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketHttpHeaders
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.client.WebSocketClient
import java.io.File
import java.io.IOException
import java.net.URI
import java.util.logging.Level
import java.util.logging.Logger
import javax.annotation.PostConstruct
import javax.websocket.*


@Component
class Client {
//    @PostConstruct
    fun start() {
        try {
            val container = ContainerProvider.getWebSocketContainer()

            val uri = "ws://localhost:8080/test"
            println("Connecting to $uri")

            container.connectToServer(WebSocketClientEndpoint::class.java, URI.create(uri))

        } catch (ex: DeploymentException) {
            Logger.getLogger(Client::class.java.name).log(Level.SEVERE, null, ex)
        } catch (ex: InterruptedException) {
            Logger.getLogger(Client::class.java.name).log(Level.SEVERE, null, ex)
        } catch (ex: IOException) {
            Logger.getLogger(Client::class.java.name).log(Level.SEVERE, null, ex)
        }
    }
}

@ClientEndpoint
class WebSocketClientEndpoint(): WebSocketClient {
    var userSession: Session? = null
    private var messageHandler: MessageHandler? = null

    @OnOpen
    fun onOpen(userSession: Session?) {
        println("opening websocket")
        this.userSession = userSession
    }

    @OnClose
    fun onClose(userSession: Session?, reason: CloseReason?) {
        println("closing websocket")
        this.userSession = null
    }

    @OnMessage
    fun onMessage(message: String?) {
        if (messageHandler != null) {
            messageHandler!!.handleMessage(message)
        }
    }

    fun addMessageHandler(msgHandler: MessageHandler?) {
        messageHandler = msgHandler
    }

    fun sendMessage(message: String?) {
        userSession!!.asyncRemote.sendText(message)
    }


    interface MessageHandler {
        fun handleMessage(message: String?)
    }

    override fun doHandshake(p0: WebSocketHandler, p1: String, vararg p2: Any?): ListenableFuture<WebSocketSession> {
        TODO("Not yet implemented")
    }

    override fun doHandshake(
        p0: WebSocketHandler,
        p1: WebSocketHttpHeaders?,
        p2: URI
    ): ListenableFuture<WebSocketSession> {
        TODO("Not yet implemented")
    }

//    init {
//        try {
//            val container = ContainerProvider.getWebSocketContainer()
//            container.connectToServer(this, endpointURI)
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
//    }
}