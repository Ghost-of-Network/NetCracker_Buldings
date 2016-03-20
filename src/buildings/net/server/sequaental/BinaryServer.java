package buildings.net.server.sequaental;

import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.HotelFactory;
import buildings.office.OfficeFactory;
import exceptions.BuildingUnderArrestException;
import interfaces.Building;
import interfaces.BuildingFactory;
import io.Buildings;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BinaryServer {
    
    public static void main(String[] args){
        
        int port = 10000;

        try {
            
            ServerSocket serverSocket = new ServerSocket(port);           
            
            System.out.println("Waiting for client...");                    
            while(true){
                
                Socket socket = serverSocket.accept();
            
                connect(socket);
            
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(BinaryServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private static void connect(Socket socket) throws IOException{
            
        System.out.println("We have got something!!!");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            
            String type = "";
            BuildingFactory factory;
            int coef = 0;
            
            boolean flag = true;
            
            while(flag){
                type = in.readUTF();
                switch(type){
                    case "Dwelling":{
                        factory = new DwellingFactory();
                        coef = 1000;
                        out.writeBoolean(true);
                        out.flush();
                        break;
                    }
                    case "Office":{   
                        factory = new OfficeFactory();
                        coef = 1500;
                        out.writeBoolean(true);
                        out.flush();
                        break;
                    }
                    case "Hotel":{                   
                        factory = new HotelFactory();
                        coef = 2000;
                        out.writeBoolean(true);
                        out.flush();
                        break;
                    }
                    default: {
                        out.writeBoolean(false);
                        out.flush();
                        continue;
                    }
                }
                Buildings.setBuildingFactory(factory);
                Building b = Buildings.inputBuilding(in);
                double price;
                try{
                price = price(b,coef);               
                }catch(BuildingUnderArrestException ex){
                    price = -4;
                }
                System.out.println("Price="+price);
                System.out.println("Send price to client...");
                out.writeDouble(price);
                out.flush();
                
                flag = in.readBoolean();
            }
            System.out.println("Connection closed");
    } 
    
    
    private static double price(Building building, int coef) throws BuildingUnderArrestException{
        Random r = new Random();
        if(r.nextInt(100)<10) { 
            throw new BuildingUnderArrestException();
        }
        return building.totalArea()*coef; 
    }
}
