package service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

import config.ConfigLoader;
import dao.ProductoDAOImpl;
import model.ProductoOtaku;
import view.InterfazConsola;

/**
 * Servicio que gestiona la interacción con un modelo de lenguaje (LLM) externo mediante la API de OpenRouter.
 * 
 * Permite generar descripciones de productos y sugerencias de categoría de forma automática,
 * utilizando una API Key definida en el archivo de configuración.
 * 
 * @author Tamara Martínez Vargas
 * @version 1.0
 * @since 13/06/2025
 */
public class LlmService {
	
	private ProductoDAOImpl dao;
    private InterfazConsola consola;

    /**
     * Constructor con consola y DAO, útil cuando se quiere mostrar mensajes al usuario.
     * 
     * @param dao DAO de productos.
     * @param consola Consola para mostrar mensajes.
     */
    public LlmService(ProductoDAOImpl dao, InterfazConsola consola) {
        this.dao = dao;
        this.consola = consola;
    }

    /**
     * Constructor sin consola, útil si se usa desde entorno gráfico u otros contextos.
     * 
     * @param dao DAO de productos.
     */
    public LlmService(ProductoDAOImpl dao) {
        this.dao = dao;
        this.consola = consola; // Puede quedar null si no se usa.
    }

    /**
     * Envía un prompt al modelo LLM y devuelve la respuesta generada.
     * 
     * @param prompt Texto que se envía al modelo como entrada.
     * @return Respuesta generada por el LLM o null si hubo error.
     */
    public String llmService(String prompt) {
        String content = null;

        try {
            String apiKey = ConfigLoader.getPropiedades("apiKey");

            String requestBody = "{\n"
                    + "  'model': 'openai/gpt-4.1',\n"
                    + "  'max_tokens': 1024,\n"
                    + "  'messages': [\n"
                    + "    {\n"
                    + "      'role': 'user',\n"
                    + "      'content': '" + prompt + "'\n"
                    + "    }\n" + "  ]\n" + "}";

            // Reemplazar comillas simples por dobles para construir JSON válido
            requestBody = requestBody.replace('\'', '\"');

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("HTTP-Referer", "http://localhost:3306")
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject obj = new JSONObject(response.body());
                content = obj.getJSONArray("choices")
                             .getJSONObject(0)
                             .getJSONObject("message")
                             .getString("content");
                return content;
            } else {
                System.out.println("ERROR: llamada fallida a la API. Código: " + response.statusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return content;
    }

    /**
     * Genera el prompt para obtener una descripción de marketing de un producto.
     * 
     * @param idPrompt ID del producto sobre el que se quiere generar la descripción.
     * @return Prompt personalizado para el modelo, o null si el producto no existe.
     */
    public String obtenerDescripcion(int idPrompt) {
        ProductoOtaku producto = dao.obtenerProductoPorId(idPrompt);
        if (producto == null) {
            if (consola != null) consola.noExiste();
            return null;
        } else {
            String nombre = producto.getNombre();
            String categoria = producto.getCategoria();
            return "Genera una descripción de marketing sencilla, breve y atractiva para el producto otaku: "
                    + nombre + " de la categoría " + categoria;
        }
    }

    /**
     * Genera un prompt para que el LLM sugiera una categoría para un nuevo producto.
     * 
     * @param nombrePrompt Nombre del producto.
     * @return Prompt con la instrucción para obtener la categoría sugerida.
     */
    public String obtenerCategoria(String nombrePrompt) {
        return "Para el producto otaku " + nombrePrompt
                + " dime solo la categoría adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
    }
}
