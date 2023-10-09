import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author zachgendreau
 * Frame for displaying snake
 * settings. Contains components that
 * may be changed to set specific settings.
 *
 */
@SuppressWarnings("serial")
public class SnakeSettingsPanel extends JPanel {
	private JLabel width = new JLabel("Width");
	private JLabel height = new JLabel("Height");
	private JLabel speed = new JLabel("Speed");
	private JLabel color = new JLabel("Snake Color");
	private JTextField textWidth;
	private JTextField textHeight;
	@SuppressWarnings({ "rawtypes" })
	private JComboBox speedBox;
	@SuppressWarnings("rawtypes")
	private JComboBox snakeColor;
	private SnakeSettings settings;

	
	/**
	 * 
	 * @param settings
	 * Creates snake settings panel by creating initial
	 * components and calling setupSetGUI()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SnakeSettingsPanel(SnakeSettings settings) {
		this.settings = settings;
		textHeight = new JTextField(settings.getWidth() + "", 20);
		textWidth = new JTextField(settings.getHeight() + "", 20);
		speedBox = new JComboBox(SnakeSettings.Speed.values());
		snakeColor = new JComboBox(SnakeSettings.SnakeColor.values());
		speedBox.setSelectedItem(settings.getSpeed());
		snakeColor.setSelectedItem(settings.getColor());
		setupSetGUI();
	
	}
	
	/**
	 * Sets up actual panel. called from constructor
	 */
	public void setupSetGUI() {
	    speedBox.setPreferredSize(new Dimension(120, 30));
	    speedBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SnakeSettings.Speed speed = (SnakeSettings.Speed)speedBox.getSelectedItem();
				settings.setSpeed(speed);

			}
	    
	    });
	    snakeColor.setPreferredSize(new Dimension(120, 30));
	    snakeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SnakeSettings.SnakeColor color = (SnakeSettings.SnakeColor)snakeColor.getSelectedItem();
				settings.setColor(color);
				
			}
	    
	    });
	    textHeight.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				settings.setWidth(Integer.parseInt(textHeight.getText()));
				
			}
	    	
	    });
	    
	    textWidth.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				settings.setHeight(Integer.parseInt(textWidth.getText()));
				
			}
	    	
	    });

		this.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        
        constraints.gridx = 0;
        constraints.gridy = 0;     
        this.add(width, constraints);
       
        constraints.gridx = 1;
        this.add(textWidth, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        this.add(height, constraints);
         
        constraints.gridx = 1;
        this.add(textHeight, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 2;     
        this.add(speed, constraints);
       
        constraints.gridx = 1;
        this.add(speedBox, constraints);
        
        constraints.gridx = 0;
        constraints.gridy = 3;     
        this.add(color, constraints);
       
        constraints.gridx = 1;
        this.add(snakeColor, constraints);
        
	}
	
	// main method
	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeSettings settings = new SnakeSettings();
		SnakeSettingsPanel sp = new SnakeSettingsPanel(settings);
		frame.getContentPane().add(sp);
		frame.pack();
		frame.setVisible(true);
	}

	
}
