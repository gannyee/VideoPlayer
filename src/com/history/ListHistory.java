package com.history;

import jdk.nashorn.internal.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ListHistory {
	
	//Default history saving path 
	private static String path1 = "output";
	private static String path2 = "output1";
	
	//Write history： movie name record into the file
	public static void writeHistory(ArrayList<String> list) throws IOException{
		File playListHisFile = new File(path1);
		if (!playListHisFile.exists()) {
			playListHisFile.createNewFile();
		}
		
	
		FileOutputStream fos = new FileOutputStream(path1);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(list);
		oos.close();
	}
	//Read history:movie name record from the file
	public static ArrayList<String> readHistory() throws ClassNotFoundException, IOException{
		File playListHisFile = new File(path1);
		if (!playListHisFile.exists()) {
			playListHisFile.createNewFile();
		}

		FileInputStream fis = new FileInputStream(path1);
		byte[] buffer = new byte[10];
		StringBuilder sb = new StringBuilder();
		while (fis.read(buffer) != -1) {
			sb.append(new String(buffer));
			buffer = new byte[10];
		}
		fis.close();



		String str[] = sb.toString().split(",");
		ArrayList<String> his = new ArrayList<String>();
		for (String history : str){
			his.add(history);
		}
		return his;

	}
	
	//Write history:movie name and path record into the file
	public static void writeHistory(HashMap<String, String> map) throws IOException{
		File playListHisFile = new File(path2);
		if (!playListHisFile.exists()) {
			playListHisFile.createNewFile();
		}
		
		FileOutputStream fos = new FileOutputStream(path2);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.close();
	}
	
	//Read history:movie name and path record from the file
	public static HashMap<String, String> readHistoryMap() throws ClassNotFoundException, IOException{
		File playListHisFile = new File(path2);
		if (!playListHisFile.exists()) {
			playListHisFile.createNewFile();
		}
		

		return new HashMap<String, String>(1);
	}
	
	//Clear storage history record
	public static void cleanHistory() throws IOException{
		RandomAccessFile file1 = new RandomAccessFile(path1, "rw");
		RandomAccessFile file2 = new RandomAccessFile(path2, "rw");
		FileChannel fc1 = file1.getChannel();
		FileChannel fc2 = file2.getChannel();
		fc1.truncate(1);
		fc2.truncate(1);
	}
	/*public static void main(String[] args) throws IOException, ClassNotFoundException {
		ArrayList<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		writeHistory(list);
		ArrayList<String> arrayList = readHistory();
		System.out.println("read: " + arrayList);
	}*/
}
