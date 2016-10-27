package com.kamesuta.mc.signpic.gui.file;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.AbstractBorder;

public class GuiUpload {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					final GuiUpload window = new GuiUpload();
					window.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiUpload() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setLocation(100, 100);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JPanel panel = new JPanel();
		this.frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		final JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(45, 45, 45));
		panel.add(panel_1, BorderLayout.NORTH);

		final UiImage settings = new UiImage();
		try {
			settings.setImage(ImageIO.read(GuiUpload.class.getResource("/assets/signpic/textures/ui/setting.png")));
		} catch (final IOException e) {
		}

		final UiImage close = new UiImage();
		try {
			close.setImage(ImageIO.read(GuiUpload.class.getResource("/assets/signpic/textures/ui/close.png")));
		} catch (final IOException e) {
		}

		final UiImage icon = new UiImage();
		try {
			icon.setImage(ImageIO.read(GuiUpload.class.getResource("/assets/signpic/textures/logo.png")));
		} catch (final IOException e) {
		}

		final JLabel lblSignpicture = new JLabel("SignPicture!File");
		lblSignpicture.setForeground(new Color(154, 202, 71));
		lblSignpicture.setFont(new Font(Font.DIALOG, Font.BOLD, 30));

		final JLabel lblDragdropUploadFile = new JLabel("A File Uploader!");
		lblDragdropUploadFile.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
		lblDragdropUploadFile.setForeground(new Color(0, 255, 255));
		final GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(12)
								.addComponent(icon, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel_1.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
														.addComponent(settings, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
														.addGap(4)
														.addComponent(close, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
														.addGap(4))
												.addGroup(gl_panel_1.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblSignpicture)
														.addGap(16)))
										.addGroup(gl_panel_1.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblDragdropUploadFile)
												.addContainerGap()))));
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(4)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(settings, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
										.addComponent(close, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(lblSignpicture)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(lblDragdropUploadFile))
										.addComponent(icon, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
								.addContainerGap(20, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		final JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel.add(panel_2, BorderLayout.CENTER);

		final JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 255, 255));
		panel_3.setBorder(new DashedBorder());
		final GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
				gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
								.addContainerGap()
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panel_2.setVerticalGroup(
				gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
								.addContainerGap()
								.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
								.addContainerGap()));

		final JPanel panel_4 = new JPanel();
		final GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
				gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
								.addContainerGap()
								.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(388, Short.MAX_VALUE)));
		gl_panel_3.setVerticalGroup(
				gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
								.addContainerGap(117, Short.MAX_VALUE)
								.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		final JLabel lblDragImageHere = new JLabel("Drop image here");
		panel_4.add(lblDragImageHere);
		lblDragImageHere.setFont(new Font("Tahoma", Font.PLAIN, 25));

		final JLabel lblOr = new JLabel("or");
		panel_4.add(lblOr);

		final JButton button = new JButton("New button");
		panel_4.add(button);
		panel_3.setLayout(gl_panel_3);
		panel_2.setLayout(gl_panel_2);

		this.frame.pack();
	}

	static class DashedBorder extends AbstractBorder {
		@Override
		public void paintBorder(final Component comp, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D gg = (Graphics2D) g;
			gg.setColor(Color.GRAY);
			gg.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 6 }, 0));
			gg.drawRect(x, y, w-1, h-1);
		}
	}
}
