import java.io.*;
import java.util.HashMap;

public class IDandPasswords {
    HashMap<String,String> loginInfo = new HashMap<String,String>();
    private final String fileName = "userCredentials.txt";

    public IDandPasswords() {
        loadCredentials();
    }

    public HashMap<String, String> getLonginInfo() {
        return loginInfo;
    }

    public void addUser(String userID, String password) {
        loginInfo.put(userID, password);
        saveCredentials();
    }

    private void loadCredentials() {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    loginInfo.put(parts[0], parts[1]);
                }
            }
        }
        catch (IOException e) {
            System.out.println("No existing credentials found.");
        }
    }

    private void saveCredentials() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for(String user : loginInfo.keySet()) {
                writer.write(user + ":" + loginInfo.get(user));
                writer.newLine();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
