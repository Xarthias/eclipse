package s8941;

import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException { 
		Scanner scanner = new Scanner (System.in);
		Menusy menusy = new Menusy();
		int wybor; 
		menusy.napisy();
		do 	{
			wybor = scanner.nextInt();
			menusy.wybor(wybor);
			}
		while (wybor>=1 && wybor<=5);
		System.out.println("\nDziekujemy za uwage :P");
		}

	}

