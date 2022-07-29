// Created by Torben R.
package de.northernsi.mineplace.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.northernsi.mineplace.MinePlace;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MultiLanguageProvider {

    private static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();
    private static final JsonParser PARSER = new JsonParser();
    private static MultiLanguageProvider instance;
    private static JsonObject jsonObject = new JsonObject();
    private final File dir = new File(MinePlace.getInstance().getDataFolder().getPath() + "data/lang/");
    private final File selectedLanguagesFile = new File(dir + "selected.json");
    private final Map<String, Properties> languages = new HashMap<>();

    @SneakyThrows
    public MultiLanguageProvider() {
        if (!selectedLanguagesFile.exists()) {
            jsonObject = new JsonObject();
        }
        jsonObject = (JsonObject) PARSER.parse(GSON.newJsonReader(new FileReader(selectedLanguagesFile)));
        loadLanguages();
    }

    public static MultiLanguageProvider getInstance() {
        if (MultiLanguageProvider.instance == null) {
            MultiLanguageProvider.instance = new MultiLanguageProvider();
        }

        return MultiLanguageProvider.instance;
    }

    public String getLang(Player player) {
        String selected = jsonObject.has(player.getUniqueId().toString()) ? jsonObject.get(player.getUniqueId().toString()).getAsString() : "en";
        if (!languages.containsKey(selected)) return "en";
        return selected;
    }

    public String getTranslatedString(Player player, String key) {
        return languages.get(getLang(player)).get(key) == null ? "Translation not found" :
                (String) languages.get(getLang(player)).get(key);
    }

    @SneakyThrows(IOException.class)
    public void saveObject() {
        if (selectedLanguagesFile.delete()) selectedLanguagesFile.createNewFile();
        FileWriter writer = new FileWriter(selectedLanguagesFile);
        writer.write(GSON.toJson(jsonObject));
        writer.close();
    }

    private void loadLanguages() {
        Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(file -> {
            if (!file.getName().split(".")[1].equals("properties")) return;
            Properties properties = new Properties();
            try {
                properties.load(new FileReader(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            languages.put(file.getName(), properties);
        });
    }
}
