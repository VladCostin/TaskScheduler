package DatabaseOperation;

import com.example.meniu.AddDeviceActivity;
import com.example.meniu.MainActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**inserts data about a device in the database
 * @author ${Vlad Herescu}
 *
 */
public class AddDeviceButton implements OnClickListener {
	
	AddDeviceActivity addDevice;
	
	public AddDeviceButton(AddDeviceActivity add_Device)
	{
		addDevice = add_Device;
	}
	

	@Override
	public void onClick(View v) {
	
		System.out.println(v.getId());
		
		EditText owner =  (EditText)  addDevice.getLayout().getChildAt(v.getId() -2 );
		TextView mac   =  (TextView)  addDevice.getLayout().getChildAt(v.getId() -4 );
		TextView name   = (TextView)  addDevice.getLayout().getChildAt(v.getId() -6 );
		
		MainActivity.getDatabase().addDevice
		(mac.getText().toString(), name.getText().toString(), owner.getText().toString());
		
		System.out.println( "3. MOMENTAN SUNT " + Thread.activeCount());
		
		
		
		addDevice.setDevices(  MainActivity.getDatabase().getAllDevices());
		addDevice.getDeviceInfo().remove(mac.getText().toString());
		addDevice.addInfoMethod();

	}

}
