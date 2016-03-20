package buildings.net.client;

import buildings.dwelling.DwellingFactory;
import buildings.dwelling.hotel.HotelFactory;
import buildings.office.OfficeFactory;
import interfaces.Building;
import interfaces.BuildingFactory;
import io.Buildings;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;

public class SerialClient {

    public static void main(String[] args) throws IOException {

        BufferedReader typeReader = new BufferedReader(new FileReader("type.txt"));
        FileWriter price = new FileWriter("price.txt");

        BuildingFactory factory;
        String factoryType;

        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 10000);

        boolean flag = true;

        if ((factoryType = typeReader.readLine()) != null) {
            do {
                switch (factoryType) {
                    case "Dwelling": {
                        factory = new DwellingFactory();
                        break;
                    }
                    case "Office": {
                        factory = new OfficeFactory();
                        break;
                    }
                    case "Hotel": {
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

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                out.writeUTF(factoryType);
                out.flush();

                if (in.readBoolean()) {
                    out.writeObject(build);
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
