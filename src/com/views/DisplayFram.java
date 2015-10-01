package com.views;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.main.MyMain;
import com.play_list.PlayListFrame;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JProgressBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSlider;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DisplayFram extends JFrame {

	private JPanel contentPane;

	EmbeddedMediaPlayerComponent playerComponent;
	private JPanel panel;
	private JButton stopButton;
	private JButton playButton;
	private JPanel controlPanel;
	private JProgressBar progressBar;
	private JSlider volumControlerSlider;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenVideo;
	private JMenuItem mntmOpenSubtitle;
	private JMenuItem mntmExit;
	private JButton forwardButton;
	private JButton backwordButton;
	private JButton FullScreenButton;
	private int flag = 0;
	private KeyBordListenerEven kble;
	private JLabel volumLabel;
	private JPanel progressTimePanel;
	private JLabel currentLabel;
	private JLabel totalLabel;
	private static PlayListFrame playListFrame;
	private JButton listButton;
	/**
	 * Create the frame.
	 */
	public DisplayFram() {
		playListFrame = new PlayListFrame();
		setIconImage(new ImageIcon("picture/icon.png").getImage());
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				if(playListFrame.getFlag() == 0){
					playListFrame.setVisible(true);
					playListFrame.setBounds(MyMain.getFrame().getX() + MyMain.getFrame().getWidth() - 15, MyMain.getFrame().getY(),400,MyMain.getFrame().getHeight());
				}
			}
			@Override
			public void componentResized(ComponentEvent e) {
				
				if(playListFrame.getFlag() == 0 && !MyMain.getFrame().getMediaPlayer().isFullScreen()){
					playListFrame.setVisible(true);
					if(Math.abs(MyMain.getFrame().getWidth() - Toolkit.getDefaultToolkit().getScreenSize().width ) <= 20){
						playListFrame.setBounds((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 400), 0,400,MyMain.getFrame().getHeight());
						playListFrame.setAlwaysOnTop(true);
					}
					else
						playListFrame.setBounds(MyMain.getFrame().getX() + MyMain.getFrame().getWidth() - 15, MyMain.getFrame().getY(),400,MyMain.getFrame().getHeight());
				}
				
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 653, 394);
		kble = new KeyBordListenerEven();
		kble.keyBordListner();
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmOpenVideo = new JMenuItem("Open Video");
		mntmOpenVideo.setSelected(true);
		mnFile.add(mntmOpenVideo);

		mntmOpenSubtitle = new JMenuItem("Open Subtitle");
		mnFile.add(mntmOpenSubtitle);

		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		mntmOpenVideo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyMain.openVedio();
				playListFrame.setList(MyMain.getListView().getList());
				playListFrame.getScrollPane().setViewportView(playListFrame.getList());
			}
		});

		mntmOpenSubtitle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyMain.openSubtitle();
			}
		});

		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MyMain.exit();
			}
		});

		contentPane = new JPanel();
		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				System.out.println();
			}
		});
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel videoPanel = new JPanel();
		contentPane.add(videoPanel, BorderLayout.CENTER);
		videoPanel.setLayout(new BorderLayout(0, 0));

		playerComponent = new EmbeddedMediaPlayerComponent();
		final Canvas videoSurface = playerComponent.getVideoSurface();
		videoSurface.addMouseListener(new MouseAdapter() {
				String btnText = ">";
				String btnText1 = "Full";
				Timer mouseTime;
				
				@Override
				public void mouseClicked(MouseEvent e) {
				int i = e.getButton();
				if (i == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 1) {
						mouseTime = new Timer(350, new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if (playButton.getText() == ">") {
									MyMain.play();
									btnText = "||";
									playButton.setText(btnText);
								} else {
									MyMain.pause();
									btnText = ">";
									playButton.setText(btnText);
								}
								mouseTime.stop();
							}
						});
						mouseTime.restart();
					} else if (e.getClickCount() == 2 && mouseTime.isRunning()) {
						mouseTime.stop();
						if (flag == 0) {
							MyMain.fullScreen();
						} else if (flag == 1) {
							MyMain.originalScreen();
						}
					}
				}
			}

		});
		videoPanel.add(playerComponent, BorderLayout.CENTER);
		panel = new JPanel();
		videoPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		controlPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) controlPanel.getLayout();
		panel.add(controlPanel);
		
				playButton = new JButton(">");
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
				controlPanel.add(playButton);
		
		backwordButton = new JButton("<<");
		backwordButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MyMain.jumpTo((float)((progressBar.getPercentComplete() * progressBar.getWidth() - 5) / progressBar.getWidth()));
			}
		});
		controlPanel.add(backwordButton);

		volumControlerSlider = new JSlider();
		volumControlerSlider.setPaintLabels(true);
		volumControlerSlider.setSnapToTicks(true);
		volumControlerSlider.setPaintTicks(true);
		volumControlerSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volumControlerSlider.setValue((int)(e.getX() * ((float)volumControlerSlider.getMaximum() / volumControlerSlider.getWidth())));
			//	volumLabel.setText("" + volumControlerSlider.getValue());
			}
			
		});
		volumControlerSlider.setValue(100);
		volumControlerSlider.setMaximum(120);
		volumControlerSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				MyMain.setVolum(volumControlerSlider.getValue());
			}
		});
		
		forwardButton = new JButton(">>");
		forwardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				MyMain.jumpTo((float)(((progressBar.getPercentComplete() * progressBar.getWidth() + 10)) / progressBar.getWidth()));
			}
		});
		
				stopButton = new JButton("Stop");
				
						stopButton.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								MyMain.stop();
								playButton.setText(">");
							}
						});
						controlPanel.add(stopButton);
		controlPanel.add(forwardButton);
		
		FullScreenButton = new JButton("Full");
		FullScreenButton.addMouseListener(new MouseAdapter() {
			String btnText = "Full";
			int flag = 0;
			@Override
			public void mouseClicked(MouseEvent e) {
	
				MyMain.fullScreen();			
			}
		});
		controlPanel.add(FullScreenButton);

		controlPanel.add(volumControlerSlider);
		
		volumLabel = new JLabel("" + volumControlerSlider.getValue());
		controlPanel.add(volumLabel);
		
		listButton = new JButton();
		if(playListFrame.getFlag() == 1){
			listButton.setText("List>>");
		}
		else if(playListFrame.getFlag() == 0){
			listButton.setText("<<List");
		}
		listButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(listButton.getText() == "List>>"){
					playListFrame.setVisible(true);
					if (Math.abs(MyMain.getFrame().getWidth() - Toolkit.getDefaultToolkit().getScreenSize().width) <= 20)
						playListFrame.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 400, 0, 400,
								MyMain.getFrame().getHeight());
					else
						playListFrame.setBounds(MyMain.getFrame().getX() + MyMain.getFrame().getWidth() - 15,
								MyMain.getFrame().getY(), 400, MyMain.getFrame().getHeight());
					playListFrame.setFlag(0);
					listButton.setText("<<List");
				}else if(listButton.getText() == "<<List"){
					playListFrame.setVisible(false);
					listButton.setText("List>>");
				}
				

			}
		});
		controlPanel.add(listButton);
		
		progressTimePanel = new JPanel();
		panel.add(progressTimePanel, BorderLayout.NORTH);
		progressTimePanel.setLayout(new BorderLayout(0, 0));
		
		progressBar = new JProgressBar();
		progressTimePanel.add(progressBar, BorderLayout.CENTER);
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				MyMain.jumpTo(((float) x / progressBar.getWidth()));

			}
		});
		
		currentLabel = new JLabel("00：00");
		progressTimePanel.add(currentLabel, BorderLayout.WEST);
		
		totalLabel = new JLabel("02：13");
		progressTimePanel.add(totalLabel, BorderLayout.EAST);
	}

	// Get the video
	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public EmbeddedMediaPlayerComponent getPlayComponent(){
		return playerComponent;
	}
	
	public JButton getPlayButton(){
		return playButton;
	}

	public JPanel getControlPanel() {
		return controlPanel;
	}


	public void setFlag(int flag){
		this.flag = flag;
	}

	public int getFlag() {
		return flag;
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

	public JPanel getProgressTimePanel() {
		return progressTimePanel;
	}

	public JButton getListButton() {
		return listButton;
	}

	public static PlayListFrame getPlayListFrame() {
		return playListFrame;
	}
	
}
