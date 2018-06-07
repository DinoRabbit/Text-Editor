import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

//Simple Text Editor similar to notepad

public class textEditor extends JFrame
{
	
	private JTextArea text = new JTextArea(20, 120);
	private String currentFile = new String("Untitiled");
	
	
	//Constructor, right now, we just make a window where we can type things, but nothing else.
	public textEditor()
	{
		text.setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);
		
		setTitle(currentFile);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public static void main(String [] args)
	{
		new textEditor();
	}
}