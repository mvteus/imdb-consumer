import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Principal {
    public static void main(String[] args) throws Exception {

        /* A solução abaixo é do site OpenJDK
         *  https://openjdk.org/groups/net/httpclient/intro.html?utm_medium=email&_hsmi=231338777&utm_content=231338777&utm_source=hs_automation
         */

        /*String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NmRmMjdlMDM0OTQzZDYxNzA2MTZlYzFhODY3N2NhOCIsInN1YiI6IjY1MGRhNDdhYjViYzIxMDE0ZTkxOTQ0ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yum_PP4Uy4rPCE63Ju6j7k1dzDKdnp-a1RHXI5XxlAI";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/movie/top_rated?language=en-US" + apiKey)).header("accept", "application/json").header("Authorization", "Bearer " + apiKey)
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();*/

        /* A solução abaixo é do pessoal do desafio #7DaysOfCode */

        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NmRmMjdlMDM0OTQzZDYxNzA2MTZlYzFhODY3N2NhOCIsInN1YiI6IjY1MGRhNDdhYjViYzIxMDE0ZTkxOTQ0ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Yum_PP4Uy4rPCE63Ju6j7k1dzDKdnp-a1RHXI5XxlAI";
        URI apiTMDB = URI.create("https://api.themoviedb.org/3/movie/top_rated?language=en-US" + apiKey);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(apiTMDB)
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        System.out.println("Resposta: " + json);

        String[] movieArray = parseJsonMovies(json);

        List<String> titles = parseTitles(movieArray);
        titles.forEach(System.out::println);

        List<String> urlImages = parseUrlImages(moviesArray);
        urlImages.forEach(System.out::println);

    }

    public String parseJsonMovies(String json){
        return null;
    }

    public String parseTitles(String titles){
        return titles;
    }

    public String parseUrlImages(String urlImages){
        return urlImages;
    }
}