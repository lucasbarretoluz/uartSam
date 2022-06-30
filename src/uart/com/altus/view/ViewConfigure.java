package uart.com.altus.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Window.Type;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

import com.fazecast.jSerialComm.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.JProgressBar;
import java.io.*;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Cursor;
import java.awt.ComponentOrientation;
import javax.swing.DropMode;

public class ViewConfigure {

	private JFrame frmSettings;
	SerialPort serialPort1;
	OutputStream outputStream1;
	private JButton btnOpenSerial;
	private JButton btnCloseSerial;
	private JComboBox comPortBox;
	private JButton btnSend;
	private JProgressBar statusSerial2;
	private JProgressBar statusSerial;
	private ButtonGroup group1;
	String dataBuffer= "";
	private JTextArea textAreaReceive;
	private JScrollBar scrollBar;
	private JScrollPane scrollPane;
	private JComboBox baudRateBox;
	private JComboBox dataBitsBox;
	private JComboBox stopBitBox;
	private JComboBox ParityBitsBox;
	private JTextArea textAreaSend;
	private JTextArea commandArea;
	private JComboBox cmdBoxType;
	private JComboBox serialCmdBox;
	private JRadioButton rdbtnSelectText;
	private JRadioButton rdbtnSelectSAM;
	
	String serialCommand = "";
	private JButton btnClear;
	private JButton btnAddMacro;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewConfigure window = new ViewConfigure();
					window.frmSettings.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ViewConfigure() {
		initialize();
		
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmSettings = new JFrame();
		frmSettings.setTitle("Serial - SAM34");
		frmSettings.setBounds(100, 100, 1009, 505);
		frmSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel Configure = new JPanel();
		Configure.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frmSettings.getContentPane().add(Configure, BorderLayout.CENTER);
		Configure.setLayout(null);
		
		JPanel ConfigurePort = new JPanel();
		ConfigurePort.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "CONFIGURE PORT", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		ConfigurePort.setBackground(Color.LIGHT_GRAY);
		ConfigurePort.setBounds(728, 21, 249, 263);
		Configure.add(ConfigurePort);
		ConfigurePort.setLayout(null);
		
		JLabel comPortLabel = new JLabel("COM PORT");
		comPortLabel.setBounds(10, 27, 81, 14);
		ConfigurePort.add(comPortLabel);
		
		
		JLabel baudRateLabel = new JLabel("BAUD RATE");
		baudRateLabel.setBounds(10, 60, 89, 14);
		ConfigurePort.add(baudRateLabel);
		
		
		comPortBox = new JComboBox();
		comPortBox.setBounds(115, 23, 100, 22);
		ConfigurePort.add(comPortBox);
		comPortBox.setEnabled(true);
		comPortBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				comPortBox.removeAllItems();
				SerialPort[] ports = SerialPort.getCommPorts();
				
				for(SerialPort port : ports) {
					comPortBox.addItem(port.getSystemPortName());
				}
			}
		});
		
		
		baudRateBox = new JComboBox();
		baudRateBox.setBounds(115, 56, 100, 22);
		ConfigurePort.add(baudRateBox);
		baudRateBox.setModel(new DefaultComboBoxModel(new String[] {"4800", "9600", "38400", "57600", "115200"}));
		baudRateBox.setSelectedItem("115200");
		
		JLabel dataBitsLabel = new JLabel("DATA BITS");
		dataBitsLabel.setBounds(10, 93, 89, 14);
		ConfigurePort.add(dataBitsLabel);
		
		dataBitsBox = new JComboBox();
		dataBitsBox.setModel(new DefaultComboBoxModel(new String[] {"5", "6", "7", "8"}));
		dataBitsBox.setSelectedItem("8");
		dataBitsBox.setBounds(115, 89, 100, 22);
		ConfigurePort.add(dataBitsBox);
		
		JLabel lblStopBits = new JLabel("STOP BITS");
		lblStopBits.setBounds(10, 126, 81, 14);
		ConfigurePort.add(lblStopBits);
		
		stopBitBox = new JComboBox();
		stopBitBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2"}));
		stopBitBox.setBounds(115, 122, 100, 22);
		ConfigurePort.add(stopBitBox);
		
		JLabel lblParityBits = new JLabel("PARITY BITS");
		lblParityBits.setBounds(10, 159, 81, 14);
		ConfigurePort.add(lblParityBits);
		
		ParityBitsBox = new JComboBox();
		ParityBitsBox.setModel(new DefaultComboBoxModel(new String[] {"NO_PARITY", "EVEN_PARITY", "COD_PARITY", "MARK_PARITY", "SPACE_PARITY"}));
		ParityBitsBox.setBounds(115, 155, 100, 22);
		ConfigurePort.add(ParityBitsBox);
		
		statusSerial = new JProgressBar();
		statusSerial.setForeground(Color.GREEN);
		statusSerial.setBounds(10, 236, 89, 14);
		ConfigurePort.add(statusSerial);
		statusSerial.setValue(0);
		
		statusSerial2 = new JProgressBar();
		statusSerial2.setValue(100);
		statusSerial2.setForeground(Color.RED);
		statusSerial2.setBounds(126, 236, 89, 14);
		ConfigurePort.add(statusSerial2);
			
		JPanel ReceiveInfo = new JPanel();
		ReceiveInfo.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "RECEIVE DATA", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		ReceiveInfo.setBackground(Color.LIGHT_GRAY);
		ReceiveInfo.setBounds(22, 21, 678, 151);
		Configure.add(ReceiveInfo);
		ReceiveInfo.setLayout(null);
	
		btnCloseSerial = new JButton("CLOSE");
		btnCloseSerial.setBounds(126, 202, 89, 23);
		ConfigurePort.add(btnCloseSerial);
		btnCloseSerial.setEnabled(false);
		btnCloseSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCloseSerialActionPerformed (e);
			}
		});
		
		btnOpenSerial = new JButton("OPEN");
		btnOpenSerial.setEnabled(true);
		btnOpenSerial.setBounds(10, 202, 89, 23);
		ConfigurePort.add(btnOpenSerial);
		btnOpenSerial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnOpenSerialActionPerformed(e);
			}
		});
		
		
		JPanel DataLora = new JPanel();
		DataLora.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "SEND DATA", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		DataLora.setBackground(Color.LIGHT_GRAY);
		DataLora.setBounds(22, 183, 678, 252);
		Configure.add(DataLora);
		DataLora.setLayout(null);
		
		JLabel lblCmdSerial = new JLabel("SERIAL CMD");
		lblCmdSerial.setBounds(20, 95, 93, 14);
		DataLora.add(lblCmdSerial);
		
		JLabel lblCmdType = new JLabel("CMD TYPE");
		lblCmdType.setBounds(154, 95, 73, 14);
		DataLora.add(lblCmdType);
		
		JLabel lblLenPayload = new JLabel("COMMAND CONTENT (HEX)");
		lblLenPayload.setBounds(382, 95, 159, 14);
		DataLora.add(lblLenPayload);
		
		serialCmdBox = new JComboBox();
		serialCmdBox.setModel(new DefaultComboBoxModel(new String[] {"SETTINGS", "TX MESSAGE", "STATUS REQUEST", "REPORT REQUEST"}));
		serialCmdBox.setBounds(20, 120, 124, 22);
		DataLora.add(serialCmdBox);
		serialCmdBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        
		        String test = " ";
				test = (String) serialCmdBox.getSelectedItem();
				switch(test) {
				case "SETTINGS": 
					cmdBoxType.setModel(new DefaultComboBoxModel(new String[] {"DEF CONFIG/JOIN", "REG BAND", "DEV EUI", "MAX PYLOAD LEN", "OTAA APP EUI","OTAA APP KEY","ABP APPSKEY","ABP NWSKEY","ABP DEV ADDR","LORA CLASS","LORA FPORT","LORA ADR", "SET CONFIG AND JOIN"}));
					break;
				case "TX MESSAGE":
					cmdBoxType.setModel(new DefaultComboBoxModel(new String[] {"CONFIRMATION TYPE UNCONFIRMED", "CONFIRMATION TYPE CONFIRMED", "PERIODIC SEND"}));
					break;
				case "STATUS REQUEST":
					cmdBoxType.setModel(new DefaultComboBoxModel(new String[] {"STATUS TOTAL DEBUG", "ACT TYPE", "REG_BAND","DEV ADDR","DEV_EUI","MAX PAYLOAD", "CLASS", "FPORT", "ADR"}));
					break;
				case "REPORT REQUEST":
					cmdBoxType.setModel(new DefaultComboBoxModel(new String[] {"TOTAL REPORT REQ", "LAST RESET CAUSE", "RESTORATION ERROR", "JOIN ERROR/STATUS", "NEW MSG RECEIVED", "RX MSG ERROR", "TX MSG ERROR", "STACK ERROR"}));
					break;
				}
		    }
		});
		
		cmdBoxType = new JComboBox();
		cmdBoxType.setModel(new DefaultComboBoxModel(new String[] {"DEF CONFIG/JOIN", "REG BAND", "DEV EUI", "MAX PYLOAD LEN", "OTAA APP EUI","OTAA APP KEY","ABP APPSKEY","ABP NWSKEY","ABP DEV ADDR","LORA CLASS","LORA FPORT","LORA ADR", "SET CONFIG AND JOIN"}));
		cmdBoxType.setBounds(154, 120, 218, 22);
		DataLora.add(cmdBoxType);
		
		commandArea = new JTextArea();
		commandArea.setBounds(382, 119, 258, 22);
		
		DataLora.add(commandArea);
		
		textAreaReceive = new JTextArea();
		textAreaReceive.setBounds(22, 22, 634, 82);
		ReceiveInfo.add(textAreaReceive);
		textAreaReceive.setEditable(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 2, 2);
		ReceiveInfo.add(scrollPane);
		
		btnClear = new JButton("CLEAR");
		btnClear.setEnabled(false);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaReceive.selectAll();
				textAreaReceive.replaceSelection(" ");
				dataBuffer = " ";
				btnClear.setEnabled(false);
							
			}
		});
		btnClear.setBounds(567, 115, 89, 23);
		ReceiveInfo.add(btnClear);
		
		textAreaSend = new JTextArea();
		textAreaSend.setEnabled(false);
		textAreaSend.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textAreaSend.setBackground(new Color(255, 255, 255));
		textAreaSend.setBounds(116, 35, 524, 22);
		textAreaSend.setText("---------------------------------------------------------------------------------------------------------------------------------------");
		DataLora.add(textAreaSend);
		
		JLabel lblTextSend = new JLabel("TEXT TO SEND");
		lblTextSend.setBounds(17, 35, 89, 25);
		DataLora.add(lblTextSend);
		
		rdbtnSelectText = new JRadioButton("", false);
		rdbtnSelectText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnSelectText.isSelected() ){
					textAreaSend.setEnabled(true);
					serialCmdBox.setEnabled(false);
					cmdBoxType.setEnabled(false);
					commandArea.setEnabled(false);
					textAreaSend.setText(" ");
					commandArea.setText("----------------------------------------------------------------");
				}
				
			}
		});
		rdbtnSelectText.setBackground(Color.LIGHT_GRAY);
		rdbtnSelectText.setBounds(646, 35, 26, 23);
		DataLora.add(rdbtnSelectText);
		
		rdbtnSelectSAM = new JRadioButton("", true);
		rdbtnSelectSAM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnSelectSAM.isSelected()) {
					textAreaSend.setEnabled(false);
					serialCmdBox.setEnabled(true);
					cmdBoxType.setEnabled(true);
					commandArea.setEnabled(true);
					commandArea.setText(" ");
					textAreaSend.setText("---------------------------------------------------------------------------------------------------------------------------------------");
					
				}
			}
		});
		rdbtnSelectSAM.setBackground(Color.LIGHT_GRAY);
		rdbtnSelectSAM.setBounds(646, 119, 26, 23);
		DataLora.add(rdbtnSelectSAM);
		
		group1 = new ButtonGroup();
		group1.add(rdbtnSelectSAM);
		group1.add(rdbtnSelectText);
		
		btnSend = new JButton("SEND");
		btnSend.setBounds(552, 200, 105, 23);
		DataLora.add(btnSend);
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSendActionPerfomerd (e);
			}
		});
		
		btnAddMacro = new JButton("ADD MACRO");
		btnAddMacro.setBounds(552, 167, 105, 23);
		DataLora.add(btnAddMacro);
		btnAddMacro.setEnabled(false);
		btnAddMacro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAddMacroActionPerfomerd(e);
			}
		});
		
		
		JButton btnMacro_1 = new JButton("MACRO 1");
		btnMacro_1.setBounds(20, 167, 173, 23);
		DataLora.add(btnMacro_1);
		btnMacro_1.setEnabled(false);
		
		JButton btnMacro_2 = new JButton("MACRO 2");
		btnMacro_2.setBounds(20, 200, 173, 23);
		DataLora.add(btnMacro_2);
		btnMacro_2.setEnabled(false);
		
		JButton btnMacro_3 = new JButton("MACRO 3 ");
		btnMacro_3.setBounds(284, 167, 164, 23);
		DataLora.add(btnMacro_3);
		btnMacro_3.setEnabled(false);
		
		JButton btnMacro_4 = new JButton("MACRO 4 ");
		btnMacro_4.setBounds(284, 200, 164, 23);
		DataLora.add(btnMacro_4);
		btnMacro_4.setEnabled(false);
		
		JButton btnDeletMacro_1 = new JButton("");
		btnDeletMacro_1.setBounds(203, 167, 26, 23);
		DataLora.add(btnDeletMacro_1);
		btnDeletMacro_1.setEnabled(false);
		
		JButton btnDeletMacro_2 = new JButton("");
		btnDeletMacro_2.setBounds(203, 200, 26, 23);
		DataLora.add(btnDeletMacro_2);
		btnDeletMacro_2.setEnabled(false);
		
		JButton btnDeletMacro_3 = new JButton("");
		btnDeletMacro_3.setBounds(458, 167, 26, 23);
		DataLora.add(btnDeletMacro_3);
		btnDeletMacro_3.setEnabled(false);
		
		JButton btnDeletMacro_4 = new JButton("");
		btnDeletMacro_4.setBounds(458, 200, 26, 23);
		DataLora.add(btnDeletMacro_4);
		btnDeletMacro_4.setEnabled(false);
		
		
	}
	
	
	private void btnAddMacroActionPerfomerd (ActionEvent e) {
		
	}
	
	private void btnSendActionPerfomerd (ActionEvent e) {
		
		outputStream1 = serialPort1.getOutputStream();
		String dataToSend = "";
		String serialCmd = "";
		String cmdType = "";
		String content = "";
		
		if(rdbtnSelectText.isSelected()) {
			dataToSend = textAreaSend.getText();
		}
		else if (rdbtnSelectSAM.isSelected()) {	
			
			CmdSam sendSam = new CmdSam();			
			serialCmd = (String) serialCmdBox.getSelectedItem();
			cmdType = (String) cmdBoxType.getSelectedItem();
			content = commandArea.getText();		
			dataToSend = sendSam.getCmdSam(serialCmd, cmdType, content);

		}		
		try {
			outputStream1.write(dataToSend.getBytes());
		}catch (IOException a) {	
			
		}				
	}
	
	
	private void btnCloseSerialActionPerformed (ActionEvent e) {
		
		if(serialPort1.isOpen()) {
			serialPort1.closePort();
			comPortBox.setEnabled(true);
			statusSerial.setValue(0);
			statusSerial2.setValue(100);
			btnCloseSerial.setEnabled(false);
			btnOpenSerial.setEnabled(true);
			btnSend.setEnabled(false);
		}		
	}
	
	//change the name next 
	private void Serial_EventBaseReading(SerialPort activePort) {
		activePort.addDataListener(new SerialPortDataListener() {
			@Override
			public int getListeningEvents() {return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }
			@Override
			public void serialEvent (SerialPortEvent event) {
				byte []newData = event.getReceivedData();
				
				if(dataBuffer != " ") {
					btnClear.setEnabled(true);
				}
				
				for(int i=0; i < newData.length; i++) {
					dataBuffer += (char)newData [i];
					textAreaReceive.setText(dataBuffer);
				}
			}
		});		
	}
	
	
	private void btnOpenSerialActionPerformed (ActionEvent e) {
		try {
			SerialPort [] ports = SerialPort.getCommPorts();
			serialPort1 = ports [comPortBox.getSelectedIndex()];
			serialPort1.setBaudRate(Integer.parseInt(baudRateBox.getSelectedItem().toString()));
			serialPort1.setNumDataBits(Integer.parseInt(dataBitsBox.getSelectedItem().toString()));
			serialPort1.setNumStopBits(Integer.parseInt(stopBitBox.getSelectedItem().toString()));
			serialPort1.setParity(ParityBitsBox.getSelectedIndex());
			serialPort1.openPort();
			
			
			if(serialPort1.isOpen()) {
				JOptionPane.showMessageDialog(null, serialPort1.getDescriptivePortName() + "Sucess OPEN");
				comPortBox.setEnabled(false);
				statusSerial.setValue(100);
				statusSerial2.setValue(0);
				btnCloseSerial.setEnabled(true);
				btnOpenSerial.setEnabled(false);
				btnSend.setEnabled(true);
				Serial_EventBaseReading(serialPort1);
			}else {
				JOptionPane.showMessageDialog(null, serialPort1.getDescriptivePortName() + "Fail OPEN");
			}					
		}
		catch(ArrayIndexOutOfBoundsException a) {
			JOptionPane.showMessageDialog(null, "Please choose COM PORT", "ERROR", ERROR_MESSAGE );
		}
		catch(Exception b) {
			
		}
	}
}



