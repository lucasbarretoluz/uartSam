package uart.com.altus.model;



public class CmdType {

	public static final int DEF_CONFIG_OR_JOIN = 0x01;
	public static final int REG_BAND = 0x02;
	public static final int DEV_EUI = 0x03;
	public static final int MAX_PYLOAD_LEN = 0x04;
	public static final int OTAA_APP_EUI = 0x05;
	public static final int OTAA_APP_KEY = 0x06;
	public static final int	ABP_APPSKEY =0x07;
	public static final int ABP_NWSKEY = 0x08;
	public static final int ABP_DEV_ADDR = 0x09;
	public static final int LORA_CLASS = 0x08;
	public static final int LORA_FPORT = 0x0a;
	public static final int LORA_ADR = 0x0c;
	public static final int SET_CONFIG_AND_JOIN = 0x0d; 
	
	public static final int CONFIRMATION_TYPE_UNCONFIRMED = 0x00;
	public static final int CONFIRMATION_TYPE_CONFIRMED = 0x01;
	public static final int PERIODIC_SEND = 0x0d;
	
	public static final int STATUS_TOTAL_DEBUG = 0x00;
	public static final int ACT_TYPE = 0x01;
	public static final int DEV_ADDR = 0x03;
	
	
	
}
