import java.io.IOException;

import org.quickserver.net.server.ClientCommandHandler;
import org.quickserver.net.server.ClientHandler;
import java.net.*;
import java.io.*;
import java.util.Date;
import org.quickserver.net.server.*;

    public class CHandler implements ClientCommandHandler {

        public void gotConnected(ClientHandler handler)
                throws SocketTimeoutException, IOException {
            handler.sendSystemMsg("Connection opened : "+
                    handler.getSocket().getInetAddress());

            handler.sendClientMsg("Welcome to DateServer v ");
        }

        public void lostConnection(ClientHandler handler)
                throws IOException {
            handler.sendSystemMsg("Connection lost : " +
                    handler.getSocket().getInetAddress());
        }
        public void closingConnection(ClientHandler handler)
                throws IOException {
            handler.sendSystemMsg("Connection closed : " +
                    handler.getSocket().getInetAddress());
        }

        public void handleCommand(ClientHandler handler, String command)
                throws SocketTimeoutException, IOException {

            if(command.toLowerCase().equals("quit")) {
                handler.sendClientMsg("Bye ;-)");
                handler.closeConnection();
            } else if(command.toLowerCase().equals("exchange date")) {
                handler.setDataMode(DataMode.OBJECT, DataType.OUT);
                handler.sendClientObject(new Date());
                handler.setDataMode(DataMode.STRING, DataType.OUT);

                //will block until the client ObjectOutputStream
                //has written and flushed the header.
                //we know our client will send date object
                //as soon as it recives our date its ok
                handler.setDataMode(DataMode.OBJECT, DataType.IN);
            } else {
                handler.sendSystemMsg("Got cmd : " + command);
                handler.sendClientMsg("You Sent : " + command);
            }
        }
    }