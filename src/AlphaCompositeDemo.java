import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class AlphaCompositeDemo extends JFrame {
		  MyCanvas canvas;		  
		  
		  JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);

		  JComboBox bottomLayerRuleBox;
		  JComboBox topLayerRuleBox;

		  String[] rulesLabels = { "CLEAR", "SRC", "DST", "SRC_OVER",
		      "DST_OVER", "SRC_IN", "DST_IN", "SRC_OUT",
		      "DST_OUT","SRC_ATOP","DST_ATOP","XOR" };

		  int[] rules = { AlphaComposite.CLEAR, AlphaComposite.SRC,AlphaComposite.DST,
		      AlphaComposite.SRC_OVER, AlphaComposite.DST_OVER,
		      AlphaComposite.SRC_IN, AlphaComposite.DST_IN,
		      AlphaComposite.SRC_OUT, AlphaComposite.DST_OUT,
		      AlphaComposite.SRC_ATOP, AlphaComposite.DST_ATOP,
		      AlphaComposite.XOR};

		  public AlphaCompositeDemo() {
		    super();
		    Container container = getContentPane();

		    canvas = new MyCanvas();
		    canvas.setBackground(Color.white);
		    container.add(canvas);

		    bottomLayerRuleBox = new JComboBox(rulesLabels);
		    bottomLayerRuleBox.setSelectedIndex(0);
		    bottomLayerRuleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		    bottomLayerRuleBox.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox) e.getSource();
		        canvas.compositeRule = rules[cb.getSelectedIndex()];
		        canvas.repaint();
		      }
		    });

		    slider.setPaintTicks(true);
		    slider.setMajorTickSpacing(25);
		    slider.setMinorTickSpacing(25);
		    slider.setPaintLabels(true);
		    slider.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		        JSlider slider = (JSlider) e.getSource();
		        canvas.alphaValue = (float) slider.getValue() / 100;
		        canvas.repaint();
		      }
		    });

		    
		    
		    JPanel panel = new JPanel();
		    panel.setLayout(new GridLayout(1,3));
		    panel.add(bottomLayerRuleBox);
		    panel.add(new JLabel("Alpha Adjustment x E-2: ", JLabel.RIGHT));
		    panel.add(slider);
		    container.add(panel, BorderLayout.SOUTH);
		    

		    addWindowListener(new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		        System.exit(0);
		      }
		    });
		    setSize(500,300);
		    setVisible(true);
		  }
		  
		  
	class MyCanvas extends JLabel {
		float alphaValue = 1.0f;

		int compositeRule = AlphaComposite.CLEAR;

		AlphaComposite ac;

		ImageFilter filter = new RGBImageFilter() {
			int transparentColor = Color.white.getRGB() | 0xFF000000;

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == transparentColor) {
					return 0x00FFFFFF & rgb;
				} else {
					return rgb;
				}
			}
		};

		public void paint(Graphics g) {
			Image imgSpot = Toolkit.getDefaultToolkit().getImage("src/yellowCircle.png");

			MediaTracker tracker = new MediaTracker(this);
			tracker.addImage(imgSpot, 0);
			try {
				tracker.waitForID(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {

				Graphics2D g2D = (Graphics2D) g;
				g2D.setColor(Color.black);
				g2D.fillRect(0, 0, this.getWidth(), this.getHeight());
				int imageSpotWidth = imgSpot.getWidth(null);
				int imageSpotHeight = imgSpot.getHeight(null);

				BufferedImage objTempSpotImage = new BufferedImage(imageSpotWidth, imageSpotHeight,
						BufferedImage.TYPE_INT_ARGB);
				objTempSpotImage.createGraphics().drawImage(imgSpot, 0, 0, null);

				g2D.drawImage(objTempSpotImage, 20, 20, null);

			} catch (Exception ee) {
				ee.printStackTrace();
			}

		}
	}

}
