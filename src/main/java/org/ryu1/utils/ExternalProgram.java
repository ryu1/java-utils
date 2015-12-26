package org.ryu1.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ExternalProgram.
 * 
 * @author R.Ishitsuka
 */
public class ExternalProgram {
	// LOG
	private Log log;

	/**
	 * ExternalProgram
	 *
	 * @author R.Ishitsuka
	 * @param cmd
	 */
	public ExternalProgram(String category) {
		log = LogFactory.getLog(category);
	}

	public int exec(String cmd) throws IOException, InterruptedException {
		log.info("START : External Program");
		log.info("CMD : " + cmd);

		StringBuilder errBuff = new StringBuilder();
		InputStream errIn = null;
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			errIn = process.getErrorStream();

			while (true) {
				int c = errIn.read();
				if (c == -1) {
					break;
				}
				errBuff.append((char) c);
			}

			return process.waitFor();
		} finally {
			if (errIn != null) {
				errIn.close();
			}
		}
	}
	
	public String execfind(String cmd) throws IOException, InterruptedException {
		log.info("START : External Program");
		log.info("CMD : " + cmd);

		InputStream is = null;
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			is = process.getInputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
		    String completeName = "";
		    String completeNameDate = "";		    
		    while ((completeName = br.readLine()) != null) {
		    	int pos = completeName.indexOf("completed_") + 10;
		    	completeNameDate = completeName.substring(pos);
		    }
			return completeNameDate;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
}
