package app.com.uptimum.Socket;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

import static app.com.uptimum.util.Constants.BASE_URL;

public class SocketIO {
    public static Socket socket;
    public void ConnectSocket(){
        try {
            socket = IO.socket(BASE_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
    }
}
