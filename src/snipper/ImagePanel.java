package snipper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class ImagePanel extends JPanel implements MouseListener, MouseMotionListener
{
	Graphics2D g2d;
	
	int[] currArgs;
	
    private BufferedImage image;
    JFrame frame;
    
    public ImagePanel(BufferedImage image) 
    {
    	super();
        this.image = image;
        
        currArgs = new int[4];
        
        addMouseListener(this);
		addMouseMotionListener(this);
    }
    
    public ImagePanel(BufferedImage image, JFrame frame) 
    {
    	super();
        this.image = image;
        this.frame = frame;
        
        currArgs = new int[4];
        
        addMouseListener(this);
		addMouseMotionListener(this);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) 
    {
    	super.paintComponent(g);
		g2d = (Graphics2D) g;
		g.drawImage(image, 0, 0, this);
    }
	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		g2d.setXORMode( Color.white );
				
		if(arg0.getX() >= currArgs[0] && arg0.getY() >= currArgs[1])
		{
			g2d.drawRect(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
			currArgs[2] = arg0.getX();
			currArgs[3] = arg0.getY();
			g2d.drawRect(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
		}
		else if(arg0.getX() < currArgs[0] && arg0.getY() >= currArgs[1])
		{
			g2d.drawRect(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
			currArgs[2] = arg0.getX();
			currArgs[3] = arg0.getY();
			g2d.drawRect(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
		}
		else if(arg0.getX() < currArgs[0] && arg0.getY() < currArgs[1])
		{
			g2d.drawRect(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
			currArgs[2] = arg0.getX();
			currArgs[3] = arg0.getY();
			g2d.drawRect(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
		}
		else if(arg0.getX() >= currArgs[0] && arg0.getY() < currArgs[1])
		{
			g2d.drawRect(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
			currArgs[2] = arg0.getX();
			currArgs[3] = arg0.getY();
			g2d.drawRect(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		currArgs[0] = arg0.getX();
		currArgs[1] = arg0.getY();
		currArgs[2] = arg0.getX();
		currArgs[3] = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		
		if(currArgs[0] != currArgs[2] && currArgs[1] != currArgs[3])
		{
			currArgs[2] = arg0.getX();
			currArgs[3] = arg0.getY();
			
			Rectangle rect;
			
			if(arg0.getX() >= currArgs[0] && arg0.getY() >= currArgs[1])
			{
				rect = new Rectangle(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
			}
			else if(arg0.getX() < currArgs[0] && arg0.getY() >= currArgs[1])
			{
				rect = new Rectangle(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
			}
			else if(arg0.getX() < currArgs[0] && arg0.getY() < currArgs[1])
			{
				rect = new Rectangle(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
			}
			else/* if(arg0.getX() >= currArgs[0] && arg0.getY() < currArgs[1])*/
			{
				rect = new Rectangle(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
			}
			
			int maxX = (int) (rect.getMaxX());
			int minX = (int) (rect.getMinX());
			int maxY = (int) (rect.getMaxY());
			int minY = (int) (rect.getMinY());
		
			BufferedImage img = new BufferedImage(maxX - minX, maxY - minY, BufferedImage.TYPE_INT_RGB);

			for(int i = 0; i < maxX - minX; i++)
			{
				for(int j = 0; j < maxY - minY; j++)
				{
					img.setRGB(i, j, image.getRGB(minX + i, minY + j));
				}
			}
			
			int index = 0;
			Scanner in = null;
			try 
			{
				in = new Scanner(new FileReader("resources\\index.txt"));
				
				StringBuilder sb = new StringBuilder();
				while(in.hasNext()) 
				{
				    sb.append(in.next());
				}
				index = Integer.parseInt(sb.toString());
				
				ImageIO.write(img, "bmp", new File("D:\\Documents\\Snipper\\snip" + index + ".bmp"));
				PrintWriter writer = new PrintWriter("resources\\index.txt");
				writer.println(index + 1);
				writer.close();
				System.out.println("Snip created successfully");
			}
			catch(IOException ex)
			{
				System.out.println("The image cannot be stored");
			}
			catch(NullPointerException nullEx)
			{
				System.out.println("Error: NULL: storing file");
			}
		}
		else
		{
			System.out.println("Rectangle too small");
		}
		frame.dispose();
	}
}
