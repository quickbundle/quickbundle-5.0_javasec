package org.quickbundle.tools.support.cn2spell;

/** 
 * @(#)CnToSpellGUI.java 
 * kindani 
 * 2004-10-25?? 
 * */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 * <pre></pre>
 * 
 * <BR>
 * <DL>
 * <DT><B>JDK版本 </B></DT>
 * <BR>
 * <DD>1.4</DD>
 * </DL>
 * 
 * @author KIN
 * @version 1.0
 * @see
 * @since 1.0
 */
public class Cn2Spell2GUI extends JFrame {

	private Cn2Spell2GUI c = null;

	public Cn2Spell2GUI() {
		super("Cn to Spell");
		setSize(800, 100);
		getContentPane().setLayout(new FlowLayout());
		// component layout
		JTextArea from = new JTextArea(5, 20);
		JTextArea to = new JTextArea(5, 20);
		JButton b = new JButton("cn to pinyin");
		getContentPane().add(new JLabel("From:"));
		getContentPane().add(from);
		getContentPane().add(b);
		getContentPane().add(new JLabel("To:"));
		getContentPane().add(to);
		// action handle
		b.addActionListener(new Cn2PinyinActionListener(from, to));
		setVisible(true);
		// set this for pack
		c = this;
	}

	/**
	 * button action listener to convert text to pinyin from one textbox to
	 * another textbox
	 */
	class Cn2PinyinActionListener implements ActionListener {

		private JTextArea from = null;

		private JTextArea to = null;

		public Cn2PinyinActionListener(JTextArea from, JTextArea to) {
			this.from = from;
			this.to = to;
		}

		public void actionPerformed(ActionEvent e) {
			if (from.getText().length() == 0) {
				JOptionPane.showMessageDialog(from, "From text is empty!",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
			String text = from.getText();
			to.setText(Cn2Spell.getFullSpell(text));
			c.pack();
		}
	}

	public static void main(String[] args) {
		Cn2Spell2GUI g = new Cn2Spell2GUI();
		System.out.println(g);
	}
}

