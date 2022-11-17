import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Server started!"); // хорошо бы серверу объявить о своем запуске
                while (true) { //чтобы сервер не закрывался после первого клиента
                    clientSocket = server.accept(); // accept() будет ждать пока кто-нибудь не захочет подключиться
                    try { // установив связь и воссоздав сокет для общения с клиентом можно перейти к созданию потоков ввода/вывода.

                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));// теперь мы можем принимать сообщения
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));// и отправлять

                        String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                        System.out.println(word);
                        // не долго думая отвечает клиенту
                        out.write("Hi, you wrote: " + word + "\n");
                        out.flush(); // выталкиваем все из буфера

                        if (word.equals("close connection")) {
                            clientSocket.close();
                            in.close();
                            out.close();
                            System.out.println("Server closed");
                            server.close();
                        }
                    } catch (IOException e) {
                        System.err.println(e);
                    }

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}