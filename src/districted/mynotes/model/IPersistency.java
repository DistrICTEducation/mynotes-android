package districted.mynotes.model;

import java.util.List;


/**
 * <p>
 * Provides methods for persisting notes in the database.
 * </p>
 * @author Joris Schelfaut, DistrICT Education
 */
public interface IPersistency {

	/**
	 * Inserts a new note into the database and returns the generated id for this entry.
	 * @param myNote the note to be persisted.
	 * @return the id of the persisted note.
	 */
	public long insert(MyNote myNote) throws PersistencyException;
	
	/**
	 * Updates the given note in the database.
	 * @param myNote
	 */
	public void update(MyNote myNote) throws PersistencyException;
	
	/**
	 * Deletes the given note in the database.
	 * @param myNote
	 */
	public void delete(MyNote myNote) throws PersistencyException;
	
	/**
	 * Select the note from the database with given id.
	 * @param comparator
	 * @return the note with given id.
	 */
	public MyNote select(long id) throws PersistencyException;
	
	/**
	 * Select all the notes from the database.
	 * @param comparator
	 * @return all the notes in the database.
	 */
	public List<MyNote> selectAll() throws PersistencyException;

	/**
	 * @return the number of records in the MyNote table.
	 */
	public int recordCount();

	/**
	 * Close the current transaction.
	 */
	public void closeTransaction();
}
