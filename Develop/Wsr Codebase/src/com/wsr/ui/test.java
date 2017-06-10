package com.wsr.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A simple Java wait cursor example.
 * @author Alvin Alexander, devdaily.com
 */
public class test implements ActionListener
{
  private JFrame frame;
  private String defaultButtonText = "Show Wait Cursor";
  private JButton button = new JButton(defaultButtonText);
  private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
  private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
  private boolean waitCursorIsShowing;
  
  public static void main(String[] args)
  {
    new test();
  }

  public test()
  {
    // our class is the actionlistener on the button
    button.addActionListener(this);

    // note: this should be inside a SwingUtilities.invokeLater call.
    // see: http://devdaily.com/java/jframe-example
    frame = new JFrame("Java Wait Cursor Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(button);
    frame.setPreferredSize(new Dimension(400,300));
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent evt)
  {
    if (waitCursorIsShowing)
    {
      // set the cursor back to the default
      waitCursorIsShowing = false;
      button.setText(defaultButtonText);
      frame.setCursor(defaultCursor);
    }
    else
    {
      // change the cursor to the wait cursor
      waitCursorIsShowing = true;
      button.setText("Back to Default Cursor");
      frame.setCursor(waitCursor);
    }
  }
  
}