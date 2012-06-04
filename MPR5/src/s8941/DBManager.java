package s8941;

import java.sql.*;
//import java.util.logging.*;
//import junit.runner.Version;


	public class DBManager {
		/*
		 * Dane bazy: adres, nazwa, login i haslo.
		 */
		private static String url = "jdbc:mysql://xarthias.kylos.pl:3306/";
		private static String base = "xarthias_mpr";
		private static String user = "xarthias_mpr";
		private static String pass = "Dupa123";
		
		public void addTracksRecord(String loc, String tr, String co, int l,	int s, int e, int c) {
				try {
					/*
					 * odpalenie statementa (pierwszy w metodzie moze byc sam execute, nastepne musza byc executeUpdate
					 * w statementa wpisujemy polecenie sql, ktore ma wykonac program. W tym przypadku do polecenia wrzucamy
					 * wartosci, ktore podajmy inicjujac metode.
					 * Podajemy kolumny, w ktore polecenie ma wpisywac dane. Autoinkrementowana pomijamy, bo nie wiedziec czemu
					 * execute nie przyjmuje pustego argumentu ''.
					 */
					Connection conn = DriverManager.getConnection(url + base, user,	pass);
					Statement st = conn.createStatement(); //tworzenie statementa
					st.executeUpdate("INSERT INTO tracks(location,track,country,length,straight,elevation,corners) " +
									 "VALUES('"	+ loc + "', '" + tr + "', '" + co + "', " + l + ", " + s + ", " + e + ", " + c + ")");
					System.out.println("Rekord zostal utworzony");
					} 
				catch (SQLException ex) { //w razie bledu wylapanie go, przepisanie do stringa i wypisanie na ekran
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
					}
				} 
		
		public void addRaceRecord(String name, int track_id, String time) {
			try {
				Connection conn = DriverManager.getConnection(url + base, user,	pass);
				Statement st = conn.createStatement();
				try {
					st.executeUpdate("INSERT INTO race(name, id_track, best_lap) VALUES('"
							+ name + "', " + track_id + ", '" + time + "')");
					System.out.println("Rekord zostal utworzony");
				} catch (SQLException ex) {
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
					}
				} 
			catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
		}

		public void updateBestLap(int id, String time) {
			try {
				Connection conn = DriverManager.getConnection(url + base, user, pass);
				Statement st = conn.createStatement();
				try {
					st.executeUpdate("UPDATE race SET best_lap='" + time
							+ "' WHERE id_track=" + id);
					System.out.println("Rekord zostal zmodyfikowany");
				} catch (SQLException ex) {
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
		}

		public void deleteRace(int id) {
			try {
				Connection conn = DriverManager.getConnection(url + base, user,	pass);
				Statement st = conn.createStatement();
				try {
					st.executeUpdate("DELETE FROM race WHERE id=" + id + " ");
					System.out.println("Rekord zostal usuniety");
				} catch (SQLException ex) {
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
		}

		public ResultSet allTracksFull() {
			ResultSet rs = null;
			try {
				Connection conn = DriverManager.getConnection(url + base, user,	pass);
				Statement st = conn.createStatement(); //tworzenie statementa

				try {
					rs = st.executeQuery("SELECT * FROM tracks"); //przypisanie do resultseta wyniku zapytania sql
					System.out.println("Lista torow ze specyfikacjami:");
				} catch (SQLException ex) { //wylapywanie i wypisywanie bledow
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
			return rs; //zwrot resultseta na zewnatrz
		}

		public ResultSet allTracksBestLaps() {
			ResultSet rs = null;
			try {
				Connection conn = DriverManager.getConnection(url + base, user, pass);
				Statement st = conn.createStatement();

				try {
					rs = st.executeQuery("SELECT race.id, tracks.track, race.best_lap, tracks.country " +
										 "FROM tracks, race " +
										 "WHERE tracks.id = race.id_track " +
										 "ORDER BY tracks.track");
					System.out.println("Alfabetyczna lista torow z rekordami:");
				} catch (SQLException ex) {
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
			return rs;
		}

		public ResultSet allCountry() {
			ResultSet rs = null;
			try {
				Connection conn = DriverManager.getConnection(url + base, user, pass);
				Statement st = conn.createStatement();

				try {
					rs = st.executeQuery("SELECT DISTINCT country FROM tracks");
					System.out.println("Lista krajow:");
				} catch (SQLException ex) {
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
			return rs;
		}

		public void deleteTrack(int id) {
			try {
				Connection conn = DriverManager.getConnection(url + base, user,	pass);
				Statement st = conn.createStatement();
				try {
					st.executeUpdate("DELETE FROM tracks WHERE id=" + id + " ");
					System.out.println("Rekord zostal usuniety");
				} catch (SQLException ex) {
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
		}

		public ResultSet singleTrack(int id) {
			ResultSet rs = null;
			try {
				Connection conn = DriverManager.getConnection(url + base, user,	pass);
				Statement st = conn.createStatement(); //tworzenie statementa

				try {
					rs = st.executeQuery("SELECT * FROM tracks WHERE id="+id);
					System.out.println("Lista torow ze specyfikacjami:");
				} catch (SQLException ex) { //wylapywanie i wypisywanie bledow
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
			return rs; //zwrot resultseta na zewnatrz
		}

		public ResultSet allRacesFull() {
			ResultSet rs = null;
			try {
				Connection conn = DriverManager.getConnection(url + base, user,	pass);
				Statement st = conn.createStatement();

				try {
					rs = st.executeQuery("SELECT race.id, tracks.track, race.name, race.best_lap FROM race JOIN tracks WHERE race.id_track=tracks.id");
					System.out.println("Lista wyscigow z czasami:");
				} catch (SQLException ex) { 
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
			}
			return rs; //zwrot resultseta na zewnatrz
		}
			
		public ResultSet oneRaceFull(int id) {
			ResultSet rs = null;
			try {
				Connection conn = DriverManager.getConnection(url + base, user,	pass);
				Statement st = conn.createStatement();
				try {
					rs = st.executeQuery("SELECT race.id, tracks.track, race.name, race.best_lap FROM race JOIN tracks WHERE race.id="+id+"&& race.id_track=tracks.id");
					System.out.println("Wyscig o id: "+id);
					} 
				catch (SQLException ex) { 
					ex.printStackTrace();
					String exe = ex.toString();
					System.out.println(exe);
					}
				} 
			catch (SQLException ex) {
				ex.printStackTrace();
				String exe = ex.toString();
				System.out.println(exe);
				}
			return rs; //zwrot resultseta na zewnatrz
			}

	}
