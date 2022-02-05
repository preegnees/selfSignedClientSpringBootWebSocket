package com.radmir.testClient.testClient

import com.neovisionaries.ws.client.HostnameUnverifiedException
import com.neovisionaries.ws.client.OpeningHandshakeException
import com.neovisionaries.ws.client.WebSocketException
import com.neovisionaries.ws.client.WebSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.ssl.SSLContexts
import org.springframework.stereotype.Component
import java.io.File
import javax.annotation.PostConstruct

@Component
class SSLClient()  {
    @PostConstruct
    fun start() {
        val factory = WebSocketFactory()
        val sslContext = SSLContexts.custom()
            .loadTrustMaterial(
                File("my-https.jks"), "secret".toCharArray(), // secret это ключ, который указывали при создании сертификата
                TrustSelfSignedStrategy()
            )
            .build()

        factory.sslContext = sslContext
        val ws = factory.createSocket("wss://localhost:8080/test")

        try {
            // Connect to the server and perform an opening handshake.
            // This method blocks until the opening handshake is finished.
            println(ws.connect())
            ws.sendText("привет")
        } catch (e: OpeningHandshakeException) {
            // A violation against the WebSocket protocol was detected
            // during the opening handshake.
        } catch (e: HostnameUnverifiedException) {
            // The certificate of the peer does not match the expected hostname.
        } catch (e: WebSocketException) {
            // Failed to establish a WebSocket connection.
        }
    }
}