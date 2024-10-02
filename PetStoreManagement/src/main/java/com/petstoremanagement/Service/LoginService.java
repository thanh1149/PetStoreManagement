package com.petstoremanagement.Service;
import com.petstoremanagement.Global.AppProperties;
import com.petstoremanagement.Model.Staff;
import org.mindrot.jbcrypt.BCrypt;

public class LoginService {

    public static boolean login(String username, String password) {
        Staff staff = StaffService.authenticate(username);
        if (staff != null && BCrypt.checkpw(password, staff.getPassword())) {
            System.out.println("Login succesfull");
            AppProperties.setProperty("user.name", staff.getUsername());
            if (staff.getRole() != null) {
                AppProperties.setProperty("user.role", staff.getRole().getTitle());
            } else {
                System.out.println("Can't find staff role");
            }
            return true;
        }
        return false;
    }



//    public static boolean login(String username, String password){
//        Staff staff = StaffService.authenticate(username);
//        if(staff != null && password.equals(staff.getPassword())) {
//            System.out.println("login successful!");
//            return true;
//        }
//        System.out.println("login failed!");
//        return false;
//    }

}
