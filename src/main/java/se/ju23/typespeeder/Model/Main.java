package se.ju23.typespeeder.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {
    @Autowired
    private Controllable controller;
    @Override
    public void run(String... args) throws Exception {
        controller.start();
    }
}
