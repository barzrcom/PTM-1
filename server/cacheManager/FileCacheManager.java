package cacheManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import board.Solution;
import board.State;

public class FileCacheManager implements CacheManager {	
	private String directory = ".cache";
	private boolean isInitialised = false;
	
	private void initialise() {
		/**
		 * Create cache manager initial folder that will include saved solutions
		 */
		File f = new File(this.directory);

		if (!f.exists()) {
			// Create a new directory only if is it not exists yet
			//System.out.println("solutions folder have been created, location: " + f);
			f.mkdir();
		}
		this.isInitialised = true;  // mark that initialization already happened 
	}

	@Override
	public <T> boolean isSolutionExist(State<T> problem) {
		/**
		 * Check by filename if a solution already exists for given problem
		 */
		return new File(this.directory, String.valueOf(problem.hashCode())).exists();  // Filename e.g. "/.cache/1318404239"
	}

	@Override
	public <T> Solution load(State<T> problem) {
		/**
		 * Loads a solution (from text file named by problem hash-code) for a given problem
		 */
		Solution solution = null;
		File f = new File(this.directory, String.valueOf(problem.hashCode()));

		try {
			// First opening file based on problem hash-code, and load the solution object from it
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(f));
			solution = (Solution) objectInputStream.readObject();
			objectInputStream.close(); 
		} catch (FileNotFoundException e1) {
			// FileInputStream exception when file is not exists on the system
			System.out.println("Problem's solution could not be found " + f + ", did you called 'isSolutionExist' before load?");
		} catch (IOException e2) {
			// ObjectInputStream exception usually when object cannot be deserialize
			System.out.println("Could not load solution, File contains a valid solution?");
		} catch (ClassNotFoundException e3) {
			// readObject exception
			System.out.println("Problem's solution could not be loaded, Error to find loaded Class type.");
		}
		return solution;
	}

	@Override
	public <T> void save(State<T> problem, Solution solution) {
		/**
		 * Saves a solution (to text file named by problem hash-code) for a given problem
		 */
		if (!this.isInitialised) {
			this.initialise();  // First initialization of cache-manager class, only mandatory when saving solutions
		}
		File f = new File(this.directory, String.valueOf(problem.hashCode()));

		try {
			// First creating file based on problem hash-code, and write the solution object into it
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
			objectOutputStream.writeObject(solution);
			objectOutputStream.close();
		} catch (FileNotFoundException e1) {
			// FileOutputStream exception when destination file could not be opened
			System.out.println("Could not save solution by filename " + f + ", Permission issues?");
		} catch (IOException e2) {
			// ObjectOutputStream exception usually when object cannot be serialized
			System.out.println("Could not save solution, Does solution class is Serializable?");
		}
	}
}
