package buildings.net.server.parallel;

import buildings.dwelling.Dwelling;
import buildings.dwelling.hotel.Hotel;
import buildings.office.OfficeBuilding;
import exceptions.BuildingUnderArrestException;
import interfaces.Building;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerialServer {

    public static void main(String[] args) {

        int port = 10000;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Waiting for clients...");
            while (true) {
                Socket clientSocket = serverSocket.accept();

                Thread newConnect = new ServerThread(clientSocket);

                newConnect.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(BinaryServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static class ServerThread extends Thread {

        Socket clientSocket;

        public ServerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                try {
                    connect(clientSocket);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(BinaryServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void connect(Socket socket) throws IOException, ClassNotFoundException {

            System.out.println("We have got something!!!");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            String type = "";
            int coef = 0;
            Building build;

            boolean flag = true;

            while (flag) {
                type = in.readUTF();
                switch (type) {
                    case "Dwelling": {
                        coef = 1000;
                        out.writeBoolean(true);
                        out.flush();
                        build = (Dwelling) in.readObject();
                        break;
                    }
                    case "Office": {
                        coef = 1500;
                        out.writeBoolean(true);
                        out.flush();
                        build = (OfficeBuilding) in.readObject();
                        break;
                    }
                    case "Hotel": {
                        coef = 2000;
                        out.writeBoolean(true);
                        out.flush();
                        build = (Hotel) in.readObject();
                        break;
                    }
                    default: {
                        out.writeBoolean(false);
                        out.flush();
                        continue;
                    }
                }

                double price;
                try {
                    price = price(build, coef);
                } catch (BuildingUnderArrestException ex) {
                    price = -4;
                }
                System.out.println("Prive=" + price);
                System.out.println("Send price to client...");
                out.writeDouble(price);
                out.flush();

                flag = in.readBoolean();
            }
            System.out.println("Connection closed");
        }

        private double price(Building building, int coef) throws BuildingUnderArrestException {
            Random r = new Random();
            if (r.nextInt(100) < 100) {
                throw new BuildingUnderArrestException();
            }
            return building.totalArea() * coef;
        }
    }

}
