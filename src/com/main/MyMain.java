package com.main;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import com.history.ListHistory;
import com.play_list.ViewList;
import com.sun.jna.NativeLibrary;
import com.views.ControlFrame;
import com.views.DisplayFram;
import com.views.MyLogo;
import com.views.VideoTime;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * Main function
 * @author ganyee
 *
 */
public class MyMain {

	private static DisplayFram frame;
	private static String filePath;
	private static ControlFrame controlFrame;
	private static VideoTime videoTime;
	private static ViewList listView = new ViewList();
	private static ListHistory listHistory = new ListHistory();

	public static void main(String[] args) {
		try {
			listView.setList(listHistory.readHistory());
			listView.setMap(listHistory.readHistoryMap());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Decide the platform
		if (RuntimeUtil.isWindows())
			filePath = "D:\\VideoLAN\\VLC";
		else if (RuntimeUtil.isMac())
			filePath = "/Applications/VLC.app/Contents/MacOS/lib";
		else if (RuntimeUtil.isNix())
			filePath = "/home/linux/vlc/install/lib";

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), filePath);
		System.out.println(LibVlc.INSTANCE.libvlc_get_version());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new DisplayFram();
					frame.setVisible(true);
					controlFrame = new ControlFrame();

					videoTime = new VideoTime();
					String[] optionDecode = { "--subsdec-encoding=GB18030" };
					// frame.getMediaPlayer().playMedia("D:\\Downloads\\ELECTROSHOCK
					// - ESMA 2011-SD.mp4",optionDecode);
					//frame.getMediaPlayer().prepareMedia("D:\\Downloads\\ELECTROSHOCK - ESMA 2011-SD.mp4", optionDecode);
					//Publish progress of movie and get the total time and current time
					new SwingWorker<String, Integer>() {

						@Override
						protected String doInBackground() throws Exception {
							while (true) {
								//current time and total time
								long totalTime = frame.getMediaPlayer().getLength();
								long currentTime = frame.getMediaPlayer().getTime();
								videoTime.timeCalculate(totalTime, currentTime);
								frame.getCurrentLabel()
										.setText(videoTime.getMinitueCurrent() + ":" + videoTime.getSecondCurrent());
								frame.getTotalLabel()
										.setText(videoTime.getMinitueTotal() + ":" + videoTime.getSecondTotal());
								controlFrame.getCurrentLabel().setText(frame.getCurrentLabel().getText());
								controlFrame.getTotalLabel().setText(frame.getTotalLabel().getText());
								
								//Get the percent of the current movie progress
								float percent = (float) currentTime / totalTime;
								publish((int) (percent * 100));
								Thread.sleep(200);
							}
						}

						protected void process(java.util.List<Integer> chunks) {
							for (int v : chunks) {
								frame.getProgressBar().setValue(v);
								controlFrame.getProgressBar().setValue(v);
							}
						};
					}.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//Play the opened movie
	public static void play() {
		frame.getMediaPlayer().play();
		frame.getPlayButton().setText("||");

	}

	//Pause
	public static void pause() {
		frame.getMediaPlayer().pause();
		frame.getPlayButton().setText(">");
	}

	//Stop
	public static void stop() {
		frame.getMediaPlayer().stop();
		frame.getPlayButton().setText(">");
	}

	//Forward
	public static void forword(float to) {
		frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
	}

	//Backword
	public static void backword() {
		frame.getPlayComponent().backward(frame.getMediaPlayer());
	}

	//Set current progress time
	public static void jumpTo(float to) {
		frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
	}

	//Set volume
	public static void setVolum(int v) {
		frame.getMediaPlayer().setVolume(v);
		frame.getVolumLabel().setText("" + frame.getMediaPlayer().getVolume());
		controlFrame.getVolumLabel().setText("" + frame.getMediaPlayer().getVolume());
	}

	//Open view from your computer
	public static void openVedio() {
		JFileChooser chooser = new JFileChooser();
		int v = chooser.showOpenDialog(null);
		if (v == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			String name = file.getName();
			String path = file.getAbsolutePath();
			// System.out.println("name: " + name + " abpath: " +
			// file.getAbsolutePath() + " path: " + file.getPath());
			if (listView.getList().contains(name)) {
				listView.getList().remove(name);
				listView.setList(name);
				listView.setMap(name, path);
			} else {
				listView.setList(name);
				listView.setMap(name, path);
			}
			// Save the list history
			try {
				listHistory.writeHistory(listView.getList());
				listHistory.writeHistory(listView.getMap());
				System.out.println("read: " + listHistory.readHistory());
				System.out.println("read1: " + listHistory.readHistoryMap());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			frame.getMediaPlayer().playMedia(file.getAbsolutePath());
			frame.getPlayButton().setText("||");
		}
	}

	//Open movie from the list view
	public static void openVedioFromList(String name) {

		String path = listView.getMap().get(name);
		if (listView.getList().contains(name)) {
			listView.getList().remove(name);
			listView.setList(name);
			listView.setMap(name, path);
		} else {
			listView.setList(name);
			listView.setMap(name, path);
		}
		// System.out.println("name: " + name + " abpath: " + path);

		// Save the list history
		try {
			listHistory.writeHistory(listView.getList());
			listHistory.writeHistory(listView.getMap());
			System.out.println("read: " + listHistory.readHistory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.getMediaPlayer().playMedia(path);
		frame.getPlayButton().setText("||");
	}

	//Open subtitle of movie
	public static void openSubtitle() {
		JFileChooser chooser = new JFileChooser();
		int v = chooser.showOpenDialog(null);
		if (v == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			frame.getMediaPlayer().setSubTitleFile(file);
		}
	}

	//Exit to program
	public static void exit() {
		frame.getMediaPlayer().release();
		System.exit(0);
	}

	//Enter full screen
	public static void fullScreen() {
		frame.getMediaPlayer().setFullScreenStrategy(new DefaultAdaptiveRuntimeFullScreenStrategy(frame));
		frame.getProgressBar().setVisible(false);
		frame.getControlPanel().setVisible(false);
		frame.getProgressTimePanel().setVisible(false);
		frame.getJMenuBar().setVisible(false);
		frame.getMediaPlayer().setFullScreen(true);
		controlFrame.getVolumLabel().setText("" + frame.getMediaPlayer().getVolume());
		controlFrame.getListButton().setText("List>>");

		frame.setFlag(1);
		frame.getPlayComponent().getVideoSurface().addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if (frame.getFlag() == 1) {
					controlFrame.setLocation((frame.getWidth() - controlFrame.getWidth()) / 2,
							frame.getHeight() - controlFrame.getHeight());
					controlFrame.setVisible(true);
					controlFrame.getVolumControlerSlider().setValue(frame.getVolumControlerSlider().getValue());
					if (frame.getMediaPlayer().isPlaying())
						controlFrame.getPlayButton().setText("||");
					else
						controlFrame.getPlayButton().setText(">");

				}

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	//Exit from full screen
	public static void originalScreen() {
		frame.getProgressBar().setVisible(true);
		frame.getControlPanel().setVisible(true);
		frame.getProgressTimePanel().setVisible(true);
		frame.getJMenuBar().setVisible(true);
		frame.getMediaPlayer().setFullScreen(false);
		frame.setFlag(0);
		if (frame.getMediaPlayer().isPlaying())
			frame.getPlayButton().setText("||");
		else
			frame.getPlayButton().setText(">");

		if (frame.getPlayListFrame().getFlag() == 1) {
			frame.getListButton().setText("List>>");
		} else if (frame.getPlayListFrame().getFlag() == 0) {
			frame.getListButton().setText("<<List");
		}
		controlFrame.setVisible(false);
	}

	public static DisplayFram getFrame() {
		return frame;
	}

	public static ControlFrame getControlFrame() {
		return controlFrame;
	}

	public void setLogo() {
		MyLogo logo = new MyLogo();
		frame.getMediaPlayer().setLogo(logo.getLogo());
	}

	public static ViewList getListView() {
		return listView;
	}

	public static ListHistory getListHistory() {
		return listHistory;
	}

}
