/*
 * Copyright (c) 2000-2017 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.FlowLayout;
import java.awt.Canvas;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * The example demonstrates how to integrate UI built with HTML+CSS+JavaScript
 * into Java desktop application.
 */
public class Regex {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("HTMLUISample");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(contentPane, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        panel.setBounds(12, 59, 229, 564);
        contentPane.add(panel);
        frame.setSize(857, 725);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
