import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        // socket used by client to send and receive data from server
        final Socket clientSocket;
        final BufferedReader in;   // read data from socket
        final PrintWriter out;     //  write data into socket
        final Scanner sc = new Scanner(System.in); // read from keyboard
        try {
            clientSocket = new Socket("192.168.1.34",5000);
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            Thread sender = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg = sc.nextLine();
                        out.println(msg);
                        out.flush();
                    }
                }
            });
            sender.start();
            Thread receiver = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try {
                        msg = in.readLine();
                        while(msg!=null){
                            System.out.println("Server : "+msg);
                            msg = in.readLine();
                        }
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Exception : "+e);
                        e.printStackTrace();
                    }
                }
            });
            receiver .start();
    }catch (IOException e){
        e.printStackTrace();
        }
    }
}
