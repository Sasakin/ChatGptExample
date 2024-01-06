package org.charot.edu.platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import okhttp3.OkHttpClient;
import org.charot.edu.platform.proxy.ProxyParser;
import retrofit2.Retrofit;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;

import static com.theokanning.openai.service.OpenAiService.*;

public class OpenAiServiceMAin {
    public static void main(String[] args) {
        String token = "sk-1231313131";
        Duration timeout = Duration.ofMillis(100000);
        /*OpenAiService service = new OpenAiService(token);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Somebody once told me the world is gonna roll me")
                .model("babbage-002")
                        .echo(true)
                        .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);*/
        //String hostPort = ProxyParser.parseProxies().get(1);
        //String[] proxyInfo = hostPort.split(" ")[4].split(":");
        String host = "23.88.59.163"; //proxyInfo[0];
        int port = 80; //Integer.valueOf(proxyInfo[1].replace(">",""));

        ObjectMapper mapper = defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
        OkHttpClient client = defaultClient(token, timeout)
                .newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        OpenAiService service = new OpenAiService(api);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Somebody once told me the world is gonna roll me")
                .model("gpt-3.5-turbo")
                .echo(true)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);

    }
}
