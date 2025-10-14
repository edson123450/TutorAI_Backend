package com.example.tutorai.OpenAI;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OpenAiClient implements AiClient{

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate=new RestTemplate();

    @Override
    public String generateResponse(String input) {
        String url = "https://api.openai.com/v1/responses";

        Map<String, Object> body = Map.of(
                "model", "gpt-5",
                "input", input
        );

        var headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ResponseEntity<Map> resp =
                restTemplate.postForEntity(url, new HttpEntity<>(body, headers), Map.class);

        Map<?, ?> root = resp.getBody();
        if (root == null) return fallback();

        Object output = root.get("output");
        if (output instanceof List<?> outList && !outList.isEmpty()) {
            // buscamos el primer bloque de tipo "message" -> content[0].text
            for (Object o : outList) {
                if (o instanceof Map<?, ?> msg &&
                        "message".equals(msg.get("type"))) {
                    Object content = msg.get("content");
                    if (content instanceof List<?> cl && !cl.isEmpty()) {
                        Object first = cl.get(0);
                        if (first instanceof Map<?, ?> block) {
                            Object text = block.get("text");
                            if (text != null) return text.toString().trim();
                        }
                    }
                }
            }
            // fallback: intenta el primer bloque que tenga "text"
            Object first = outList.get(0);
            if (first instanceof Map<?, ?> block) {
                Object content = block.get("content");
                if (content instanceof List<?> cl && !cl.isEmpty()) {
                    Object b0 = cl.get(0);
                    if (b0 instanceof Map<?, ?> m && m.get("text") != null) {
                        return m.get("text").toString().trim();
                    }
                }
            }
        }

        // compatibilidad con chat.completions si alguna vez lo usas
        Object choices = root.get("choices");
        if (choices instanceof List<?> ch && !ch.isEmpty()) {
            Object first = ch.get(0);
            if (first instanceof Map<?, ?> cm) {
                Object message = cm.get("message");
                if (message instanceof Map<?, ?> mm) {
                    Object text = mm.get("content");
                    if (text != null) return text.toString().trim();
                }
            }
        }

        return fallback();
    }

    private String fallback() {
        return "Hola, profesor. Puedo ayudarte a planificar niveles y ejercicios para tus alumnos.";
    }






    /*@Override
    public String generateGreeting(String prompt) {
        String url = "https://api.openai.com/v1/responses";

        // Cuerpo del request según el nuevo formato de OpenAI Responses API
        Map<String, Object> body = Map.of(
                "model", "gpt-5",
                "input", prompt
        );

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Llamada HTTP al endpoint
        ResponseEntity<Map> resp =
                restTemplate.postForEntity(url, new HttpEntity<>(body, headers), Map.class);

        Map<?, ?> root = resp.getBody();
        if (root == null) {
            return fallback();
        }

        Object output = root.get("output");
        if (output instanceof List<?> outList) {
            for (Object item : outList) {
                if (item instanceof Map<?, ?> msg) {
                    Object type = msg.get("type");
                    if ("message".equals(type)) { // solo nos interesa el bloque del assistant
                        Object content = msg.get("content");
                        if (content instanceof List<?> contentList) {
                            for (Object block : contentList) {
                                if (block instanceof Map<?, ?> blockMap) {
                                    Object text = blockMap.get("text");
                                    if (text != null) {
                                        return text.toString().trim();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return fallback();
    }

    private String fallback() {
        return "Hola, profesor. Puedo ayudarte a planificar niveles y ejercicios para tus alumnos.";
    }*/

}
