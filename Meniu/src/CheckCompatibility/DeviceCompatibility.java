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
		
		System.out.println(nrRan +  " Dispozitivele de care am nevoie sunt" + devicesNeeded);
		System.out.println(nrRan + " dispozitivele pe care le am sunt" + devicesHave);
		
		
		
		if(devicesNeeded.size() == 0){
			
			System.out.println(nrRan + "Nu am ales dispozitivul, deci nu este nicio pretentie aici ");
			return true;
		}
		
		
		for(String deviceNeeded : devicesNeeded)
			if(devicesHave.contains(deviceNeeded) == false)
			{
				System.out.println(nrRan + "Nu este gasit dispozitivul" + deviceNeeded);
				return false;
			}
		System.out.println(nrRan + "Totul este ok privind device-urile");
		
		return true;
	}

}
