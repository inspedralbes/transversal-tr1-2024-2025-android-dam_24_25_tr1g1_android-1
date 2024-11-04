package com.example.myapplication

import android.os.Bundle
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

object SocketManager {
    var socket: Socket? = null
    var connected=false
    init {
        socket = IO.socket("http://dam.inspedralbes.cat:26968")
        println("Socket initialized")
    }

    fun onCreate(savedInstanceState: Bundle?) {
        println(socket)
    }
    fun connectSocket() {
        socket?.connect()
        println("Socket connected")
        connected=true
    }
    fun disconnectSocket() {
        socket?.disconnect()
        println("Socket disconnected")
        connected=false
    }
}