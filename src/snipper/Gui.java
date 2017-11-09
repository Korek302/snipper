package snipper;

import javax.swing.JFrame;

public class Gui 
{
	JFrame frame;
	public static void main(String[] args)
	{
		Gui gui = new Gui();
		gui.GUI();
	}
	
	private void GUI()
	{
		frame = new JFrame();
		Panel panel = new Panel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Snipper");
		frame.setSize(200, 90);
		frame.getContentPane().add(panel);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}
}

