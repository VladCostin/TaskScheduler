package DeviceData;

public class Device {

	private int idDevice;
	
	private String macAddress;
	
	private String nameDevice;
	
	private String ownerDevice;
	
	public Device(int  id_Device, String mac_Address, String  name_Device, String owner_Device)
	{
		idDevice = id_Device;
		macAddress = mac_Address;
		nameDevice = name_Device;
		ownerDevice = owner_Device;
	}
	
	public Device()
	{
		
	}
	
	public Device(String mac_Address, String name_Device, String owner_Device)
	{
		macAddress = mac_Address;
		nameDevice = name_Device;
		ownerDevice = owner_Device;
	}

	public int getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(int idDevice) {
		this.idDevice = idDevice;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getNameDevice() {
		return nameDevice;
	}

	public void setNameDevice(String nameDevice) {
		this.nameDevice = nameDevice;
	}

	public String getOwnerDevice() {
		return ownerDevice;
	}

	public void setOwnerDevice(String ownerDevice) {
		this.ownerDevice = ownerDevice;
	}
	
	
}
