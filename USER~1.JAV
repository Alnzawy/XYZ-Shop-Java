import java.util.*;

public abstract class User {

    private int UserID;
    private String USERNAME;
    private String PASSWORD;
    private boolean isActive;

    private List<Integer> usedIds = new ArrayList<>();
    private Random random = new Random();
    private int ID_LENGTH = 3;

    private int generateUniqueID() {
        int id;
        do {
            id = random.nextInt((int) Math.pow(10, ID_LENGTH));
        } while (usedIds.contains(id));
        usedIds.add(id);
        return id;
    }


    public User(String username, String password) {
        this.UserID = generateUniqueID();
        this.USERNAME = username;
        this.PASSWORD = password;
        this.isActive = false;
    }





    public abstract void displayUserInfo();
    public abstract String getUserType();
    public abstract void Interface();



    public int getUserID() {
        return UserID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public boolean isActive() {
        return isActive;
    }
    public String getStatus() {
        if (isActive == true) {
            return "Active";
        } else {
            return "Inactive";
        }
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}