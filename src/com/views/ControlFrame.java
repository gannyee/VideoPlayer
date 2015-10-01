package com.views;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.main.MyMain;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JLabel;

public class ControlFrame extends JFrame {

	private JPanel contentPane;
	private JButton playButton;
	private JButton backwordButton;
	private JProgressBar progressBar;
	private JSlider volumControlerSlider;
	private JButton smallButton;
	private JLabel volumLabel;
	private JPanel progressTimepanel;
	private JLabel currentLabel;
	private JLabel totalLabel;
	private JButton listButton;

	/**
	 * Create the frame,a new control frame after enter full screen model.
	 */
	public ControlFrame() {
		setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		setType(Type.UTILITY);
		setResizable(false);
		setUndecorated(true);
		setOpacity(0.5f);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 623, 66);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);

		backwordButton = new JButton("<<");
		backwordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyMain.jumpTo((float) ((progressBar.getPercentComplete() * progressBar.getWidth() - 5)
						/ progressBar.getWidth()));
			}
		});

		playButton = new JButton(">");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		playButton.addMouseListener(new MouseAdapter() {
			String btnText = ">";

			@Override
			public void mouseClicked(MouseEvent e) {
				if (playButton.getText() == ">") {
					MyMain.play();
					btnText = "||";
					playButton.setText(btnText);
				} else {
					MyMain.pause();
					btnText = ">";
					playButton.setText(btnText);
				}
			}
		});
		panel.add(playButton);
		panel.add(backwordButton);

		JButton stopButton = new JButton("Stop");
		stopButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyMain.stop();
				playButton.setText(">");
			}
		});
		panel.add(stopButton);

		JButton forwardButton = new JButton(">>");
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyMain.jumpTo((float) (((progressBar.getPercentComplete() * progressBar.getWidth() + 15))
						/ progressBar.getWidth()));
			}
		});
		panel.add(forwardButton);

		smallButton = new JButton("small");
		smallButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyMain.originalScreen();
			}
		});
		panel.add(smallButton);

		volumControlerSlider = new JSlider();
		volumControlerSlider.setPaintTicks(true);
		volumControlerSlider.setSnapToTicks(true);
		volumControlerSlider.setPaintLabels(true);
		panel.add(volumControlerSlider);
		// volumControlerSlider.setValue(MyMain.getFrame().getVolumControlerSlider().getValue());
		volumControlerSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volumControlerSlider.setValue((int) (e.getX()
						* ((float) volumControlerSlider.getMaximum() / volumControlerSlider.getWidth())));
				MyMain.getFrame().getVolumControlerSlider().setValue(volumControlerSlider.getValue());
			}
		});
		volumControlerSlider.setMaximum(120);

		volumLabel = new JLabel("0");
		panel.add(volumLabel);

		listButton = new JButton();
		listButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (listButton.getText() == "List>>") {
					MyMain.getFrame().getPlayListFrame().setVisible(true);
					MyMain.getFrame().getPlayListFrame().setBounds(
							(int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()
									- MyMain.getFrame().getPlayListFrame().getWidth(),
							0, 400, MyMain.getFrame().getHeight());
					MyMain.getFrame().getPlayListFrame().setFlag(0);
					listButton.setText("<<List");
				} else if (listButton.getText() == "<<List") {
					MyMain.getFrame().getPlayListFrame().setVisible(false);
					listButton.setText("List>>");
				}
			}
		});
		panel.add(listButton);

		progressTimepanel = new JPanel();
		contentPane.add(progressTimepanel, BorderLayout.NORTH);
		progressTimepanel.setLayout(new BorderLayout(0, 0));

		progressBar = new JProgressBar();
		progressTimepanel.add(progressBar, BorderLayout.CENTER);
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				MyMain.jumpTo(((float) x / progressBar.getWidth()));

			}
		});

		currentLabel = new JLabel("00:00");
		progressTimepanel.add(currentLabel, BorderLayout.WEST);

		totalLabel = new JLabel("00:00");
		progressTimepanel.add(totalLabel, BorderLayout.EAST);
		volumControlerSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				MyMain.setVolum(volumControlerSlider.getValue());
			}
		});


	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public JButton getPlayButton() {
		return playButton;
	}

	public JSlider getVolumControlerSlider() {
		return volumControlerSlider;
	}

	public JLabel getVolumLabel() {
		return volumLabel;
	}

	public JLabel getCurrentLabel() {
		return currentLabel;
	}

	public JLabel getTotalLabel() {
		return totalLabel;
	}

	public JButton getListButton() {
		return listButton;
	}

}
