
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RIc lee
 * @Date 2020.03.30 21:48
 * @version 1.0.1
 *
 */
public class Server {
    //建立索引（User id 和 User的对应）
     private static Map<Integer,Object> map=new HashMap<Integer,Object>();
    public static void main(String[] args) throws IOException {
        //端口号
        int port=1024;
        //服务器建立Socket
        ServerSocket serverSocket=new ServerSocket(port);
        System.out.println("[Server is running ON PORT "+port+"]");
        //循环监听
        while (true){
            //num代表每个user的id
            int num;
            //接受客户端的请求
            Socket socket=serverSocket.accept();
            //保持长时间的连接，如不写此句，可能会一会儿不用就掉线，造成各种错误
            socket.setKeepAlive(true);
            System.out.println("[A client connected.]");
            //建立User的实例，并将与客户端对接的Socket口 赋值给其Socket变量
//            User user = new User(map.size() + 1, socket);
            User user = new User();
            user.setSocket(socket);
            for(int i=1;;i++) {
                if(!map.containsKey(i)) {
                    user.setId(i);
                break;
                }
            }
            //给num赋值
            num=user.getId();
            System.out.println("[Client's number is "+num+"]");
            //发送给客户端消息，此为告诉客户端自己的编号
            socket.getOutputStream().write(new String("---------------------[your client number is "+num+"   Online Client number is "+"【"+(int)(map.size()+1)+"】"+"]---------------------").getBytes("utf-8"));
            //如不发送一个‘\n’消息接收不到
            socket.getOutputStream().write(new String("\n").getBytes("utf-8"));
            //将user存入map中
            map.put(user.getId(),user);
            SendComeMassage(num);
            //新建立一个线程
            new Thread(()->{
                try {
                    //缓冲池接受 客户端发送过来的数据
                    BufferedReader bf=new BufferedReader(new InputStreamReader(user.getSocket().getInputStream(),"utf-8"));
                    String str;
                    while((str=bf.readLine())!=null){
                        //若 字符串未“END”则关闭Socket 并在map中删除 此User实例
                        if(str.equals("END")){
//                            SendMassage("quit",num);
                            SendQuitMassage(num);;
                            System.out.println("[Client "+num+" quit]");
                            user.getSocket().shutdownInput();
                            user.getSocket().shutdownOutput();
                            user.getSocket().close();
                            map.remove(user.getId());
                            //如不是“END” 则 调用SendMassage方法发送消息
                        }else {
                            System.out.println("[Client "+num+" Say]: "+str);
                            SendMassage(str, num);
                        }
                    }
                } catch (SocketException s){
                    try {
                        SendQuitMassage(num);;
                        user.getSocket().shutdownInput();
                        user.getSocket().shutdownOutput();
                        user.getSocket().close();
                        map.remove(user.getId());
                        System.out.println("[Client "+num+" quit]");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }
    //SendMassage方法
    private static void SendMassage(String massage,int num) throws IOException {
        //遍历map，将发送的数据传给出自己外的所有客户端
        for (int i=0;i<map.size();i++){
            User u=new User();
            u=(User)map.get(i+1);
            if(u.getId()!=num)
            u.send("[Client "+num+" Say]: "+massage);
        }

    }
    private static void SendQuitMassage(int num) throws IOException {
        //遍历map，将发送的数据传给出自己外的所有客户端
        for (int i=0;i<map.size();i++){
            User u=new User();
            u=(User)map.get(i+1);
            if(u.getId()!=num){
                u.send("[[Client "+num+" Already Quit!]]     "+"Online Client number is "+"【"+(int)(map.size()-1)+"】");
            }
        }

    }
    private static void SendComeMassage(int num) throws IOException {
        //遍历map，将发送的数据传给出自己外的所有客户端
        for (int i=0;i<map.size();i++){
            User u=new User();
            u=(User)map.get(i+1);
            if(u.getId()!=num){
                u.send("[[Client "+num+" Come in !]]     "+"Online Client number is "+"【"+(int)(map.size())+"】");
            }
        }
    }

}
