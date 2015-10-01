package com.history;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class ListHistory {
	
	//Default history saving path 
	private static String path1 = "D:\\output.txt";
	private static String path2 = "D:\\output1.txt";
	
	//Write history： movie name record into the file
	public static void writeHistory(ArrayList<String> list) throws IOException{
		FileOutputStream fos = new FileOutputStream(path1);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(list);
		oos.close();
	}
	//Read history:movie name record from the file
	public static ArrayList<String> readHistory() throws ClassNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(path1);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ArrayList<String> historyList = (ArrayList<String>) ois.readObject();
		ois.close();
		return historyList;
	}
	
	//Write history:movie name and path record into the file
	public static void writeHistory(HashMap<String, String> map) throws IOException{
		FileOutputStream fos = new FileOutputStream(path2);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(map);
		oos.close();
	}
	
	//Read history:movie name and path record from the file
	public static HashMap<String, String> readHistoryMap() throws ClassNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(path2);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<String, String> historyMap = (HashMap<String, String>) ois.readObject();
		ois.close();
		return historyMap;
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
