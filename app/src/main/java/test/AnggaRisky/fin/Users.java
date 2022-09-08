package test.AnggaRisky.fin;

import com.google.firebase.firestore.auth.User;

public class Users {

    public String username, email;

    public Users(){

    }

    public Users(String username, String email){
        this.username = username;
        this.email = email;
    }
}
