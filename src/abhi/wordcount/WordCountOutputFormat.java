/**
 * 
 */
package abhi.wordcount;

import abhi.mapreduce.OutputFormat;

/**
 * @author abhisheksharma
 *
 */
public class WordCountOutputFormat extends OutputFormat<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String format(String key, String value) {
		
		return key + "\t" + value + "\n";
		//We write key values on new lines separated by tabs
	}


}
