package frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import interfaces.Building;
import interfaces.Floor;
import interfaces.Space;
import io.Buildings;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import buildings.dwelling.DwellingFactory;
import buildings.office.OfficeFactory;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class BuildingFrame extends JFrame {

    JMenuBar mainMenuBar;
    JMenu fileMenu;
    JMenu lookAndFeelMenu;

    JMenuItem openDwelling;
    JMenuItem openOfficeBuilding;

    JPanel mainPanel;
    JPanel infoPanel;
    JPanel viewPanel;

    JPanel spacePanel;
    JLabel spaceNumber;
    JLabel spaceRoomsCount;
    JLabel spaceArea;

    JPanel floorPanel;
    JLabel floorNumber;
    JLabel floorSpacesCount;
    JLabel floorArea;

    JPanel buildingPanel;
    JLabel buildingType;
    JLabel buildingFloorsCount;
    JLabel buildingArea;

    JPanel viewBuildingPanel;

    Building building;

    private class FloorPanel extends JPanel {

        int number;
        Floor floor;

        public FloorPanel(int number, Floor floor) {
            super(new FlowLayout(FlowLayout.LEFT));
            this.number = number;
            this.floor = floor;

            this.setBackground(new Color(255, 68, 0, 150));
            this.setBorder(new TitledBorder(new EtchedBorder(), "[" + String.valueOf(number) + "]", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.white));
        }

        public int getNumber() {
            return number;
        }

        public Floor getFloor() {
            return floor;
        }
    }

    private class SpaceButton extends JButton {

        int number;
        Space space;

        public SpaceButton(int number, Space space) {
            super();
            this.number = number;
            this.space = space;

            setPreferredSize(new Dimension(100, 50));
            setBorder(null);
//			setBackground(new Color(255, 222, 0, 255));
            setBackground(Color.yellow);
            setText(String.valueOf(number));
        }

        public int getNumber() {
            return number;
        }

        public Space getSpace() {
            return space;
        }
    }

    FloorPanel[] floorPanels;
    SpaceButton[] spaceButtons;

    public BuildingFrame() {
        super("Buildings");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 400);

        initComponents();
    }

    private void initComponents() {
        mainMenuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        lookAndFeelMenu = new JMenu("Look&Feel");
        openDwelling = new JMenuItem("Open Dwelling");
        openOfficeBuilding = new JMenuItem("Open OfficeBuilding");

        fileMenu.add(openDwelling);
        fileMenu.add(openOfficeBuilding);

        openDwelling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buildings.setBuildingFactory(new DwellingFactory());
                openBuildingActionPerformed(e);
            }
        });

        openOfficeBuilding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Buildings.setBuildingFactory(new OfficeFactory());
                openBuildingActionPerformed(e);
            }
        });

        ButtonGroup directionGroup = new ButtonGroup();
        LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        JRadioButtonMenuItem[] radioPlafs = new JRadioButtonMenuItem[plafs.length];
        for (int i = 0; i < radioPlafs.length; i++) {
            radioPlafs[i] = new JRadioButtonMenuItem(plafs[i].getName());
            lookAndFeelMenu.add(radioPlafs[i]);
            directionGroup.add(radioPlafs[i]);

            radioPlafs[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    lookAndFeelActionPerformed(e);
                }
            });
        }

        mainMenuBar.add(fileMenu);
        mainMenuBar.add(lookAndFeelMenu);
        this.setJMenuBar(mainMenuBar);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.darkGray);
        mainPanel.setBorder(new CompoundBorder(mainPanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

        // init info panel
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.darkGray);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Building info", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 14), Color.white));
        infoPanel.setBorder(new CompoundBorder(infoPanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

        spacePanel = new JPanel();
        spacePanel.setBackground(Color.darkGray);
        spacePanel.setLayout(new GridLayout(3, 1));
        spacePanel.setBorder(new TitledBorder(new EtchedBorder(), "Space", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.white));
        spacePanel.setBorder(new CompoundBorder(spacePanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

        floorPanel = new JPanel();
        floorPanel.setBackground(Color.darkGray);
        floorPanel.setLayout(new GridLayout(3, 1));
        floorPanel.setBorder(new TitledBorder(new EtchedBorder(), "Floor", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.white));
        floorPanel.setBorder(new CompoundBorder(floorPanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

        buildingPanel = new JPanel();
        buildingPanel.setBackground(Color.darkGray);
        buildingPanel.setLayout(new GridLayout(3, 1));
        buildingPanel.setBorder(new TitledBorder(new EtchedBorder(), "Building", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.white));
        buildingPanel.setBorder(new CompoundBorder(buildingPanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

        spaceNumber = new JLabel("Number: ");
        spaceNumber.setFont(new Font("SansSerif", Font.PLAIN, 11));
        spaceNumber.setForeground(Color.white);

        spaceRoomsCount = new JLabel("Rooms count: ");
        spaceRoomsCount.setFont(new Font("SansSerif", Font.PLAIN, 11));
        spaceRoomsCount.setForeground(Color.white);

        spaceArea = new JLabel("Area: ");
        spaceArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        spaceArea.setForeground(Color.white);

        spacePanel.add(spaceNumber);
        spacePanel.add(spaceRoomsCount);
        spacePanel.add(spaceArea);

        floorNumber = new JLabel("Number: ");
        floorNumber.setFont(new Font("SansSerif", Font.PLAIN, 11));
        floorNumber.setForeground(Color.white);

        floorSpacesCount = new JLabel("Flats count: ");
        floorSpacesCount.setFont(new Font("SansSerif", Font.PLAIN, 11));
        floorSpacesCount.setForeground(Color.white);

        floorArea = new JLabel("Area: ");
        floorArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        floorArea.setForeground(Color.white);

        floorPanel.add(floorNumber);
        floorPanel.add(floorSpacesCount);
        floorPanel.add(floorArea);

        buildingType = new JLabel("Type: ");
        buildingType.setFont(new Font("SansSerif", Font.PLAIN, 11));
        buildingType.setForeground(Color.white);

        buildingFloorsCount = new JLabel("Floors count: ");
        buildingFloorsCount.setFont(new Font("SansSerif", Font.PLAIN, 11));
        buildingFloorsCount.setForeground(Color.white);

        buildingArea = new JLabel("Area: ");
        buildingArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        buildingArea.setForeground(Color.white);

        buildingPanel.add(buildingType);
        buildingPanel.add(buildingFloorsCount);
        buildingPanel.add(buildingArea);

        infoPanel.add(buildingPanel);
        infoPanel.add(floorPanel);
        infoPanel.add(spacePanel);

        // init view panel
        viewPanel = new JPanel();
        viewPanel.setBackground(Color.darkGray);
        viewPanel.setLayout(new BorderLayout());
        viewPanel.setBorder(new TitledBorder(new EtchedBorder(), "Building view", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 14), Color.white));
        viewPanel.setBorder(new CompoundBorder(viewPanel.getBorder(), new EmptyBorder(5, 5, 5, 5)));

        viewBuildingPanel = new JPanel();
        viewBuildingPanel.setBackground(Color.darkGray);
        viewBuildingPanel.setLayout(new BoxLayout(viewBuildingPanel, BoxLayout.Y_AXIS));
        viewBuildingPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        viewPanel.add(viewBuildingPanel, BorderLayout.CENTER);

        mainPanel.add(infoPanel, BorderLayout.LINE_START);
        mainPanel.add(viewPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(viewBuildingPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.darkGray);
        viewPanel.add(scrollPane);

        this.add(mainPanel);
    }

    private void openBuildingActionPerformed(ActionEvent e) {
        JFileChooser dialog = new JFileChooser();
        dialog.showOpenDialog(this);
        File file = dialog.getSelectedFile();
        try {
            building = Buildings.inputBuilding(new FileInputStream(file));
            showBuilding();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "File " + file.getName() + "is not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        setVisible(true);
    }

    private void showBuilding() {
        viewBuildingPanel.removeAll();
        viewBuildingPanel.revalidate();

        int floorsCount = building.floorsCount();
        floorPanels = new FloorPanel[floorsCount];

        int spacesCount = building.spacesCount();
        spaceButtons = new SpaceButton[spacesCount--];

        buildingFloorsCount.setText("Floors count: " + floorsCount);
        buildingArea.setText("Area : " + String.format("%.2f", building.totalArea()));
        buildingType.setText("Type: " + building.getClass().getSimpleName());

        for (int i = floorPanels.length - 1; i >= 0; i--) {
            floorPanels[i] = new FloorPanel(i, building.getFloor(i));
            floorPanels[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    floorPanelMouseClicked(arg0);
                }

                @Override
                public void mouseEntered(MouseEvent arg0) {
                }

                @Override
                public void mouseExited(MouseEvent arg0) {
                }

                @Override
                public void mousePressed(MouseEvent arg0) {
                }

                @Override
                public void mouseReleased(MouseEvent arg0) {
                }
            });

            for (int j = floorPanels[i].getFloor().spacesCount() - 1; j >= 0; j--) {
                spaceButtons[spacesCount] = new SpaceButton(spacesCount, building.getSpace(spacesCount));
                spaceButtons[spacesCount].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent arg0) {
                        spaceButtonMouseClicked(arg0);
                    }

                    @Override
                    public void mouseEntered(MouseEvent arg0) {
                    }

                    @Override
                    public void mouseExited(MouseEvent arg0) {
                    }

                    @Override
                    public void mousePressed(MouseEvent arg0) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent arg0) {
                    }
                });
                floorPanels[i].add(spaceButtons[spacesCount--]);
            }
            viewBuildingPanel.add(floorPanels[i]);
        }
    }

    private void floorPanelMouseClicked(MouseEvent e) {
        FloorPanel floor = (FloorPanel) e.getComponent();
        floorNumber.setText("Number: " + floor.getNumber());
        floorSpacesCount.setText("Spaces count: " + floor.getFloor().spacesCount());
        floorArea.setText("Area: " + String.format("%.2f", floor.getFloor().totalArea()));
    }

    private void spaceButtonMouseClicked(MouseEvent e) {
        SpaceButton space = (SpaceButton) e.getComponent();
        spaceNumber.setText("Number: " + space.getNumber());
        spaceRoomsCount.setText("Rooms count: " + space.getSpace().getRoomsCount());
        spaceArea.setText("Area: " + String.format("%.2f", space.getSpace().getArea()));
    }

    private void lookAndFeelActionPerformed(ActionEvent e) {
        JRadioButtonMenuItem b = (JRadioButtonMenuItem) e.getSource();
        String className = getLookAndFeelClassName(b.getText());
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(getContentPane());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
        }
    }

    private String getLookAndFeelClassName(String name) {
        LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
        for (LookAndFeelInfo info : plafs) {
            if (info.getName().contains(name)) {
                return info.getClassName();
            }
        }
        return null;
    }
}
