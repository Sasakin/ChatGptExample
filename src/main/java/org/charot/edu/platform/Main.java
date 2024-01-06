package org.charot.edu.platform;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import static com.theokanning.openai.service.OpenAiService.defaultObjectMapper;

public class Main {
    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            String host = "23.88.59.163"; //proxyInfo[0];
            int port = 80; //Integer.valueOf(proxyInfo[1].replace(">",""));

            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            URL obj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection(proxy);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

    public static void main(String[] args) {

        System.out.println(chatGPT("hello, how are you? Can you tell me what's a Fibonacci Number?"));

    }
    public static void main2(String[] args) {
        try {
            // Установите URL-адрес API GPT-3
            URL url = new URL("https://api.openai.com/");

            // Создайте объект HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Установите метод запроса на POST
            connection.setRequestMethod("POST");

            // Установите заголовок Content-Type
            connection.setRequestProperty("Content-Type", "application/json");

            // Установите заголовок Authorization с вашим API-ключом GPT-3
            connection.setRequestProperty("Authorization",
                    "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");

            // Включите вывод данных в тело запроса
            connection.setDoOutput(true);

            // Создайте тело запроса в формате JSON
            String requestBody = "{\"prompt\": \"Hello, GPT-3!\"}";

            // Получите поток вывода для записи данных в тело запроса
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(requestBody);
            outputStream.flush();
            outputStream.close();

            // Получите код ответа от сервера
            int responseCode = connection.getResponseCode();

            // Прочитайте ответ от сервера
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Выведите ответ от сервера
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}