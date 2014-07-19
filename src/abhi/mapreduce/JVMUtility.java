package abhi.mapreduce;

import java.io.File;

//Reference: http://docs.oracle.com/javase/7/docs/api/java/lang/ProcessBuilder.html#ProcessBuilder(java.lang.String...)

public class JVMUtility {

  public static void startProcessinJVM(String[] args) throws Exception {
	String[] args1 = new String[] {Test1.class.getName(), Test1.class.getName(), "Sharma"};
    String separator = System.getProperty("file.separator");
    String classpath = System.getProperty("java.class.path");
    
    String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
    String[] processbuilderArgs = new String[args1.length + 4];
    processbuilderArgs[0] = path;
    processbuilderArgs[1] = "-cp";
    processbuilderArgs[2] = classpath;// + separator + SystemConstants.getConfig(SystemConstants.JAR_DIRECTORY);

    String policyFileName = SystemConstants.getConfig(SystemConstants.CLIENT_POLICY);
    //Provide the MapReduce Jar Folder ensure everyone has RMI access. 
    //Just like Douglas Runs the Code Manually.
    processbuilderArgs[3] = "-Djava.security.policy="+ policyFileName +" -Djava.rmi.server.codebase=file:" + SystemConstants.getConfig(SystemConstants.RMI_CODE_BASE);
    
    for (int i = 4, j = 0; j < args1.length; i++, j++) {
    	processbuilderArgs[i] = args1[j];
    }

    //Start a brand new process -- under a new JVM
    ProcessBuilder processBuilder = new ProcessBuilder(processbuilderArgs);
    processBuilder.start();
  }
}
