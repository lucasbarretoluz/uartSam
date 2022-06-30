package uart.com.altus.view;

import java.util.HexFormat;
import java.util.zip.Deflater;

public class CmdSam {

	private String inputCommand = "";
	private byte crcByte [] = null;
	private int cmd;
	private int type;
	private int command;

	public String getCmdSam(String serialCmd2, String cmdType, String content) {

		switch (serialCmd2) {
			case "SETTINGS":
				cmd = 0x01;
				configure(cmdType);
				break;
			case "TX MESSAGE":
				cmd = 0x02;
				txMensage(cmdType);
				break;
			case "STATUS REQUEST":
				cmd = 0x03;
				statusRequest(cmdType);
				break;
			case "REPORT REQUEST":
				cmd = 0x04;
				reportRequest(cmdType);
				break;
			default:
		}

		if ((content = content.toUpperCase()).startsWith("0X")) {
			content = content.replace("0X", "");
	    }
		
		inputCommand = "0"+Integer.toString(cmd)+ " 0".concat(Integer.toString(type)+ " ".concat(content));
		crcByte =  HexFormat.ofDelimiter(" ").parseHex(inputCommand);
		
		command = CRC16_CCITT(crcByte);

		return String.valueOf(command);
	}
	
	public static int CRC16_CCITT (byte[] buffer) {
	     int wCRCin = 0xFFFF; 
	     int wCPoly = 0x1021; 
	     for (byte b : buffer) {
	         for (int i = 0; i < 8; i++) {
	             boolean bit = ((b >> (7 - i) & 1) == 1);
	             boolean c15 = ((wCRCin >> 15 & 1) == 1);
	             wCRCin <<= 1;
	             if (c15 ^ bit)
	                 wCRCin ^= wCPoly;
	         }
	     }
	     wCRCin &= 0xffff;
	     return wCRCin;
	    }
	
	
	public void configure(String cmdType) {
		switch (cmdType) {
			// CONFIGURATION
			case "DEF CONFIG/JOIN":
				type = 0x01;
				break;
			case "REG BAND":
				type = 0x02;
				break;
			case "DEV EUI":
				type = 0x03;
				break;
			case "MAX PYLOAD LEN":
				type = 0x04;
				break;
			case "OTAA APP EUI":
				type = 0x05;
				break;
			case "OTAA APP KEY":
				type = 0x06;
				break;
			case "ABP APPSKEY":
				type = 0x07;
				break;
			case "ABP NWSKEY":
				type = 0x08;
				break;
			case "ABP DEV ADDR":
				type = 0x09;
				break;
			case "LORA CLASS":
				type = 0x0a;
				break;
			case "LORA FPORT":
				type = 0x0b;
				break;
			case "LORA ADR":
				type = 0x0c;
				break;
			case "SET CONFIG AND JOIN":
				type = 0x0d;
				break;
			default:
		}
	}

	public void txMensage(String cmdType) {
		switch (cmdType) {
			// TX MSG
			case "CONFIRMATION TYPE UNCONFIRMED":
				type = 0x00;
				break;
			case "CONFIRMATION TYPE CONFIRMED":
				type = 0x01;
				break;
			case "PERIODIC SEND":
				type = 0x42;
				break;
			default:
		}
	}

	public void statusRequest(String cmdType) {
		switch (cmdType) {
			// STATUS REQUEST
			case "STATUS TOTAL DEBUG":
				type = 0x00;
				break;
			case "ACT TYPE":
				type = 0x01;
				break;
			case "REG_BAND":
				type = 0x02;
				break;
			case "DEV ADDR":
				type = 0x03;
				break;
			case "DEV_EUI":
				type = 0x04;
				break;
			case "MAX PAYLOAD":
				type = 0x05;
				break;
			case "CLASS":
				type = 0x06;
				break;
			case "FPORT":
				type = 0x07;
				break;
			case "ADR":
				type = 0x08;
				break;
			default:
		}
	}

	public void reportRequest(String cmdType) {
		switch (cmdType) {
			// REPORT REQUEST
			case "TOTAL REPORT REQ":
				type = 0xff;
				break;
			case "LAST RESET CAUSE":
				type = 0x01;
				break;
			case "RESTORATION ERROR":
				type = 0x02;
				break;
			case "JOIN ERROR/STATUS":
				type = 0x03;
				break;
			case "NEW MSG RECEIVED":
				type = 0x04;
				break;
			case "RX MSG ERROR":
				type = 0x05;
				break;
			case "TX MSG ERROR":
				type = 0x06;
				break;
			case "STACK ERROR":
				type = 0x07;
				break;
			default:
		}
	}

}
