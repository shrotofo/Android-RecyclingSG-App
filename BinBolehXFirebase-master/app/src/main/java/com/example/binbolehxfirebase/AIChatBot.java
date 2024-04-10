package com.example.binbolehxfirebase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AIChatBot extends Fragment {
    String[] keywords = {"recycle", "plastic", "plastics", "metal", "metals",
            "glass", "batteries", "battery", "trash", "bin", "bins", "compost",
            "environment", "sustainability"};
    String[] queries = {"hello", "who are you", "what are you", "what can you do",
            "hello, who are you", "hello who are you"};
    private EditText editTextQuestion;
    private TextView textViewResponse;
    private ExecutorService executorService = Executors.newFixedThreadPool(4); // Executor for background tasks
    private Handler handler = new Handler(Looper.getMainLooper()); // Handler to post results back to the main thread

    public AIChatBot() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.aichatbot, container, false);

        editTextQuestion = view.findViewById(R.id.editTextQuestion);
        Button buttonAsk = view.findViewById(R.id.buttonAsk);
        textViewResponse = view.findViewById(R.id.textViewResponse);

        buttonAsk.setOnClickListener(v -> {
            String question = editTextQuestion.getText().toString();
            chatGPT(question);
        });

        return view;
    }

    private void chatGPT(String question) {
        CompletableFuture.supplyAsync(() -> {
            // Network call to chatGPT API
            boolean containsKeyword = false;
            for (String keyword : keywords) {
                if (question.toLowerCase().contains(keyword.toLowerCase())) {
                    containsKeyword = true;
                    break;
                }
            }
            boolean containsQuery = false;
            for (String query : queries) {
                if (question.toLowerCase().contains(query.toLowerCase())) {
                    containsQuery = true;
                    break;
                }
            }
            if (containsKeyword) {
                try {
                    // If it contains a recycling keyword, process the question with OpenAI
                    return makeNetworkCallToChatGPT(question);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Failed to get response: " + e.getMessage();
                }
            } else if (containsQuery) {
                // If it contains a query, respond with a predefined message
                return "Hello, I am your recycling guide. Ask me anything about recycling!";
            } else {
                // If it contains neither, respond with an error message
                return "Sorry, I can only provide information about recycling. Please ask a recycling-related question.";
            }
        }, executorService).thenAccept(response -> handler.post(() -> textViewResponse.setText(response)));
    }

    private String makeNetworkCallToChatGPT(String question) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo"; // Adjust model as needed

        // Create the HTTP POST request
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        // Build the request body
        String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + question + "\"}]}";

        try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
            writer.write(body);
        }
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        // Extract and return the response content
        return extractContentFromResponse(response.toString());
    }
    private String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 10; // Adjusted for correct JSON parsing
        int endMarker = response.indexOf("\"", startMarker);
        return response.substring(startMarker, endMarker);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Shut down the executor service when the fragment is destroyed
    }
}



