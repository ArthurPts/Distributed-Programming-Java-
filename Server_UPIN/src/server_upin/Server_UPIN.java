/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package server_upin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Laptop
 */
class User {
    String fullname, username, password, email, dob, memberSince;
    
    public User(String fullname, String username, String password, String email, String dob, String memberSince) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.memberSince = memberSince;
    }
}
public class Server_UPIN {
    private static String username = "UPIN";
    private static String password = "1234";
    private static ArrayList<User> users = new ArrayList<>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try (ServerSocket serverSocket = new ServerSocket(6000)) {
            System.out.println("Server berjalan, menunggu koneksi...");
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Koneksi tersambung ke " + socket.getInetAddress());
                
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                
                   try {
                    String receivedData = input.readUTF();
                    if (receivedData.startsWith("REGISTER")) {
                        String[] credentials = receivedData.split(",");
                        String fullname = credentials[1];
                        String username = credentials[2];
                        String password = credentials[3];
                        String email = credentials[4];
                        String dob = credentials[5];
                        String memberSince = credentials[6];
                        
                        if (isUserExists(username)) {
                            output.writeUTF("exists");
                        }
                        else {
                            users.add(new User(fullname, username, password, email, dob, memberSince));
                            output.writeUTF("registered");
                        }
                    } 
                    else {
                        String[] credentials = receivedData.split(",");
                        String username = credentials[0];
                        String password = credentials[1];
                   
                        if (username.equals(username) && password.equals(password) || isValidUser(username, password)) {
                            output.writeUTF("success");
                        } 
                        else {
                            output.writeUTF("fail");
                        }
                    }
                }  catch (IOException e) {
                    System.out.println("Koneksi ditutup oleh klien.");
                    }
          
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Terjadi error (server): " + e);
        } 
    }
     private static boolean isUserExists(String username) {
        return users.stream().anyMatch(user -> user.username.equals(username));
    }
    
    private static boolean isValidUser(String username, String password) {
        return users.stream().anyMatch(user -> user.username.equals(username) && user.password.equals(password));
    }
    
}
