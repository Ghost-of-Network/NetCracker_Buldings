package javaapplication4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

import frames.BuildingFrame;
import buildings.dwelling.*;
import buildings.office.*;
import buildings.threads.Cleaner;
import buildings.threads.FloorSynchronizer;
import buildings.threads.Repairer;
import buildings.threads.SequentalCleaner;
import buildings.threads.SequentalRepairer;
import buildings.SynchronizedFloor;
import buildings.PlacementExchanger;
import interfaces.*;
import io.Buildings;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.accessibility.Accessible;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JavaApplication4 extends JComponent implements Accessible {

//    private javax.swing.JButton clearButton;
//    private javax.swing.JScrollPane scrollPane;
//    private javax.swing.JTextArea textArea;
    private int voron = 0;

    public static void main(String[] args) {
                Floor[] floors = new Floor[30];
		Random r = new Random();
		for (int i = 0; i < 30; i++) {
			Space[] spaces = new Space[r.nextInt(40) + 1];
			for (int j = 0; j < spaces.length; j++) {
				spaces[j] = Buildings.createSpace(r.nextInt(4), 50 * r.nextFloat() + 10);
			}
			floors[i] = Buildings.createFloor(spaces);
		}
		
//		Building b = Buildings.createBuilding(floors);
//		Buildings.outputBuilding(b, new FileOutputStream(new File("/home/cub4d/testframeb.b")));
		
		BuildingFrame bframe  = new BuildingFrame();
		bframe.setVisible(true);
    }

    public JavaApplication4() {
//      super("My First Window");
        setBounds(100, 100, 200, 200);
   //     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
