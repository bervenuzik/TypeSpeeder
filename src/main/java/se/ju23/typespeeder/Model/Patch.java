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
    private String newVersionDescribtion;

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
            printer.printError("Error occurred while trying to get the latest version. Wrong URL format");
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            printer.printError("Error occurred while trying to get the latest version. Connection error");
        }
        try {
            conn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            printer.printError("Error occurred while trying to get the latest version. GET request error");
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            printer.printError("Error occurred while trying to get the latest version. Error while reading the response");
        }
        String line ="";
        StringBuilder response = new StringBuilder();
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                printer.printError("Error occurred while trying to get the latest version. Error while reading the response");
            }
            response.append(line);
        }
        try {
            reader.close();
        } catch (IOException e) {
            printer.printError("Error occurred while trying to get the latest version. Error while closing the reader");
        }
        map = gson.fromJson(response.toString(), map.getClass());
        String versionOnRemote = map.get("tag_name");
        if(versionOnRemote != null) latestVersion = versionOnRemote;
        String decribtion = map.get("body");
        if(decribtion != null) newVersionDescribtion = decribtion;
        if(isUpdateAvailable()) {
            printer.printWarning("New version available: " + latestVersion);
            printer.printWarning("Description: " + newVersionDescribtion);
        } else {
            printer.printSuccess("You are using the latest version: " + currentVersion);
        }
        String date = map.get("published_at");
        if(date != null) realeaseDateTime = LocalDateTime.parse(date.substring(0,date.length()-1),formatter);

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

