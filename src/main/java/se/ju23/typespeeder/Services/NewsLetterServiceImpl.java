package se.ju23.typespeeder.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.ju23.typespeeder.Model.NewsLetter;
import se.ju23.typespeeder.Model.Player;
import se.ju23.typespeeder.Model.PlayerType;
import se.ju23.typespeeder.Repositories.NewsLetterRepo;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NewsLetterServiceImpl implements NewsLetterServise {
    @Autowired
    private NewsLetterRepo newsLetterRepo;
    @Autowired
    private PrintService printer;
    @Autowired
    private InputService inputService;

    public NewsLetterServiceImpl(NewsLetterRepo newsLetterRepo, PrintService printer) {
        this.newsLetterRepo = newsLetterRepo;
        this.printer = printer;
    }

    public NewsLetterServiceImpl() {

    }
    @Override
    public void sendMessage(Player player){
        while(true){
            String message ;
            printer.printMessage("Wright your message here: ");
            message = inputService.getInputText();
            if(message.length() <100 || message.length() > 200){
                printer.printMessage("Message should be between 100 and 200 characters");
                if (!printer.tryAgain()){
                    break;
                }else{
                    continue;
                }
            }
            NewsLetter newsLetter = new NewsLetter(message , player , LocalDateTime.now());
            newsLetterRepo.save(newsLetter);
            printer.printMessage("Message sent");
            break;
        }

    }

    @Override
    public void openMesseges(){
        List<NewsLetter> newsLetters =  newsLetterRepo.findAll();
        printer.printMessage("Here is all messages from admins:");
        printer.printMessage("========================================================================");
        for (NewsLetter newsLetter : newsLetters) {
            printer.printMessage(newsLetter.getContent());
            printer.printMessage("From: " + newsLetter.getAuthor().getNickname() + " published" + newsLetter.getPublishDateTime() + "\n");
        }

        printer.printMessage("========================================================================");
    }

}
