package se.ju23.typespeeder.Model;

import com.google.gson.Gson;
import jakarta.persistence.PostLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Services.PrintService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
@Component
public class Patch {
    private String currentVersion = "1.0.0";
    private String latestVersion;
    private LocalDateTime realeaseDateTime;
    @Autowired
    private PrintService printer;

    public Patch() {

    }

    public Patch(PrintService printer) {
        this.printer = printer;
    }
    public  void getLatestUpdate(){
        Gson gson = new Gson();
        HashMap<String,String> map = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        String owner = "bervenuzik";
        String repo = "TypeSpeeder";

        URL url = null;
        try {
            url = new URL("https://api.github.com/repos/" + owner + "/" + repo + "/releases/latest");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String line;
        StringBuilder response = new StringBuilder();
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response.append(line);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        map = gson.fromJson(response.toString(), map.getClass());
        latestVersion = map.get("tag_name");
        if(isUpdateAvailable()) {
            printer.printWarning("New version available: " + latestVersion);
        } else {
            printer.printSuccess("You are using the latest version: " + currentVersion);
        }
        realeaseDateTime = LocalDateTime.parse(map.get("published_at") , formatter);;
        System.out.println(response);
    }
    private boolean isUpdateAvailable() {
        return !latestVersion.equals(currentVersion);
    }

    public LocalDateTime getRealeaseDateTime() {
        return realeaseDateTime;
    }

    public void setRealeaseDateTime(LocalDateTime realeaseDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        System.out.println(formatter.format(realeaseDateTime));
        this.realeaseDateTime = LocalDateTime.parse(formatter.format(realeaseDateTime));
        System.out.println(this.realeaseDateTime.toString());
    }
}

