package s8941;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;


public class Menusy {
	DBManager dbManager = new DBManager();
	private Scanner scanner = new Scanner (System.in);
	//private String input = "";
	private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public Menusy() {
		}
	
	/*
	 * metoda wypisujaca menu programu
	 */
	public void napisy() {
		System.out.println("\nWybierz opcje:");
		System.out.println("1. Wypisz pelne dane wszystkich torow."); //zrobione
		System.out.println("2. Wpisz nowy tor."); //zrobione
		System.out.println("3. Wyswietl rekordy torow."); //zrobione
		System.out.println("4. Dodanie lub zmiana rekordu wybranej trasy."); //zrobione
		System.out.println("5. Usuniecie rekordu."); //zrobione
		System.out.println("Dowolny klawisz, aby wyjsc.");
		System.out.println("Twoj wybor:");
		}
	
	/*
	 * switch obslugujacy menu
	 */
	public void wybor(int wybor) {
		switch(wybor){
			case 1:
				printTracksFull();
				napisy();
				break;
			case 2:
				insertTracksRecord();
				napisy();
				break;
			case 3:
				printBestLaps();
				napisy();
				break;
			case 4:
				updateBestLap();
				napisy();
				break;
			case 5:
				deleteRaceRecord();
				napisy();
				break;
			default:
				break;
			}
		}

	/*
	 * metoda zbierajaca dane do i uruchamiajaca metode updateBestLap z DBManagera.
	 * Brak jakiegokolwiek sprawdzania poprawnosci danych - we wszystkich metodach, ktore wczytuja dane z klawiatury. Brak czasu :(
	 * Metoda prosciutka - zbiera inty, sklada w stringa i przekazuje do metody obslugujacej baze.
	 */
	private void updateBestLap() {
		System.out.println("Podaj id toru: ");
		int id = scanner.nextInt();
		System.out.println("Podaj nowy rekord (osobno minuty, sekundy i setne). ");
		System.out.println("Podaj ilosc minut: ");
		int min = scanner.nextInt();;
		System.out.println("Podaj ilosc sekund: ");
		int sek = scanner.nextInt();;
		System.out.println("Podaj ilosc setnych: ");
		int set = scanner.nextInt();;
		String time = min+":"+sek+":"+set;
		dbManager.updateBestLap(id,time);
		System.out.println("Nowy rekord toru numer "+id+", to: "+time);
	}

	/*
	 * metoda wrzuca do arrayow wynik (resultseta) zapytania do bazy z metody allTracksFull()
	 * rowniez prosta jak budowa cepa
	 */
	public void printTracksFull() {
		int nr, l; //pomocnicze inty do petli for
		ArrayList<Integer> listId = new ArrayList<Integer>();
		ArrayList<String> listTrack = new ArrayList<String>();
		ArrayList<String> listCountry = new ArrayList<String>();
		ArrayList<Integer> listLength = new ArrayList<Integer>();
		ArrayList<Integer> listStraight = new ArrayList<Integer>();
		ArrayList<Integer> listElevation = new ArrayList<Integer>();
		ArrayList<Integer> listCorners = new ArrayList<Integer>();
		ResultSet rs = dbManager.allTracksFull();
		try {
			while (rs.next()) { //petla dziala, poki rs zwraca kolejne wyniki
				listId.add(rs.getInt("id"));
				listTrack.add(rs.getString("track"));
				listCountry.add(rs.getString("country"));
				listLength.add(rs.getInt("length"));
				listStraight.add(rs.getInt("straight"));
				listElevation.add(rs.getInt("elevation"));
				listCorners.add(rs.getInt("corners"));
				}
			} 
		catch (Exception ex) { //obsluga wyjatku
			ex.printStackTrace();
			String exe = ex.toString();
			System.out.println(exe);
			}
		l = listId.size(); //ilosc wierszy w bazie obliczona na podstawie kolumny Id (bo to kolumna z autoincrementem)
		System.out.println("Tracks in database:");
		for (nr = 0; nr < l; nr++) { //petelka wypisujaca, chyba nie wymaga komentarza
			System.out.println(listId.get(nr) + ". " + listTrack.get(nr) + ", "
					+ listCountry.get(nr) + ", " + listLength.get(nr)
					+ "m long, " + listStraight.get(nr)
					+ "m longest straight, elevation: " + listElevation.get(nr)
					+ "m, corners: " + listCorners.get(nr) + ".\n");
		}
	}

	/*
	 * metoda wrzucajaca dane do metody robiacej inserta do bazy
	 */
	public void insertTracksRecord() {
		String loc = "", tr = "", co = "";
		System.out.println("Wpisywanie nowego toru");
		System.out.println("Podaj lokacje: ");
		try {
		    loc = in.readLine();
		    } 
		catch(IOException e) {
		    System.err.println("*zonk*" + e);
		    }
		System.out.println("Podaj nazwe toru: ");
		//String tr = scanner.next();
		try {
			tr = in.readLine();
		    } 
		catch(IOException e) {
		    System.err.println("*zonk*" + e);
		    }
		System.out.println("Podaj kraj: ");
		try {
		    co = in.readLine();
		    } 
		catch(IOException e) {
		    System.err.println("*zonk*" + e);
		    }
		System.out.println("Podaj dlugosc toru: ");
		int l = scanner.nextInt();
		System.out.println("Podaj dlugosc najdluzszej prostej: ");
		int s = scanner.nextInt();
		System.out.println("Podaj roznice poziomow: ");
		int e = scanner.nextInt();
		System.out.println("Podaj ilosc zakretow: ");
		int c = scanner.nextInt();
		dbManager.addTracksRecord(loc, tr, co, l, s, e, c);
		System.out.println("Dodano wpis: "+loc+", "+tr+", "+co+", "+l+"m, "+s+"m, "+e+"m, "+c);
		}
	
	/*
	 * metoda uruchamiajaca metode kasujaca wpis w tabeli race o podanym id
	 */
	private void deleteRaceRecord(){
		System.out.println("Usuwanie wyscigu.");
		System.out.println("Podaj id wyscigu do usuniecia: ");
		int id = scanner.nextInt();
		System.out.println("Czy jestes pewien? (0=nie, 1=tak)"); //taki bajerek :P
		int p = scanner.nextInt();
		if (p==1)	
			{
			dbManager.deleteRace(id);
			System.out.println("Wpis o numerze id = "+id+" zostal usuniety.");
			}
		else System.out.println("Wpis nie zostal usuniety.");
	}

	/*
	 * metoda wypisujaca nazwy torow i najlepsze czasy na nich, korzystajaca z dwoch resultsetow z metod allTracksBestLaps i allCounty
	 * pierwsza robi prostego joina, druga liste unikalnych krajow
	 * potem to juz wrzucanie danych w arraye, wybor metody i petelki wypisujace do obydwu metod
	 */
	private void printBestLaps(){
		int nr, nr2, l, l2, kraj, p;
		String countryName;
		ArrayList<Integer> listId = new ArrayList<Integer>();
		ArrayList<String> listTrack = new ArrayList<String>();
		ArrayList<String> listTime = new ArrayList<String>();
		ArrayList<String> listCountry = new ArrayList<String>();
		ArrayList<String> listCountry2 = new ArrayList<String>();
		ResultSet rs = dbManager.allTracksBestLaps();
		ResultSet rs2 = dbManager.allCountry();
		try {
			while (rs.next()){
				listId.add(rs.getInt("race.id"));
				listTrack.add(rs.getString("tracks.track"));
				listTime.add(rs.getString("race.best_lap"));
				listCountry.add(rs.getString("tracks.country"));
				}
			}
		catch (Exception ex) {
			ex.printStackTrace();
			String exe = ex.toString();
			System.out.println(exe);
			}
		try {
			while (rs2.next()){
				listCountry2.add(rs2.getString("country"));
				}
			}
		catch (Exception ex) {
			ex.printStackTrace();
			String exe = ex.toString();
			System.out.println(exe);
			}
		System.out.println("Wypisac wszystkie rekordy czy wg kraju? (1 - wszystkie, 2 - krajami).");
		p = scanner.nextInt();
		l = listId.size();
		l2 = listCountry2.size();
		if (p==1)	{
			System.out.println("Wszystkie rekordy: (minuty:sekundy:setne)");
			for (nr = 0; nr < l; nr++) {
				System.out.println(listId.get(nr) + ". " + listTrack.get(nr) +": "+listTime.get(nr)+".");
				}
			}
		else if (p==2) {
			System.out.println("Lista krajow:");
			for (nr = 0; nr < l2; nr++) {
				nr2=nr+1;
				System.out.println(nr2 + ". "+listCountry2.get(nr));
				}
			System.out.println("Podaj numer kraju:");
			kraj = scanner.nextInt()-1;
			countryName = listCountry2.get(kraj);
			System.out.println("Wybrales: "+countryName+". Oto rekordy: ");
			for (nr = 0; nr < l; nr++) {
				if (countryName == listCountry.get(nr)) System.out.println(listId.get(nr) + ". " + listTrack.get(nr) +": "+listTime.get(nr)+".");
				}
			}
		}
	}