import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        BooleanSearchEngine engine = new BooleanSearchEngine(new File("pdfs"));
        engine.readUnusefulWords("stop-ru.txt");

        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            System.out.println("Server started...");
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String word = in.readLine();
                    System.out.println("Request find \"" + word + "\"");

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    List<PageEntry> resp = engine.search(word);
                    System.out.println("Results -> " + resp);
                    out.println(gson.toJson(resp));
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
