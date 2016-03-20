package buildings.net.client;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.HotelFactory;
import buildings.office.OfficeBuilding;
import buildings.office.OfficeFactory;
import interfaces.Building;
import interfaces.BuildingFactory;
import io.Buildings;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;

public class BinaryClient {

    public static void main(String[] args) throws IOException {

        Dwelling dwelling = new Dwelling(3, new int[]{3, 4, 5});
        OfficeBuilding offices = new OfficeBuilding(3, new int[]{3, 4, 5});

        //FileWriter fr = new FileWriter("testWriter.txt");
        //io.Buildings.writeBuilding(dwelling, fr);
        BufferedReader typeReader = new BufferedReader(new FileReader("type.txt"));
        FileWriter price = new FileWriter("price.txt");

        BuildingFactory factory;
        String factoryType;

        Socket socket = new Socket(InetAddress.getByName("192.168.43.209"), 10000);

        boolean flag = true;

        if ((factoryType = typeReader.readLine()) != null) {
            do {
                switch (factoryType) {
                    case "Dwelling": {
                        //System.out.println("1");
                        factory = new DwellingFactory();
                        break;
                    }
                    case "Office": {
                        //System.out.println("2");
                        factory = new OfficeFactory();
                        break;
                    }
                    case "Hotel": {
                        //System.out.println("3");
                        factory = new HotelFactory();
                        break;
                    }
                    default:
                        continue;
                }

                Buildings.setBuildingFactory(factory);

                BufferedReader buildingReader = new BufferedReader(new FileReader("buildings.txt"));
                StringReader buildingFile = new StringReader(buildingReader.readLine());

                Building build = Buildings.readBuilding(buildingFile);

                DataInputStream in;
                DataOutputStream out;

                System.out.println("Send " + factoryType + " to server");

                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());

                out.writeUTF(factoryType);
                out.flush();

                if (in.readBoolean()) {
                    Buildings.outputBuilding(build, out);
                    out.write('$');
                    out.flush();
                    System.out.println("Get price from server...");
                    double buildingPrice = in.readDouble();
                    if (buildingPrice == -4) {
                        price.write("Arested\r\n");
                    } else {
                        price.write("" + buildingPrice + "\r\n");
                    }
                }
                if ((factoryType = typeReader.readLine()) == null) {
                    flag = false;
                }
                out.writeBoolean(flag);
                out.flush();
            } while (flag);
            price.close();
        }
    }
}
