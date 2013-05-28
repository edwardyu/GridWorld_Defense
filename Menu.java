package td;
/*
 * Menu.java
 * The GridDefense Menu that allows for placement of towers as well as a few other
 * interactions with the player
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Menu {
	
	/*
	 * Panels to add to JFrame
	 */
    private JFrame frame = new JFrame("GridDefense");
    private JPanel barrPanel = new JPanel();
    private JPanel basicPanel = new JPanel();
    private JPanel waterPanel = new JPanel();
    private JPanel firePanel = new JPanel();
    private JPanel magePanel = new JPanel();
    private JPanel moneyPanel = new JPanel();
    private JPanel consecPanel = new JPanel(); //Place consecutively
    private JPanel hsPanel = new JPanel();

    /*
     * Buttons for each panel
     * Each button has a specific function related to GridDefense gameplay
     */
    private JButton barrBtn = new JButton("Barricade (5g)");
    private JButton basicBtn = new JButton("Basic Tower (10g)");
    private JButton waterBtn = new JButton("Water Tower (50g)");
    private JButton fireBtn = new JButton("Fire Tower (100g)");
    private JButton mageBtn = new JButton("Mage Tower (1000g)");
    private JButton moneyBtn = new JButton("Money Hut (100g)");
    private JButton hsBtn = new JButton("View Highscores");
	
	private TDWorld world;
	
	/*
	 * Menu bar with instructions
	 */
    private JMenuBar mb = new JMenuBar();
    private JMenu mnuHelp = new JMenu("Help");
    private JMenuItem instructions = new JMenuItem("Instructions");
    
    /*
     * Player data to be displayed as JLabels and constantly updated whenever modified
     */
    private JLabel hp = new JLabel("HP: null", JLabel.CENTER);
    private JLabel gold = new JLabel("Gold: null", JLabel.CENTER);
    private JLabel infoLabel = new JLabel("Wave Info", JLabel.CENTER);
    private JLabel waveLabel = new JLabel("Wave: null", JLabel.CENTER);
    private JLabel minionsLabel = new JLabel("Minions Left: null", JLabel.CENTER);
    private JLabel hpLabel = new JLabel("Minion HP: null", JLabel.CENTER);
    private JLabel score = new JLabel("Score:", JLabel.CENTER);
    private JLabel[] info = new JLabel[5];

    public Menu(TDWorld world){
    	
    	this.world = world;
    	
        frame.setJMenuBar(mb);
		instructions.addActionListener(new Help());
        mnuHelp.add(instructions); 
        mb.add(mnuHelp);
        frame.getContentPane().setLayout(new GridLayout(15,1,0,0));

        /*
         * Add all panels to the game
         */
		frame.getContentPane().add(barrPanel);
		frame.getContentPane().add(basicPanel);
		frame.getContentPane().add(waterPanel);
		frame.getContentPane().add(firePanel);
		frame.getContentPane().add(magePanel);
		frame.getContentPane().add(moneyPanel);
		frame.getContentPane().add(hsPanel);
        frame.addWindowListener(new Close());

        /*
         * Add buttons to panels, as well as attach ActionListeners
         */
        barrPanel.add(barrBtn);
		barrBtn.addActionListener(new ListenBarr());
		
        basicPanel.add(basicBtn);
		basicBtn.addActionListener(new ListenBasic());
		
        waterPanel.add(waterBtn);
		waterBtn.addActionListener(new ListenWater());

        firePanel.add(fireBtn);
		fireBtn.addActionListener(new ListenFire());

        magePanel.add(mageBtn);
		mageBtn.addActionListener(new ListenMage());
		
        moneyPanel.add(moneyBtn);
        moneyBtn.addActionListener(new ListenMoney());
		
        hsPanel.add(hsBtn);
		hsBtn.addActionListener(new HS());
		
		/*
		 * Player data displays (Lives and Gold)
		 */
		hp.setText("Lives: " + world.getWorldHP());
		hp.setFont(new Font("Serif", Font.BOLD, 24));
		frame.getContentPane().add(hp);
		
		gold.setText("Gold: " + world.getGold());
		gold.setFont(new Font("Serif", Font.BOLD, 24));
		frame.getContentPane().add(gold);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane);
		
		infoLabel.setFont(new Font("Serif", Font.BOLD, 20));
		frame.getContentPane().add(infoLabel);
		waveLabel.setFont(new Font("Serif", Font.BOLD, 15));
		frame.getContentPane().add(waveLabel);
		minionsLabel.setFont(new Font("Serif", Font.BOLD, 15));
		frame.getContentPane().add(minionsLabel);
		hpLabel.setFont(new Font("Serif", Font.BOLD, 15));
		frame.getContentPane().add(hpLabel);
		score.setFont(new Font("Serif", Font.BOLD, 15));
		frame.getContentPane().add(score);
    	
    }
    
    /* 
     * Updates the current wave number, HP per minion, minions left, and score
     */
    public void updateWaveInfo() {
    	waveLabel.setText("Wave: " + world.getWave());
    	hpLabel.setText("Minion HP: " + (world.getWave()* world.getWave()* 2));
    	minionsLeft();
    	frame.repaint();
    }
    
    /*
     * Updates the number of minions left as well as the score
     */
    public void minionsLeft() {
    	minionsLabel.setText("Minions Left: " + world.getMinionsLeft());
    	score.setText("Score: " + world.getMinionsSlain());
    	frame.repaint();
    }
    
    /*
     * Updates the amount of lives left
     */
    public void updateHP() {
    	hp.setText("Lives: " + world.getWorldHP());
    	frame.repaint();
    }
    
    /*
     * Updates the current amount of gold
     */
    public void updateGold() {
    	gold.setText("Gold: " + world.getGold());
    	frame.repaint();
    }
    
    /*
     * Displays the help frame
     */
    public class Help implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	ArrayList<String> help = new ArrayList<String>();
        	try {
            	Scanner scan = new Scanner(new File("help.txt"));
        		while(scan.hasNextLine()) {
        			help.add(scan.nextLine());
        		}
        		System.out.println(help);
        		scan.close();
                JFrame helpFrame = new JFrame("Instructions");
            	helpFrame.getContentPane().setLayout(new GridLayout(24,1,0,0));
            	for(String s : help) {
            		JLabel next;
            		next = new JLabel(s);
            		next.setFont(new Font("Serif", Font.PLAIN, 14));
            		helpFrame.getContentPane().add(next);
            	}
            	helpFrame.setSize(800,600);
            	helpFrame.setLocation(500, 10);
            	helpFrame.setResizable(true);
            	helpFrame.setVisible(true);
        	} catch (IOException e2) {
        		System.out.println("Help file not found.");
        	} catch (Exception e3) {
        		e3.printStackTrace();
        	}
        }
    }
	
    /*
     * Listens to see if the Barricade button is pressed and acts accordingly
     */
	public class ListenBarr implements ActionListener{
	    public void actionPerformed(ActionEvent e){
	        world.nextType("barricade");  
	    }
	}
    /*
     * Listens to see if the Basic Tower button is pressed and acts accordingly
     */    	
    public class ListenBasic implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("basictower");  
        }
    }
    /*
     * Listens to see if the Water Tower button is pressed and acts accordingly
     */
    public class ListenWater implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("watertower");  
        }
    }
    /*
     * Listens to see if the Fire Tower button is pressed and acts accordingly
     */
    public class ListenFire implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("firetower");  
        }
    }
    /*
     * Listens to see if the Mage Tower button is pressed and acts accordingly
     */
    public class ListenMage implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("magetower");  
        }
    }
    /*
     * Listens to see if the Money Hut button is pressed and acts accordingly
     */
    public class ListenMoney implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("moneyhut");  
        }
    }
    /*
     * Listens to see if the Highscores button is pressed and acts accordingly
     */
    public class HS implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFrame hsf = new JFrame("Highscores");
        	ArrayList<Player> hs = world.getHighscores();
            hsf.getContentPane().setLayout(new GridLayout(10,2,0,0));
        	for(int k = 0; k < 20; k++) {
        		JLabel next;
        		if(k >= hs.size()) {
        			next = new JLabel(k + 1 + ". SPOT UNOCCUPIED");
            		next.setFont(new Font("Serif", Font.PLAIN, 15));
        			hsf.add(next);
        			continue;
        		}
        		next = new JLabel(k + 1 + ". " + hs.get(k).getName() + " - " + hs.get(k).getScore());
        		next.setFont(new Font("Serif", Font.PLAIN, 15));
        		hsf.add(next);
        	}
            hsf.setSize(600,400);
            hsf.setLocation(900, 10);
            hsf.setResizable(false);
            hsf.setVisible(true);
        }
    }
    /*
     * Deals with what happens when the menu is closed (game exits)
     */
    public class Close extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);         
        }
    }
	/*
	 * Shows the menu with specified settings
	 */
    public void show(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,600);
        frame.setLocation(900, 10);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}