package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DisneyApplication {

// ...

// ...

// ...
// ...

public static void main(String[] args) {
    SpringApplication.run(DisneyApplication.class, args);
    try {
        // Leitura do nome do personagem a ser pesquisado
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Digite o nome do personagem: ");
        String characterName = reader.readLine();

        // Formatação do nome do personagem para ser usado na URL da API
        String formattedName = characterName.replace(" ", "%20");

        // Construção da URL da API com o nome do personagem
        String apiUrl = "https://api.disneyapi.dev/character?name=" + formattedName;

        // Criação da conexão HTTP
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Verificação do código de resposta HTTP
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Leitura da resposta da API
            BufferedReader apiReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = apiReader.readLine()) != null) {
                response.append(line);
            }
            apiReader.close();

            // Processamento dos dados da resposta
            JSONObject responseObject = new JSONObject(response.toString());
            if (responseObject.has("data")) {
                JSONArray characterArray = responseObject.getJSONArray("data");
                if (characterArray.length() > 0) {
                    JSONObject characterObject = characterArray.getJSONObject(0);
                    JSONArray filmsArray = characterObject.getJSONArray("films");
                    if (filmsArray.length() > 0) {
                        System.out.println("Filmes em que o personagem " + characterName + " participou:");
                        for (int i = 0; i < filmsArray.length(); i++) {
                            String filmTitle = filmsArray.getString(i);
                            System.out.println(filmTitle);
                        }
                    } else {
                        System.out.println("O personagem " + characterName + " não participou de nenhum filme.");
                    }
                } else {
                    System.out.println("Personagem não encontrado.");
                }
            } else {
                System.out.println("Resposta da API inválida. A chave 'data' não foi encontrada.");
            }
        } else {
            System.out.println("Erro na conexão. Código de resposta: " + responseCode);
        }

        // Fechamento da conexão
        connection.disconnect();
    } catch (IOException | JSONException e) {
        e.printStackTrace();
    }
}
}







	
		// RestTemplate restTemplate = new RestTemplate();

		// Scanner scanner = new Scanner(System.in);

		// System.out.print("Digite os parâmetros da consulta (ex: name=mickey): ");
		// String queryParameters = scanner.nextLine();

		// try {
		// String filterCharacterEndpoint = "https://api.disneyapi.dev/character?" +
		// queryParameters;

		// String filterCharacterResponse =
		// restTemplate.getForObject(filterCharacterEndpoint, String.class);

		// ObjectMapper objectMapper = new ObjectMapper();
		// Object json = objectMapper.readValue(filterCharacterResponse, Object.class);
		// String formattedJson =
		// objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

		// System.out.println("Response from filterCharacter endpoint:");
		// System.out.println(formattedJson);
		// } catch (Exception e) {
		// System.out.println("Erro ao consultar a API: " + e.getMessage());
		// }

		// scanner.close();
	

	
   