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

/**
 *
 * @author Laptop
 */
public class Server_UPIN {
    private static String username = "UPIN";
    private static String password = "1234";
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
                
                String receivedData = input.readUTF();
                String[] informasi = receivedData.split("#");
                String usernameClient = informasi[0];
                String passwordClient = informasi[1];
                
                if (username.equals(usernameClient) && password.equals(passwordClient)) {
                    output.writeUTF("success");
                } else {
                    output.writeUTF("fail");
                }
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Terjadi error (server): " + e);
        }
    }
    
}
