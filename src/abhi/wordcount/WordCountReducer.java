/**
 * 
 */
package abhi.wordcount;

import java.io.IOException;
import java.util.Iterator;

import abhi.mapreduce.OutputCollector;
import abhi.mapreduce.Reducer;

/**
 * @author abhisheksharma
 *
 *
 * Extending the Reducer Class of the MR frameowkr to implement the Reduce functions for hte WordCound exmaple.
 *
 */
public class WordCountReducer extends Reducer<String, String, String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void reduce(String key, Iterator<String> values,
			OutputCollector<String, String> output) throws IOException {
		
		long addition = 0;  //Final summation of the word-count
		
		while(values.hasNext()) //While we have similar tokens
		{
			addition += Long.parseLong(values.next());
		}
		
		output.collect(key, Long.toString(addition));
	}

}
