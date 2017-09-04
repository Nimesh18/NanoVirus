package nano.virus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

public class App {
	
	//prints to the console and file
	public static void print(final String msg) {
		System.out.print(msg);
		stream.print(msg);
	}
	
	public static void println(final String msg) {
		System.out.println(msg);
		stream.println(msg);
	}
	
	public static PrintStream stream;
	final static Random rnd = new Random();
	public static void main(String[] args) {
		
		try {
			stream = new PrintStream(new File("output.txt"));
			Body body = new Body();
			body.injectVirus();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		
	}

}
