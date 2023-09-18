package com.vasseurr.chatApp.socket;


import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.vasseurr.chatApp.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SocketModule {

    private static final Logger logger = LogManager.getLogger(SocketModule.class);
    private final SocketIOServer socketIOServer;

    private static final String ROOM = "room";

    public SocketModule(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", Message.class, onMessageReceived());
    }

    private DataListener<Message> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            logger.info("{} send a message -> {}", senderClient.getSessionId(), data.getContent());
            String room = senderClient.getHandshakeData().getSingleUrlParam(ROOM);
            senderClient.getNamespace().getRoomOperations(room).getClients().forEach(
                    socketIOClient -> {
                        //preventing user receive own message
                        if(!socketIOClient.getSessionId().equals(senderClient.getSessionId())) {
                            socketIOClient.sendEvent("get_message", data);
                        }
                    }
            );
            //senderClient.getNamespace().getBroadcastOperations().sendEvent("get_message", data.getContent());
        };
    }

    private ConnectListener onConnected() {
        return socketIOClient -> {
            String room = socketIOClient.getHandshakeData().getSingleUrlParam(ROOM);
            socketIOClient.joinRoom(room);
            socketIOClient.getNamespace().getRoomOperations(room)
                    .sendEvent("get_message", String.format("%s connected to -> %s",
                            socketIOClient.getSessionId(), room));
            logger.info("SocketId: {} connected", socketIOClient.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
        return socketIOClient -> {
            String room = socketIOClient.getHandshakeData().getSingleUrlParam(ROOM);
            socketIOClient.getNamespace().getRoomOperations(room)
                    .sendEvent("get_message", String.format("%s disconnected to -> %s",
                            socketIOClient.getSessionId(), room));
            logger.info("SocketId: {} disconnected", socketIOClient.getSessionId().toString());
        };
    }
}
