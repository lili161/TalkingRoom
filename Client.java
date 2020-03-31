import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author RIc lee
 * @Date 2020.03.30 21:48
 * @version 1.0.0
 *
 */
public class Client {
    //此为是否关闭的关键字
    static int isClose=0;
    public static void main(String[] args)  {
        System.out.println();
        System.out.println();
            System.out.println("--------------------------------------------READ ME----------------------------------------------");
            System.out.println("---------------Welcome to taling room,if you want to leave ,input ' END ' to Quit!---------------");
        System.out.println();
        Socket socket = null;
        try {
            //建立socket，连接服务器
            socket = new Socket("127.0.0.1",1024);
            //保持长时间的连接，如不写此句，可能会一会儿不用就掉线，造成各种错误
            socket.setKeepAlive(true);
            System.out.println("----------------------------------[Connect to Server Success!]-----------------------------------");
        } catch (IOException e) {
            System.err.println("----------------[Sorry,can not connect the server,please call the Administrator.]-----------------");
        }
        User user=new User();
        user.setSocket(socket);
        Scanner in=new Scanner(System.in);
        new Thread(()->{
            try {
                BufferedReader bf =new BufferedReader(new InputStreamReader(user.getSocket().getInputStream(),"utf-8"));
                String str;
                while((str=bf.readLine())!=null){
                    //判断关键字是否为1，若为1，则线程暂停
                    if(isClose!=1)
                    System.out.println(str);
                    else
                        Thread.interrupted();
                }
            } catch (IOException e) {
                System.err.println("[Sorry,there is something Wrong , please  retry.]");
            }catch (NullPointerException e1){
                return;
            }

        }).start();
        //以下为发送操作
        while(true){
            String s=in.nextLine();
            //若发送的值未“END” 则isClose关键字为1
            if(s.equals("END")) {
                try {
                    user.send(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isClose=1;
            }
            //若发送的数据不是“END” 则正常发送
            else {
                try {
                    user.send(s);
                } catch (IOException e) {
                    System.out.println("[You already Quit!]");
                    return;
                }catch (NullPointerException e2){
                    System.err.println("[Something wrong!]");
                    return;
                }
            }
        }
    }
}
