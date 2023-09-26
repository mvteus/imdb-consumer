import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // System.out.println("Resposta: " + json);

        String[] moviesArray = parseJsonMovies(json);

        List<String> titles = parseTitles(moviesArray);
        titles.forEach(System.out::println);

        List<String> urlImages = parseUrlImages(moviesArray);
        urlImages.forEach(System.out::println);

    }

    private static String[] parseJsonMovies(String json){

        /* Pegamos tudo que estiver dentro do colchetes [] do resultdo do JSON */
        Matcher matcher = Pattern.compile(".*\\[(.*)\\].*").matcher(json);

        /* Validamos o matcher */
        if (!matcher.matches()) {
            throw new IllegalArgumentException("no match in " + json);
        }

        /* Após pegarmos tudo dentro do colchetes, dividimos por filme de acordo com o regex },{ e atribuímos a um array moviesArray */
        String[] moviesArray = matcher.group(1).split("\\},\\{");
        moviesArray[0] = moviesArray[0].substring(1);
        int last = moviesArray.length - 1;
        String lastString = moviesArray[last];
        moviesArray[last] = lastString.substring(0, lastString.length() - 1);
        return moviesArray;
    }

    /* Parseia o título de cada filme do JSON */
    private static List<String> parseTitles(String[] moviesArray){
        return parseAttribute(moviesArray, 3);
    }

    /* Parseia a URL do pôster de cada filme do JSON */
    private static List<String> parseUrlImages(String[] moviesArray){
        return parseAttribute(moviesArray, 5);
    }

    private static List<String> parseAttribute(String[] moviesArray, int pos){
        return Stream.of(moviesArray)
                .map(e->e.split("\",\"")[pos])
                .map(e->e.split(":\"")[1])
                .map(e->e.replaceAll("\"",""))
                .collect(Collectors.toList());
    }
}