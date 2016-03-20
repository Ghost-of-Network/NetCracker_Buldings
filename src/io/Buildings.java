package io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringWriter;
import java.io.Writer;

import buildings.SynchronizedFloor;
import interfaces.BuildingFactory;
import interfaces.Space;
import interfaces.Floor;
import interfaces.Building;
import java.lang.reflect.InvocationTargetException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Buildings {

    static BuildingFactory buildingFactory = new buildings.dwelling.DwellingFactory();

    public static void setBuildingFactory(BuildingFactory factory) {
        buildingFactory = factory;
    }

    public static Space createSpace(float area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(float area, Class<? extends Space> spaceClass) {
        try {
            return spaceClass.getConstructor(new Class[]{Float.TYPE}).newInstance(new Object[]{area});
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Buildings.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException();
    }

    public static Space createSpace(int roomsCount, float area) {
        return buildingFactory.createSpace(roomsCount, area);
    }

    public static Space createSpace(int roomsCount, float area, Class<? extends Space> spaceClass) {
        try {
            return (Space) spaceClass.getConstructor(new Class[]{Integer.TYPE, Float.TYPE}).newInstance(new Object[]{roomsCount}, new Object[]{area});
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Buildings.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException();
    }

    public static Floor createFloor(int spacesCount) {
        return buildingFactory.createFloor(spacesCount);
    }

    public static Space createFloor(int spacesCount, Class<? extends Floor> floorClass) {
        try {
            return (Space) floorClass.getConstructor(new Class[]{Integer.TYPE}).newInstance(new Object[]{spacesCount});
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Buildings.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException();
    }

    public static Floor createFloor(Space[] spaces) {
        return buildingFactory.createFloor(spaces);
    }

    public static Floor createFloor(Class<? extends Floor> floorClass, Space... spaces) {
        try {
            return floorClass.getConstructor(new Class[]{spaces.getClass()}).newInstance(new Object[]{spaces});
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Buildings.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException();
    }

    public static Building createBuilding(int floorsCount, int... spacesCounts) {
        return buildingFactory.createBuilding(floorsCount, spacesCounts);
    }

    public static Building createBuilding(int floorsCount, Class<? extends Building> buildingClass) {
        try {
            return buildingClass.getConstructor(new Class[]{Integer.TYPE}).newInstance(new Object[]{floorsCount});
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Buildings.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException();
    }

    public static Building createBuilding(Floor[] floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static Building createBuilding(Class<? extends Building> buildingClass, Floor... floors) {
        try {
            return buildingClass.getConstructor(new Class[]{floors.getClass()}).newInstance(new Object[]{floors});
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Buildings.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new IllegalArgumentException();
    }

    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        Writer writer = new StringWriter();
        writeBuilding(building, writer);
        out.write(writer.toString().getBytes());
        out.flush();
    }

    public static Building inputBuilding(InputStream in) throws IOException {
        StreamTokenizer inputStream = new StreamTokenizer(in);

        inputStream.nextToken();
        Floor[] floors = new Floor[(int) inputStream.nval];
        for (int i = 0; i < floors.length; i++) {
            inputStream.nextToken();
            Space[] spaces = new Space[(int) inputStream.nval];
            for (int j = 0; j < spaces.length; j++) {
                inputStream.nextToken();
                int roomsCount = (int) inputStream.nval;
                inputStream.nextToken();
                float area = (float) inputStream.nval;
                spaces[j] = createSpace(roomsCount, area);
            }
            floors[i] = createFloor(spaces);
        }

        return createBuilding(floors);
    }

    public static Building inputBuilding(InputStream in, Class<? extends Building> buildingClass, Class floorClass, Class spaceClass) throws IOException {

        StreamTokenizer inputStream = new StreamTokenizer(in);

        inputStream.nextToken();
        Floor[] floors = new Floor[(int) inputStream.nval];
        for (int i = 0; i < floors.length; i++) {
            inputStream.nextToken();
            Space[] spaces = new Space[(int) inputStream.nval];
            for (int j = 0; j < spaces.length; j++) {
                inputStream.nextToken();
                int roomsCount = (int) inputStream.nval;
                inputStream.nextToken();
                float area = (float) inputStream.nval;
                spaces[j] = createSpace(roomsCount, area, spaceClass);
            }
            floors[i] = (Floor) createFloor(floorClass, spaces);
        }
        return (Building) createBuilding(buildingClass, floors);
    }

    public static Building readBuilding(Reader in) throws IOException {
        Scanner inputStream = new Scanner(in);
                
        Floor[] floors = new Floor[inputStream.nextInt()];
        for (int i = 0; i < floors.length; i++) {
            Space[] spaces = new Space[inputStream.nextInt()];
            for (int j = 0; j < spaces.length; j++) {
                int roomsCount = inputStream.nextInt();
                float area = inputStream.nextFloat();
                spaces[j] = createSpace(roomsCount, area);
            }
            floors[i] = createFloor(spaces);
        }
        return createBuilding(floors);
    }

//    public static void writeBuilding(Building building, Writer out) throws IOException {
//        PrintWriter printWriter = new PrintWriter(out);
//
//        int floorsCount = building.floorsCount();
//        printWriter.print(floorsCount + " ");
//        for (int i = 0; i < floorsCount; i++) {
//            Floor floor = building.getFloor(i);
//            Space[] spaces = floor.getSpaces();
//
//            printWriter.print(spaces.length + " ");
//            for (Space space : spaces) {
//                printWriter.print(space.getRoomsCount() + " ");
//                printWriter.print(space.getArea() + " ");
//            }
//        }
//        printWriter.println();
//    }
    public static void writeBuilding(Building building, Writer out) throws IOException {
        Formatter printWriter = new Formatter(out);

        int floorsCount = building.floorsCount();
        printWriter.format("%d ", floorsCount);
        for (int i = 0; i < floorsCount; i++) {
            Floor floor = building.getFloor(i);
            Space[] spaces = floor.getSpaces();

            printWriter.format("%f ", spaces.length);
            for (Space space : spaces) {
                printWriter.format("%d %f", space.getRoomsCount(), space.getArea());
            }
        }
    }

    public static Floor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }
}
