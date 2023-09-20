package com.vasseurr.chatApp.socket;


import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.vasseurr.chatApp.dto.MessageDto;
import com.vasseurr.chatApp.service.MessageService;
import com.vasseurr.chatApp.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SocketModule {

    private static final Logger logger = LogManager.getLogger(SocketModule.class);

    private final SocketIOServer socketIOServer;
  //  private RoomService roomService;
    private MessageService messageService;

    private static final String SEND_EVENT = "send_message";
    private static final String LISTEN_EVENT = "get_message";
    private static final String ROOM = "room";

    public SocketModule(SocketIOServer socketIOServer, RoomService roomService, MessageService messageService) {
        this.socketIOServer = socketIOServer;
    //    this.roomService = roomService;
        this.messageService = messageService;
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener(SEND_EVENT, MessageDto.class, onMessageReceived());
        //todo: add event for connect room
    }

    private DataListener<MessageDto> onMessageReceived() {
        return (senderClient, data, ackSender) -> {
            logger.info("{} send a message to {} -> {}", data.getSender().getUserName(),
                    data.getReceiver().getUserName(), data.getContent());
            MessageDto messageDto = messageService.save(data);
            String room = senderClient.getHandshakeData().getSingleUrlParam(ROOM);
            senderClient.getNamespace().getRoomOperations(room).getClients().forEach(
                    socketIOClient -> {
                        //preventing user receive own message
                       // if(!socketIOClient.getSessionId().equals(senderClient.getSessionId())) {
                            socketIOClient.sendEvent(LISTEN_EVENT, messageDto);
                      //  }
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
                    .sendEvent(LISTEN_EVENT, String.format("%s connected to -> %s",
                            socketIOClient.getSessionId(), room));
            logger.info("SocketId: {} connected", socketIOClient.getSessionId().toString());
        };
    }

    private DisconnectListener onDisconnected() {
        return socketIOClient -> {
            String room = socketIOClient.getHandshakeData().getSingleUrlParam(ROOM);
            socketIOClient.getNamespace().getRoomOperations(room)
                    .sendEvent(LISTEN_EVENT, String.format("%s disconnected to -> %s",
                            socketIOClient.getSessionId(), room));
            logger.info("SocketId: {} disconnected", socketIOClient.getSessionId().toString());
        };
    }
}
