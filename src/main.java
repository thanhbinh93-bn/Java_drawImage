import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testComposite();
		new AlphaCompositeDemo();
	}
	
	 /**
	   * ����¬��
	   */
	  public static void testComposite() {
	    //��Ω�ط�Ԧ�ҡ���Ʃ��ʬ��Ū��
	    BufferedImage bg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D bgGraphics = (Graphics2D) bg.getGraphics();
	    bgGraphics.setColor(Color.yellow); //��������
	    bgGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //�����
	    bgGraphics.fillRect(0, 0, bg.getWidth(), bg.getHeight()); //Ŷ�����
	    bgGraphics.setColor(Color.BLACK);
	    bgGraphics.setFont(new Font("ܴ��", Font.ITALIC, 50));
	    bgGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //ʸ��������
	    bgGraphics.drawString("�طʿ�", 50, 150); //��ʸ��
	    bgGraphics.dispose();

	    //��Ω����ĥԦ��
	    BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
	    imageGraphics.setColor(Color.GREEN);
	    imageGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    imageGraphics.fillRoundRect(0, 0, image.getWidth(), image.getHeight(), image.getWidth(), image.getHeight());
	    imageGraphics.setColor(Color.BLACK);
	    imageGraphics.setFont(new Font("ܴ��", Font.ITALIC, 50));
	    imageGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    imageGraphics.drawString("���ʿ�", 50, 200);
	    imageGraphics.dispose();

	    JFrame jf = new JFrame(); //ɽ��
	    JPanel contentPanel = new JPanel(); //������
	    contentPanel.setBorder(BorderFactory.createLineBorder(Color.blue)); //�������
	    //contentPanel.setLayout(new BorderLayout());

	    //��ɸ��
	    JLabel label = new JLabel();
	    label.setText("�ȹ��ϼ���");
	    contentPanel.add(label);

	    //��������
	    JComboBox comboBox = new JComboBox();
	    comboBox.addItem("����");
	    comboBox.addItem("CLEAR");
	    comboBox.addItem("SRC");
	    comboBox.addItem("DST");
	    comboBox.addItem("SRC_OVER");
	    comboBox.addItem("DST_OVER");
	    comboBox.addItem("SRC_IN");
	    comboBox.addItem("DST_IN");
	    comboBox.addItem("SRC_OUT");
	    comboBox.addItem("DST_OUT");
	    comboBox.addItem("SRC_ATOP");
	    comboBox.addItem("DST_ATOP");
	    comboBox.addItem("XOR");
	    contentPanel.add(comboBox);

	    JLabel label2 = new JLabel();
	    label2.setText("��Ԧ��Ʃ���١�");
	    contentPanel.add(label2);

	    //����ư��([0-10]=>[0,0.1,0.2,.... 1.0])
	    final JSlider jSlider = new JSlider(0,10,10);
	    jSlider.setMajorTickSpacing(5); // �������ٴֳ�
	    jSlider.setMinorTickSpacing(1); // ���꼡���ٴֳ�
	    jSlider.setPaintTicks(true); // ��������
	    jSlider.setPaintLabels(true); // ����ɸ��
	    jSlider.setSnapToTicks(true); // ��¶�����ټ�
	     /*
	     * �����Ū�������������ɸ��
	     */
	    Hashtable<Integer, JComponent> hashtable = new Hashtable<Integer, JComponent>();
	    hashtable.put(0, new JLabel("0"));   // 0 ���ٰ��֡����� "0"
	    hashtable.put(5, new JLabel("0.5"));  // 5 ���ٰ��֡����� "0.5"
	    hashtable.put(10, new JLabel("1"));    // 10 ���ٰ��֡����� "1"
	    jSlider.setLabelTable(hashtable); //������¼����ɸ��Ū������������������������Ժ���������Ū���١�

	    contentPanel.add(jSlider);


	    jf.setContentPane(contentPanel); //ɽ�����������İ�jp
	    jf.setBounds(200, 200, 500, 500);
	    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jf.setVisible(true); //ɽ�Ųĸ�


	    final DrawingPanel drawPanel = new DrawingPanel();
	    drawPanel.setBounds(0,60,500,415);
	    drawPanel.setPreferredSize(new Dimension(500,415));
	    drawPanel.setBorder(BorderFactory.createLineBorder(Color.red)); //�������
	    drawPanel.setBg(bg);
	    drawPanel.setImage(image);
	    // drawPanel.setAlphaComposite(AlphaComposite.SrcAtop);
	    contentPanel.add(drawPanel);

	    final Map<String,AlphaComposite> compositeMap = new HashMap<>();
	    compositeMap.put("CLEAR",AlphaComposite.Clear);
	    compositeMap.put("SRC",AlphaComposite.Src);
	    compositeMap.put("DST",AlphaComposite.Dst);
	    compositeMap.put("SRC_OVER",AlphaComposite.SrcOver);
	    compositeMap.put("DST_OVER",AlphaComposite.DstOver);
	    compositeMap.put("SRC_IN",AlphaComposite.SrcIn);
	    compositeMap.put("DST_IN",AlphaComposite.DstIn);
	    compositeMap.put("SRC_OUT",AlphaComposite.SrcOut);
	    compositeMap.put("DST_OUT",AlphaComposite.DstOut);
	    compositeMap.put("SRC_ATOP",AlphaComposite.SrcAtop);
	    compositeMap.put("DST_ATOP",AlphaComposite.DstAtop);
	    compositeMap.put("XOR",AlphaComposite.Xor);
	    //�������������
	    comboBox.addItemListener(new ItemListener() {
	      @Override
	      public void itemStateChanged(ItemEvent e) {
	        if(e.getStateChange() == ItemEvent.SELECTED){
	          String selected = e.getItem().toString();
	          System.out.println(selected);
	          drawPanel.setAlphaComposite(compositeMap.get(selected));
	          drawPanel.repaint(); //����
	        }
	      }
	    });

	    //��ư����λ���
	    jSlider.addChangeListener(new ChangeListener() {
	      @Override
	      public void stateChanged(ChangeEvent e) {
	        int val = jSlider.getValue();
	        float alpha = (float) (val / 10.0);
	        System.out.println("alpha:"+alpha);
	        drawPanel.setAlpha(alpha);
	        drawPanel.repaint(); //����
	      }
	    });

	    //ɽ�Ų��λ���
	    jf.addWindowStateListener(new WindowStateListener() {

	      @Override
	      public void windowStateChanged(WindowEvent e) {
	        System.out.println("window state:"+e.paramString());
	      }
	    });
	  }


	  static class DrawingPanel extends JPanel{

	    BufferedImage bg;
	    BufferedImage image;
	    AlphaComposite alphaComposite;
	    float alpha = 1.0f; //��Ԧ��Ʃ���١ʿ���ŪԦ��

	    public BufferedImage getBg() {
	      return bg;
	    }

	    public void setBg(BufferedImage bg) {
	      this.bg = bg;
	    }

	    public BufferedImage getImage() {
	      return image;
	    }

	    public void setImage(BufferedImage image) {
	      this.image = image;
	    }

	    public AlphaComposite getAlphaComposite() {
	      return alphaComposite;
	    }

	    public void setAlphaComposite(AlphaComposite alphaComposite) {
	      this.alphaComposite = alphaComposite;
	    }

	    public float getAlpha() {
	      return alpha;
	    }

	    public void setAlpha(float alpha) {
	      this.alpha = alpha;
	    }

	    /**
	     * ����paint��ˡ
	     * @param g
	     */
	    @Override
	    public void paint(Graphics g){
	      //�ƶ�Ūsuper.paint(g),����������캳����Ū���ǡ�ſ��������
	      super.paint(g);

	      //�����ľ�����
	      Graphics2D g2d = (Graphics2D)g;
	      g2d.setComposite(AlphaComposite.Src);
	      g2d.drawImage(bg,100,100,null); //�ط�Ԧ
	      if(alphaComposite!=null) {
	        g2d.setComposite(alphaComposite.derive(alpha));
	      }else{
	        //����SrcOver
	        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
	      }
	      g2d.drawImage(image,100,100,null); //�Ȳ�Ԧ

	      //g2d.setColor(Color.GREEN);
	      //g2d.fillRoundRect(100,100,image.getWidth(),image.getHeight(),image.getWidth(),image.getHeight()); //�Ȳ�Ԧ��
	      g2d.dispose();
	    }
	  }

}
