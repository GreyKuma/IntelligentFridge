import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TelegramFridgeBot extends TelegramLongPollingBot {
    final String database = "***REMOVED***";
    final String user = "***REMOVED***";
    final String password = "***REMOVED***";


    public static void main(String[] args){
        System.out.println("Initializing API Context...");
        ApiContextInitializer.init();
        System.out.println("Initializing API...");
        TelegramBotsApi botsApi = new TelegramBotsApi();
        System.out.println("Registering Bots...");
        try{
            botsApi.registerBot(new TelegramFridgeBot());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
        System.out.println("Done initializing!");
    }

    @Override
    public void onUpdateReceived(Update update){
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            try(Connection connection = DriverManager.getConnection(database, user, password)) {
                FridgeDatabaseConnection fdbc = new FridgeDatabaseConnection(connection);
                String[] msgText = update.getMessage().getText().split(" ");
                if(msgText.length == 1 && msgText[0].equals("/content")){
                    sendContents(fdbc, update);
                }else if(msgText[0].equals("/add")){
                    if(msgText.length == 3 && isInteger(msgText[2])) addItem(fdbc, update, msgText);
                    else sendSimpleMessage("Not enough Arguments!\nFormat: /add <Name> <Quantity>", update);
                }else if(msgText[0].equals("/delete")){
                    if(msgText.length == 2) deleteItem(fdbc, update, msgText);
                    else sendSimpleMessage("Not enough Arguments!\nFormat: /delete <Name>", update);
                }else{
                    SendMessage message = new SendMessage()
                            .setChatId(update.getMessage().getChatId())
                            .setText("Invalid Command! Try again!");
                    try {
                        execute(message); // Call method to send the message
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException ex) {
                System.err.println("SQLException: " + ex.getMessage());
            }
        }
    }

    public void deleteItem(FridgeDatabaseConnection fdbc, Update update, String[] msgText) throws SQLException{
        fdbc.deleteItem(msgText[1]);
        sendSimpleMessage("Done! New contents are: ", update);
        sendContents(fdbc, update);
    }

    public void sendContents(FridgeDatabaseConnection fdbc, Update update) throws SQLException{
        HashMap<String,Integer> itemList = fdbc.getItems();
        String itemListString = "";
        for(Map.Entry<String,Integer> entry: itemList.entrySet()){
            itemListString += entry.getKey() + ":" + entry.getValue() + ";\n";
        }
        sendSimpleMessage(itemListString, update);
    }

    public void addItem(FridgeDatabaseConnection fdbc, Update update, String[] msgText) throws SQLException{
        fdbc.setItem(msgText[1], Integer.parseInt(msgText[2]));
        sendSimpleMessage("Done", update);
    }


    public void sendSimpleMessage(String msg, Update update){
        SendMessage message = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(msg);
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return "***REMOVED***";
    }

    @Override
    public String getBotToken() {
        return "***REMOVED***";
    }

    public boolean isInteger( String input ) {
        try {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e ) {
            return false;
        }
    }
}
