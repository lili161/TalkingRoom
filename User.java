
import java.io.IOException;
import java.net.Socket;

/**
 * @author RIc lee
 * @Date 2020.03.30 21:48
 * @version 1.0.0
 *
 */
//建立 User实体类
public class User {
    //独特id
    private int id;
  //Socket
    private Socket socket;
    //消息
    private String massage;

    public User() {
        super();
    }

    public void send (String massage) throws IOException {
        this.massage=massage;
        socket.getOutputStream().write(massage.getBytes("utf-8"));
        socket.getOutputStream().write("\n".getBytes("utf-8"));
    }
    public User(int id ,Socket socket){
        this.id=id;
        this.socket=socket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", socket=" + socket +
                ", massage='" + massage + '\'' +
                '}';
    }
}
