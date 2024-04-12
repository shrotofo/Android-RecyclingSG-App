package com.example.binbolehxfirebase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;

public class AIChatBot extends Fragment {
    private EditText editTextQuestion;
    private Button buttonSend;
    private RecyclerView messagesRecyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messagesList = new ArrayList<>();

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
        buttonSend = view.findViewById(R.id.buttonSend);
        messagesRecyclerView = view.findViewById(R.id.messagesRecyclerView);

        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter = new MessageAdapter(getContext(), messagesList);
        messagesRecyclerView.setAdapter(messageAdapter);

        buttonSend.setOnClickListener(v -> {
            String question = editTextQuestion.getText().toString().trim();
            if (!question.isEmpty()) {
                // Display the user's message
                addMessage(new Message(question, true));
                // Clear input field
                editTextQuestion.setText("");
                // Call the method to handle sending the question
                chatGPT(question);
            }
        });

        return view;
    }

    private void chatGPT(String question) {
        String[] keywords = {"recycle", "recycling", "plastic", "plastics", "metal", "metals",
                "glass", "batteries", "battery", "trash", "bin", "bins", "compost",
                "environment", "sustainability", "iron", "steel"};
        String[] queries = {"hello", "who are you", "what are you", "what can you do",
                "hello, who are you", "hello who are you", "hi"};
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
                return "Hello, I am Boleh Bot, your recycling guide. Ask me anything about recycling!";
            } else {
                // If it contains neither, respond with an error message
                return "Sorry, I can only provide information about recycling. Please ask a recycling-related question.";
            }
        }, executorService).thenAccept(response -> handler.post(() -> addMessage(new Message(response, false))));
    }

    private void addMessage(Message message) {
        messagesList.add(message);
        // Notify the adapter that a new item has been added
        messageAdapter.notifyItemInserted(messagesList.size() - 1);
        // Scroll to the bottom so the newest message is visible
        messagesRecyclerView.scrollToPosition(messagesList.size() - 1);
    }

    private String makeNetworkCallToChatGPT(String question) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "INSERT API KEY HERE";
        String model = "gpt-3.5-turbo"; // Adjust model as needed

        // Create the HTTP POST request
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Authorization", "Bearer " + apiKey);
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(15000); // 15 seconds connection timeout
        con.setReadTimeout(15000); // 15 seconds read timeout
        con.setDoOutput(true);
        // Build the request body
        String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + question + "\"}]}";
        try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
            writer.write(body);
        }
        StringBuilder response = new StringBuilder();
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Check for success response code
            Log.i("Request sent", "makeNetworkCallToChatGPT: Success");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Log.i("Buffered input", "makeNetworkCallToChatGPT: Success");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            Log.i("return", "makeNetworkCallToChatGPT: return success ");
            return extractContentFromResponse(response.toString());
        } else {
            // Handle error response...
            return "Error: Server responded with code " + responseCode;
        }
    }
    private String extractContentFromResponse(String response) {
        Log.i("response", "extractContentFromResponse: " + response);
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray choices = jsonResponse.getJSONArray("choices");
            if (choices.length() > 0) {
                JSONObject firstChoice = choices.getJSONObject(0);
                // Now we access the 'message' object first
                if(firstChoice.has("message")) {
                    JSONObject message = firstChoice.getJSONObject("message");
                    // And then, extract 'content' from 'message'
                    if(message.has("content")) {
                        return message.getString("content");
                    } else {
                        return "The 'content' field is missing in the message.";
                    }
                } else {
                    return "The 'message' object is missing in the choice.";
                }
            } else {
                return "No choices available in the response.";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error parsing response: " + e.getMessage();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Shut down the executor service when the fragment is destroyed
    }
}


