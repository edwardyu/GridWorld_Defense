package td;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Menu {
	
    private JFrame frame = new JFrame("GridDefense");
    private JPanel barrPanel = new JPanel();
    private JPanel basicPanel = new JPanel();
    private JPanel waterPanel = new JPanel();
    private JPanel firePanel = new JPanel();
    private JPanel magePanel = new JPanel();
    private JPanel moneyPanel = new JPanel();
    private JPanel consecPanel = new JPanel(); //Place consecutively
    private JPanel hsPanel = new JPanel();

    private JButton barrBtn = new JButton("Barricade (5g)");
    private JButton basicBtn = new JButton("Basic Tower (10g)");
    private JButton waterBtn = new JButton("Water Tower (50g)");
    private JButton fireBtn = new JButton("Fire Tower (100g)");
    private JButton mageBtn = new JButton("Mage Tower (1000g)");
    private JButton moneyBtn = new JButton("Money Hut (100g)");
    private JButton consecBtn = new JButton("Toggle Consecutive Placement");
    private JButton hsBtn = new JButton("View Highscores");
	
	private TDWorld world;
		
    private JMenuBar mb = new JMenuBar();
    private JMenu mnuHelp = new JMenu("Help");
    private JMenuItem instructions = new JMenuItem("Instructions");
    
    private JLabel hp = new JLabel("HP: null", JLabel.CENTER);
    private JLabel gold = new JLabel("Gold: null", JLabel.CENTER);
    private JLabel infoLabel = new JLabel("Game Output", JLabel.CENTER);
    private JLabel[] info = new JLabel[5];

    public Menu(TDWorld world){
    	this.world = world;
    	
        frame.setJMenuBar(mb);
		instructions.addActionListener(new Help());
        mnuHelp.add(instructions); 
        mb.add(mnuHelp);
        frame.getContentPane().setLayout(new GridLayout(20,1,0,0));

		frame.getContentPane().add(barrPanel);
		frame.getContentPane().add(basicPanel);
		frame.getContentPane().add(waterPanel);
		frame.getContentPane().add(firePanel);
		frame.getContentPane().add(magePanel);
		frame.getContentPane().add(moneyPanel);
		frame.getContentPane().add(consecPanel);
		frame.getContentPane().add(hsPanel);
        frame.addWindowListener(new Close());

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
		
        consecPanel.add(consecBtn);
		consecBtn.addActionListener(new Consecutive());
		
        hsPanel.add(hsBtn);
		hsBtn.addActionListener(new HS());
		
		hp.setText("HP: " + world.getWorldHP());
		hp.setFont(new Font("Serif", Font.BOLD, 24));
		frame.getContentPane().add(hp);
		
		gold.setText("Gold: " + world.getGold());
		gold.setFont(new Font("Serif", Font.BOLD, 24));
		frame.getContentPane().add(gold);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		frame.getContentPane().add(splitPane);
		
		infoLabel.setFont(new Font("Serif", Font.BOLD, 20));
		frame.getContentPane().add(infoLabel);
		
    	for(int k = 0; k < info.length; k++) {
    		info[k] = new JLabel("", JLabel.CENTER);
    		info[k].setFont(new Font("Serif", Font.PLAIN, 12));
    		frame.getContentPane().add(info[k]);
    	}
    	
    }
    
    public void output(String s) {
    	for(int k = info.length - 1; k > 0; k--) {
    		info[k].setText(info[k - 1].getText());
    	}
    	info[0].setText(s);
    	frame.repaint();
    }
    
    public void updateHP() {
    	hp.setText("HP: " + world.getWorldHP());
    	frame.repaint();
    }
    
    public void updateGold() {
    	gold.setText("Gold: " + world.getGold());
    	frame.repaint();
    }
    
    public class Help implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	System.out.println("hi there this isn't done");
        }
    }
	
	public class ListenBarr implements ActionListener{
	    public void actionPerformed(ActionEvent e){
	        world.nextType("barricade");  
	    }
	}
    	
    public class ListenBasic implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("basictower");  
        }
    }
    
    public class ListenWater implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("watertower");  
        }
    }
    
    public class ListenFire implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("firetower");  
        }
    }
    
    public class ListenMage implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("magetower");  
        }
    }
    
    public class ListenMoney implements ActionListener{
        public void actionPerformed(ActionEvent e){
            world.nextType("moneyhut");  
        }
    }
    
    public class Consecutive implements ActionListener{
        public void actionPerformed(ActionEvent e){
            boolean current = world.cheater();  
            System.out.println(current ? "You can now place structures without re-clicking them!" : "Consecutive placement has been turned off.");
        }
    }
    
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
    
    public class Close extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);         
        }
    }
	
    public void show(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,800);
        frame.setLocation(900, 10);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}