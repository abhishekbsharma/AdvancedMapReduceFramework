/**
 * 
 */
package abhi.mapreduce;

/**
 * @author abhisheksharma
 *
 *This interface defines the methods to be implemented by the Partitioner 
 *The partitioning policy will be provided by the App user 
 *Generally in Hadoop this is just a HashFucntion of the key based on the number of reducers
 *choose for the job.
 * @param <K>
 */
public interface Partitioner<K> {

	//The Paritioner of the App. Programmer Side will implement this method.
	public int getPartition(K key, int numofReducers);
}
