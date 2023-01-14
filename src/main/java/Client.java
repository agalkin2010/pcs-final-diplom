import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    final static int PORT = 8989;

    public static void main(String[] args) {

        try (Socket client = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);) {

            String find = "состав этап бизнес";

            System.out.println("Find \"" + find + "\"");
            out.println(find);

            System.out.println("Results from Server> " + in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
