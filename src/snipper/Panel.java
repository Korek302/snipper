package snipper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener, KeyListener
{
	Graphics2D g2d;
	
	int[] currArgs;
	BufferedImage screen;
	Robot r;
	
	JButton snipButton;
	JLabel iconLabel;

	public Panel()
	{
		super();
		setBackground(new Color(150, 150, 100));
		
		currArgs = new int[4];
		
		try 
		{
			r = new Robot();
		} 
		catch (AWTException e) 
		{
			System.out.println("Error: Robot declaration");
		}
		
		screen = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		
		
				
		setLayout(null);
		snipButton = new JButton("Snip");
		snipButton.setBounds(100, 10, 70, 30);
		snipButton.setMnemonic(KeyEvent.VK_Z);
		
		add(snipButton);
		snipButton.addActionListener(this);
		
		BufferedImage iconImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		try
		{
			iconImage = ImageIO.read(new File("D:\\GitHub\\snipper\\icon2.bmp"));
		} 
		catch (IOException e) 
		{
			System.out.println("Error: connot load iconImage");
		}
		iconLabel = new JLabel(new ImageIcon(iconImage));
		iconLabel.setBounds(10, 5, 40, 40);
		add(iconLabel);
		
		addKeyListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g2d = (Graphics2D) g;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if(source == snipButton)
		{
			screen = r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			
			JFrame screenFrame = new JFrame();
			
			screenFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			screenFrame.setTitle("Snipper");
			screenFrame.getContentPane().add(new ImagePanel(screen, screenFrame));
			screenFrame.setLocationRelativeTo(null);
			screenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			screenFrame.setUndecorated(true);
			screenFrame.setVisible(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
