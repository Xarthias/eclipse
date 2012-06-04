import static org.junit.Assert.*;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.junit.Test;

public class DBManagerTest {
	s8941.DBManager dbManager = new s8941.DBManager();

	/*
	 * Testowanie metod addTracksRecord, allTracksFull i deleteTrack
	 */
	@Test
	public void testObslugiTabeliTracks(){
		String  tor = "Nazwa Testowa", torT,
				lokacja = "Lokacja Testowa", lokacjaT,
				kraj = "Kraj Testowy", krajT,
				lokTest = "aa";
		int 	length = 7777, lengthT,
				straight = 666, straightT,
				elevation = 55, elevationT,
				corners = 4, cornersT,
				l, i, ii = 0, idTest = 9999, iTest=9999;
		ArrayList<Integer> listId = new ArrayList<Integer>();
		ArrayList<String> listLocation = new ArrayList<String>();
		ArrayList<String> listTrack = new ArrayList<String>();
		ArrayList<String> listCountry = new ArrayList<String>();
		ArrayList<Integer> listLength = new ArrayList<Integer>();
		ArrayList<Integer> listStraight = new ArrayList<Integer>();
		ArrayList<Integer> listElevation = new ArrayList<Integer>();
		ArrayList<Integer> listCorners = new ArrayList<Integer>();
		ResultSet rs;
		
		//1. Wrzucenie danych do tabeli (insert) (addTracksRecord)
		dbManager.addTracksRecord(lokacja, tor, kraj, length, straight, elevation, corners);
		
		//2. Odczytanie tablicy (allTracksFull)
		rs = dbManager.allTracksFull();
		//3. Wypisanie danych i wyszukanie id dodanego wpisu
		try {
			while (rs.next()) { 
				listId.add(rs.getInt("id"));
				listLocation.add(rs.getString("location"));
				listTrack.add(rs.getString("track"));
				listCountry.add(rs.getString("country"));
				listLength.add(rs.getInt("length"));
				listStraight.add(rs.getInt("straight"));
				listElevation.add(rs.getInt("elevation"));
				listCorners.add(rs.getInt("corners"));
				} 
			}
			catch (Exception ex) { 
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
				}
		l = listId.size();
		for (i=0; i<l; i++){
			lokTest = listLocation.get(i);
			if (lokacja.equals(lokTest)){
				idTest = listId.get(i);
				iTest = i;
				ii++;
				}
			}
		if (ii>1) System.out.println("Blad - jest wiecej ("+ii+") wpisow odpowiadajacych kryteriom wyszukiwania");
		
		//4. Porownanie danych wczytanych i odczytanych
		lokacjaT = listLocation.get(iTest);
		torT = listTrack.get(iTest);
		krajT = listCountry.get(iTest);
		lengthT = listLength.get(iTest);
		straightT = listStraight.get(iTest);
		elevationT = listElevation.get(iTest);
		cornersT = listCorners.get(iTest);
		
		System.out.println("Dodany wpis o id: "+idTest+": "+lokacjaT+", "+torT+", "+krajT+", "+lengthT+", "+straightT+", "+elevationT+", "+cornersT);
		
		assertEquals("Lockacja", lokacjaT, lokacja);
		assertEquals("Tor", torT, tor);
		assertEquals("Kraj", krajT, kraj);
		assertEquals("Dlugosc", lengthT, length);
		assertEquals("Prosta", straightT, straight);
		assertEquals("Poziomy", elevationT, elevation);
		assertEquals("Zakrety", cornersT, corners);
		
		//5. Usuniecie wpisu (deleteTrack)
		if (idTest != 0) {
			dbManager.deleteTrack(idTest);
			}
		//6. Sprawdzenie poprawnosci usuniecia wpisu - test nie dziala :/
		//idTest = listId.get(iTest);
		//assertEquals("po usunieciu", idTest, 0);
		//assertNull(idTest);
	}
	
	@Test
	public void testObslugiTabeliRace(){
		ResultSet rs;
		String 	name = "Testowy Wyscig", nameT,
				time = "12:34:567", timeT,
				time2 = "76:54:321", 
				trackT, track = "The Top Gear Test Track",
				name2 = "";
		int		idtrack = 21,
				l,i,
				ii=0, 
				iTest = 9999,
				idTest = 9999;
		ArrayList<Integer> listId = new ArrayList<Integer>();
		ArrayList<String> listTrack = new ArrayList<String>();
		ArrayList<String> listName = new ArrayList<String>();
		ArrayList<String> listTime = new ArrayList<String>();
		//1. Wrzucenie danych do tabeli (addRaceRekord)
		dbManager.addRaceRecord(name, idtrack, time);
		//2. Odczytanie tabeli (allRacesFull)
		rs = dbManager.allRacesFull();
		try {
			while (rs.next()) { 
				listId.add(rs.getInt("race.id"));
				listName.add(rs.getString("race.name"));
				listTrack.add(rs.getString("tracks.track"));
				listTime.add(rs.getString("race.best_lap"));
				} 
			}
			catch (Exception ex) { 
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
				}
		
		//3. Wyszukanie id dodanego wpisu
		l = listId.size();
		for (i=0; i<l; i++){
			name2 = listName.get(i);
			if (name.equals(name2)){
				idTest = listId.get(i);
				iTest = i;
				ii++;
				}
			}
		if (ii>1) System.out.println("Blad - jest wiecej ("+ii+") wpisow odpowiadajacych kryteriom wyszukiwania");
		//4. Porownanie danych i wczytanych i odczytanych
		nameT = listName.get(iTest);
		timeT = listTime.get(iTest);
		trackT = listTrack.get(iTest);
		assertEquals("name",nameT,name);
		assertEquals("time",timeT,time);
		assertEquals("track",trackT,track);
		//5. Modyfikacja wpisu (updateBestLap)
		dbManager.updateBestLap(idTest, time2);
		//6. Ponowne odczytanie tabeli (oneRaceFull())
		//7. Porownanie danych
		//8. Usuniecie dodanego wpisu (deleteRace)
		System.out.println("Usuwam wyscig o id: "+idTest);
		dbManager.deleteRace(idTest);
		//9. Sprawdzenie poprawnosci usuniecia wpisu
		
	}
}
