package si.unm_fei.logic;


import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

import java.io.FileReader;
import java.lang.reflect.Type;


public class QuestionLoader {

    public static Map<Kategorija, ArrayList<Question>> load() {
        Map<Kategorija, ArrayList<Question>> map = new HashMap<>();

        // Inicializiraj prazne liste za vse kategorije
        for (Kategorija k : Kategorija.values()) {
            map.put(k, new ArrayList<>());
        }

        try (Reader reader = new InputStreamReader(
                QuestionLoader.class.getResourceAsStream("/Questions.json"))) {

            Gson gson = new Gson();
            Type listType = new TypeToken<List<JsonObject>>() {}.getType();
            List<JsonObject> questions = gson.fromJson(reader, listType);

            for (JsonObject obj : questions) {
                String text = obj.get("question").getAsString();

                JsonArray ans = obj.getAsJsonArray("answers");
                String[] answers = new String[ans.size()];
                for (int i = 0; i < ans.size(); i++) {
                    answers[i] = ans.get(i).getAsString();
                }

                String correct = obj.get("correct").getAsString();

                // Preberi category string in pretvori v Kategorija enum
                String categoryStr = obj.get("category").getAsString();
                Kategorija kategorija = Kategorija.valueOf(categoryStr);

                // Kreiraj Question objekt
                Question question = new Question(text, answers, correct, kategorija);

                // Dodaj v ustrezno kategorijo
                map.get(kategorija).add(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Napaka pri nalaganju vprašanj", e);
        }

        return map;
    }
}

/*public class QuestionLoader {

    public static List<Question> load(String path) {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Question>>() {}.getType();
            return gson.fromJson(new FileReader(path), listType);
        } catch (Exception e) {
            throw new RuntimeException("Napaka pri branju JSON", e);
        }
    }
}*/
