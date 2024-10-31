package com.example.myapplication

import android.os.Bundle
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter

object SocketManager {
    var socket: Socket? = null

    init {
        socket = IO.socket("http://dam.inspedralbes.cat:26968")
        println("Socket initialized")
    }

    fun onCreate(savedInstanceState: Bundle?) {
        socket?.connect();
        println(socket)
    }
}