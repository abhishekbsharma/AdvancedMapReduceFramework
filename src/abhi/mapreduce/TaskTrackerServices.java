/**
 * 
 */
package abhi.mapreduce;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author abhisheksharma
 *
 * This is the Remote Referecne for the Task Tracker. Basically this registered to the RMI and acts the front end for the Task Tracker. 
 * 
 * Task Tracker Services Exposes the Functionaliyt to the Outside World.
 *
 */
public class TaskTrackerServices extends UnicastRemoteObject implements ITaskTrackerServices{

	private TaskTracker taskTrackerReference; 
	
	protected TaskTrackerServices(TaskTracker taskTracker) throws RemoteException {
		this.taskTrackerReference = taskTracker;
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean executeTask(TaskMetaData taskMetaData) {
		if(taskMetaData.getTaskType() == SystemConstants.TaskType.MAPPER)
		{
			synchronized(this.taskTrackerReference.countofRunningMapperFieldAgents)
			{
				if(this.taskTrackerReference.countofRunningMapperFieldAgents < this.taskTrackerReference.mapperSlotCapacity)
				{
					synchronized(this.taskTrackerReference.countofRunningMapperFieldAgents){
						this.taskTrackerReference.countofRunningMapperFieldAgents++;
					}
					
					String[] processargs = new String [] 
							{
								MapperFieldAgent.class.getName(),
								String.valueOf(taskMetaData.getTaskID()),
								SystemConstants.getConfig(SystemConstants.ADFS_DIRECTORY) + System.getProperty("file.separator") + taskMetaData.getInputPath(),
								SystemConstants.getConfig(SystemConstants.ADFS_DIRECTORY) + System.getProperty("file.separator") + taskMetaData.getOutputPath(),
								taskMetaData.getMapper(),
								taskMetaData.getPartitioner(),
								taskMetaData.getInputFormat(),
								String.valueOf(taskMetaData.getReducerNum())
							};
					

					try {
						//Start it in a Brand New JVM
						JVMUtility.startProcessinJVM(processargs);
					} catch (Exception e) {
						System.err.println("Could initiate the Map Request");
						e.printStackTrace();
						return false;
					}
					return true;
				}
				else
					return false;
			}
		}
		else
		{
			synchronized(this.taskTrackerReference.countofRunningReducerFieldAgents)
			{
				if(this.taskTrackerReference.countofRunningReducerFieldAgents < this.taskTrackerReference.reducerSlotCapacity)
				{
					synchronized(this.taskTrackerReference.countofRunningReducerFieldAgents){
						this.taskTrackerReference.countofRunningReducerFieldAgents++;
						}
					
					String[] processargs = new String [] 
							{
								ReducerFieldAgent.class.getName(),
								String.valueOf(taskMetaData.getTaskID()),
								SystemConstants.getConfig(SystemConstants.ADFS_DIRECTORY) + System.getProperty("file.separator") + taskMetaData.getOutputPath(),
								taskMetaData.getReducer(),
								taskMetaData.getOutputFormat(),
								String.valueOf(taskMetaData.getParitionNumber())
							};
					

					try {
						//Start it in a Brand New JVM
						JVMUtility.startProcessinJVM(processargs);
					} catch (Exception e) {
						System.err.println("Could initiate the Map Request");
						e.printStackTrace();
						return false;
					}
					return true;
				}
				else
					return false;
			}
		}
		
	}

	
	//Get the HeartBeat from the Field Agent and Update its Status
	@Override
	public void updateFieldAgentStatus(Object status) throws RemoteException {
		if(status instanceof TaskProgress)
		{
			TaskProgress progress = (TaskProgress) status;
			this.taskTrackerReference.updateFieldAgentStatus(progress);
		}
		
	}

}
