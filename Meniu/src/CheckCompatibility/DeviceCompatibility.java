package CheckCompatibility;

import java.util.ArrayList;
import java.util.Random;

import com.example.meniu.Constants;

import ContextElements.ContextElement;
import ContextElements.DeviceContext;
import Task.Task;

/**
 * checks whether the user's devices needed for the task are around him
 * @author ${Vlad Herescu}
 *
 */
public class DeviceCompatibility extends Compatibility{

	@Override
	public boolean check(ContextElement task, ContextElement current,
			Task taskDetails) {
		
		Random r = new Random();
		int nrRan = r.nextInt();
		
		DeviceContext currentContext = (DeviceContext) current;
		DeviceContext taskContext = (DeviceContext) task;
		
		ArrayList<String> devicesNeeded = taskContext.getDeviceTask();
		ArrayList<String> devicesHave   = currentContext.getDeviceTask();
	
		if(devicesNeeded.size() == 0)
			return true;
		
		
		for(String deviceNeeded : devicesNeeded)
			if(devicesHave.contains(deviceNeeded) == false)
				return false;
		
		return true;
	}

}
