package DatabaseOperation;

import com.example.meniu.AddDeviceActivity;
import com.example.meniu.MainActivity;
import com.example.meniu.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	
		Button buton = (Button) v;
		System.out.println(v.getId());
		if(buton.getText().toString().equals(addDevice.getResources().getString(R.string.buttonAddOwnerDevice)))
			addWithOwner(v);
		else
			addAsMine(v);
		

	}
	
	
	public void addAsMine(View v)
	{
		String owner = addDevice.getResources().getString(R.string.myDeviceConstant);
		TextView mac   =  (TextView)  addDevice.getLayout().getChildAt(v.getId() -5 );
		TextView name   = (TextView)  addDevice.getLayout().getChildAt(v.getId() -7 );
		
		MainActivity.getDatabase().addDevice
		(mac.getText().toString(), name.getText().toString(), owner);
		
		addDevice.setDevices(  MainActivity.getDatabase().getAllDevices());
		addDevice.getDeviceInfo().remove(mac.getText().toString());
		addDevice.addInfoMethod();
	}
	
	public void addWithOwner(View v)
	{
		EditText owner =  (EditText)  addDevice.getLayout().getChildAt(v.getId() -2 );
		TextView mac   =  (TextView)  addDevice.getLayout().getChildAt(v.getId() -4 );
		TextView name   = (TextView)  addDevice.getLayout().getChildAt(v.getId() -6 );
		
		MainActivity.getDatabase().addDevice
		(mac.getText().toString(), name.getText().toString(), owner.getText().toString());
		
		addDevice.setDevices(  MainActivity.getDatabase().getAllDevices());
		addDevice.getDeviceInfo().remove(mac.getText().toString());
		addDevice.addInfoMethod();
	}

}
