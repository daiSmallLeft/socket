import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class B {
    public static final int PORT = 1992 ;

    public static void main(String[] args){
        System.out.println("----------服务器启动---------");
        B b = new B();
        b.init();
    }

    public void init(){
        try {
            ServerSocket socket = new ServerSocket(PORT);
            while(true){
                Socket client = socket.accept();
                new HandlerThread(client);
            }
        } catch (Exception e) {
            // TODO 自動生成された catch ブロック
            System.out.println("error"+ e.getMessage() +"服务器异常");
        }
    }

    private class HandlerThread implements Runnable{
        private Socket socket;
        public HandlerThread(Socket client){
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try{
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String str = input.readUTF();
                System.out.println("用户发来信息："+ str);

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("请回复：\t");
                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(s);
                out.close();
                input.close();
            }catch (Exception e){
                System.out.println("服务器 run 异常: " + e.getMessage());
            } finally {
                if(socket !=null){
                    try{
                        socket.close();
                    }catch(Exception e){
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                    }
                }
            }

        }
    }

}
