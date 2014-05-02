package cab.invoice.generator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import connection.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InvoiceForm {
	
	DatabaseConnection db2  = new DatabaseConnection();
	InvoiceGenerator inv = new InvoiceGenerator();
	Double rateOfCar = 0.0;
	int packageKms,packageHours;

	private JFrame frmCabInvoiceGenerator;
	private JTextField tfBookedBy;
	private JTextField tfUsedBy;
	private JTextField tfCarModel;
	private JTextField tfKms;
	private JTextField tfHours;
	private JTextField tfParking;
	private JTextField tfOthers;
	private JTextField tfDays;
	private JTextField tfNights;
	private JTextField tfBookingAmount;
	private JComboBox branchBox;
	private JComboBox packageBox;
	private JComboBox carSelectBox;
	private JButton btnGenerateInvoice;
	private JTextField tfDD;
	private JTextField tfMM;
	private JTextField tfYYYY;
	private static InvoiceForm window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new InvoiceForm();
					window.frmCabInvoiceGenerator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InvoiceForm() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
			
		frmCabInvoiceGenerator = new JFrame();
		//frmCabInvoiceGenerator.setVisible(true);
		frmCabInvoiceGenerator.setResizable(false);
		frmCabInvoiceGenerator.setTitle("Cab Invoice Generator");
		frmCabInvoiceGenerator.setBounds(100, 100, 482, 453);
		frmCabInvoiceGenerator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCabInvoiceGenerator.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 466, 54);
		frmCabInvoiceGenerator.getContentPane().add(panel_1);
		
		JLabel lblCabInvoiceGenerator = new JLabel("Cab Invoice Generator");
		lblCabInvoiceGenerator.setFont(new Font("Tahoma", Font.BOLD, 28));
		panel_1.add(lblCabInvoiceGenerator);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 204, 255));
		panel_2.setBounds(0, 65, 466, 318);
		frmCabInvoiceGenerator.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblCarBookedBy = new JLabel("Booked by");
		lblCarBookedBy.setBounds(10, 11, 70, 14);
		lblCarBookedBy.setAlignmentY(0.0f);
		lblCarBookedBy.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblCarBookedBy.setVerticalAlignment(SwingConstants.TOP);
		lblCarBookedBy.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblCarBookedBy);
		
		JLabel lblCarUsedBy = new JLabel("Used by");
		lblCarUsedBy.setBounds(10, 35, 58, 14);
		panel_2.add(lblCarUsedBy);
		
		tfBookedBy = new JTextField();
		tfBookedBy.setBounds(90, 8, 147, 20);
		panel_2.add(tfBookedBy);
		tfBookedBy.setColumns(10);
		
		tfUsedBy = new JTextField();
		tfUsedBy.setBounds(90, 32, 147, 20);
		panel_2.add(tfUsedBy);
		tfUsedBy.setColumns(10);
		
		branchBox = new JComboBox();
		branchBox.setBounds(324, 8, 132, 20);
		branchBox.addItem("Delhi");
		branchBox.addItem("Faridabad");
		branchBox.addItem("Gurgaon");
		branchBox.addItem("Noida");
		branchBox.addItem("Ghaziabad");
		branchBox.addItem("Sonipat");
		panel_2.add(branchBox);
		
		JLabel lblRequestedBranch = new JLabel("Branch");
		lblRequestedBranch.setBounds(247, 11, 67, 14);
		panel_2.add(lblRequestedBranch);
		
		JLabel lblPackage = new JLabel("Package");
		lblPackage.setBounds(247, 35, 67, 14);
		panel_2.add(lblPackage);
		
		packageBox = new JComboBox();
		packageBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				try{
					Connection con  = db2.createConnection();
					PreparedStatement statement = con.prepareStatement("select * from packages where packname=?");
					statement.setString(1,packageBox.getSelectedItem().toString());
					ResultSet rs = statement.executeQuery();
					while(rs.next()){
						packageKms = rs.getInt(3);
						packageHours = rs.getInt(2);
						tfHours.setText(Integer.toString(packageHours));
						tfKms.setText(Integer.toString(packageKms));
					}
					con.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(packageBox, e.getMessage());
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			}
		});
		packageBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				try{
					Connection con = db2.createConnection();
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery("select packname from packages");
					packageBox.removeAllItems();			//remove previous list of items before updating the list with new items.
					while(rs.next()){
						packageBox.addItem(rs.getString("packname"));
					}
					con.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(packageBox,e.getMessage());
				}
			}
		});
		packageBox.setBounds(324, 32, 132, 20);
		panel_2.add(packageBox);
		
		JLabel lblCarModel = new JLabel("Select Cab");
		lblCarModel.setBounds(10, 60, 70, 14);
		panel_2.add(lblCarModel);
		
		carSelectBox = new JComboBox();
		carSelectBox.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
				String selectedCarNumber = carSelectBox.getSelectedItem().toString();
				try{
					Connection con = db2.createConnection();
					PreparedStatement statement = con.prepareStatement("select * from cabs where car_number=?");
					statement.setString(1,selectedCarNumber);
					ResultSet rs = statement.executeQuery();
					while(rs.next()){
						tfCarModel.setText(rs.getString(1));
						rateOfCar = rs.getDouble(3);
					}
					con.close();
				}catch(Exception e){
					JOptionPane.showMessageDialog(carSelectBox, e.getMessage());
				}
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			}
		});
		carSelectBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				try{
					Connection con = db2.createConnection();
					String sqlStatement = "select car_number from cabs";
					ResultSet rs = db2.selectStatement(con, sqlStatement);
					carSelectBox.removeAllItems();				//remove previous list of items before updating the list with new items.
					while(rs.next()){
						carSelectBox.addItem(rs.getString("car_number"));						
					}
					con.close();					
				}catch(Exception ee){
					JOptionPane.showMessageDialog(carSelectBox,ee.getMessage());
				}
			}
		});
		carSelectBox.setBounds(90, 57, 147, 20);
		panel_2.add(carSelectBox);
		
		tfCarModel = new JTextField();
		tfCarModel.setEditable(false);
		tfCarModel.setBounds(324, 57, 132, 20);
		panel_2.add(tfCarModel);
		tfCarModel.setColumns(10);
		
		JLabel lblCarModel_1 = new JLabel("Car Model ");
		lblCarModel_1.setBounds(247, 60, 67, 14);
		panel_2.add(lblCarModel_1);
		
		JLabel lblKmsUsed = new JLabel("KMs Used");
		lblKmsUsed.setBounds(10, 85, 70, 14);
		panel_2.add(lblKmsUsed);
		
		tfKms = new JTextField();
		tfKms.setBounds(117, 82, 120, 20);
		panel_2.add(tfKms);
		tfKms.setColumns(10);
		
		JLabel lblHoursUsed = new JLabel("Hours Used");
		lblHoursUsed.setBounds(247, 85, 67, 14);
		panel_2.add(lblHoursUsed);
		
		tfHours = new JTextField();
		tfHours.setBounds(324, 82, 132, 20);
		panel_2.add(tfHours);
		tfHours.setColumns(10);
		
		JLabel lblParkingToll = new JLabel("Parking & Toll");
		lblParkingToll.setBounds(10, 110, 97, 14);
		panel_2.add(lblParkingToll);
		
		tfParking = new JTextField();
		tfParking.setBounds(117, 107, 120, 20);
		panel_2.add(tfParking);
		tfParking.setColumns(10);
		
		JLabel lblOthers = new JLabel("Others");
		lblOthers.setBounds(247, 110, 67, 14);
		panel_2.add(lblOthers);
		
		tfOthers = new JTextField();
		tfOthers.setBounds(324, 107, 132, 20);
		panel_2.add(tfOthers);
		tfOthers.setColumns(10);
		
		JLabel lblOutStationAllowance = new JLabel("Out Station Allowance :  Days");
		lblOutStationAllowance.setBounds(10, 135, 171, 14);
		panel_2.add(lblOutStationAllowance);
		
		tfDays = new JTextField();
		tfDays.setBounds(191, 132, 46, 20);
		panel_2.add(tfDays);
		tfDays.setColumns(10);
		
		tfNights = new JTextField();
		tfNights.setColumns(10);
		tfNights.setBounds(324, 132, 46, 20);
		panel_2.add(tfNights);
		
		JLabel lblNights = new JLabel("Nights");
		lblNights.setBounds(247, 135, 46, 14);
		panel_2.add(lblNights);
		
		JLabel lblBookingAmountPaid = new JLabel("Booking Amount Paid");
		lblBookingAmountPaid.setBounds(10, 160, 132, 14);
		panel_2.add(lblBookingAmountPaid);
		
		tfBookingAmount = new JTextField();
		tfBookingAmount.setBounds(179, 157, 58, 20);
		panel_2.add(tfBookingAmount);
		tfBookingAmount.setColumns(10);
		
		btnGenerateInvoice = new JButton("Generate Invoice");
		btnGenerateInvoice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tfBookedBy.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfUsedBy.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfCarModel.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfBookingAmount.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfDays.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfNights.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfKms.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfHours.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfParking.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else if(tfOthers.getText().isEmpty()){
					JOptionPane.showMessageDialog(btnGenerateInvoice, "Please Fill All the details!");
				}
				else{
					try{
						Connection con = db2.createConnection();
					String selectedPack = packageBox.getSelectedItem().toString();
					int hours = Integer.parseInt(tfHours.getText());
					int kms = Integer.parseInt(tfKms.getText());
					//String carNumber = carSelectBox.getSelectedItem().toString();
					Double rate = rateOfCar; 
					Double eKmsCharge=0.0;
					Double eHoursCharge=0.0;
					Double parking = Double.parseDouble(tfParking.getText());
					Double osaDays = Double.parseDouble(tfDays.getText());
					Double osaNights = Double.parseDouble(tfNights.getText());
					Double others = Double.parseDouble(tfOthers.getText());
					//calculation of extra Hours and Kms charges
					if(kms>packageKms){
						eKmsCharge = rate*1.1*(kms-packageKms);
					}
					if(hours>packageHours){
						eHoursCharge = (double) (50*(hours-packageHours));
					}
				
					//calculation of subtotal(a)
					Double subTotalA = (packageKms*rate)+eKmsCharge+eHoursCharge;
					Double subTotalB = parking+osaDays+osaNights+others;
					Double total = subTotalA+subTotalB;
					Double serviceTax = 0.04994*total;
					Double finalAmount = total+serviceTax;
					String invoiceID = inv.invoiceIDGenerator();
					
						
						String ins = "insert into invoices values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement st = con.prepareStatement(ins);
						st.setString(1, invoiceID);			//returns the unique 9-digit alphanumeric code to be used as Invoice ID
						st.setString(2, tfBookedBy.getText());
						st.setString(3, tfUsedBy.getText());
						st.setString(4, branchBox.getSelectedItem().toString());
						st.setInt(5,Integer.parseInt(tfDD.getText()));
						st.setInt(6,Integer.parseInt(tfMM.getText()));
						st.setInt(7, Integer.parseInt(tfYYYY.getText()));
						st.setInt(8, kms);				//for used Kms
						st.setInt(9, hours);			//for used Hours
						st.setInt(10, packageKms);		//For Package Kms
						st.setInt(11, packageHours);	//For Package Hours
						st.setDouble(12, rate);
						st.setDouble(13, eKmsCharge);		//extra kms charge
						st.setDouble(14, eHoursCharge);		//extra hours charge
						st.setDouble(15, osaDays);	//OutStation Allowance for days	
						st.setDouble(16, osaNights);	//OutStation Allowance for Nights
						st.setDouble(17, parking); 	//parking and toll charges
						st.setDouble(18, others); 	//Other Charges
						st.setDouble(19, subTotalA);	//for Sub Total A
						st.setDouble(20, subTotalB);	//for Sub Total B
						st.setDouble(21, serviceTax);	//for Service Tax
						st.setDouble(22, Double.parseDouble(tfBookingAmount.getText()));
						st.setDouble(23, finalAmount);
						int status = st.executeUpdate();
						if(status>0){
							JOptionPane.showMessageDialog(btnGenerateInvoice, "Invoice Generated. View/Print Invoice");
							inv.writeToFile(invoiceID,carSelectBox.getSelectedItem().toString(),tfCarModel.getText(), packageBox.getSelectedItem().toString());
						}
						else
							JOptionPane.showMessageDialog(btnGenerateInvoice, "Error");
						con.close();
						
					}catch(Exception e){
						JOptionPane.showMessageDialog(btnGenerateInvoice, e.getMessage()+"Exception Outer Method");
					}
					
				}
			}
		});
		btnGenerateInvoice.setBounds(106, 202, 131, 23);
		panel_2.add(btnGenerateInvoice);
		
		JButton btnResetInformation = new JButton("Reset Info");
		btnResetInformation.setBounds(247, 202, 120, 23);
		panel_2.add(btnResetInformation);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setBounds(247, 160, 67, 14);
		panel_2.add(lblDate);
		
		tfDD = new JTextField();
		tfDD.setBounds(324, 157, 30, 20);
		panel_2.add(tfDD);
		tfDD.setColumns(10);
		
		tfMM = new JTextField();
		tfMM.setBounds(364, 157, 30, 20);
		panel_2.add(tfMM);
		tfMM.setColumns(10);
		
		tfYYYY = new JTextField();
		tfYYYY.setBounds(404, 157, 52, 20);
		panel_2.add(tfYYYY);
		tfYYYY.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frmCabInvoiceGenerator.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewInvoice = new JMenuItem("New Invoice");
		mnFile.add(mntmNewInvoice);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.frmCabInvoiceGenerator.dispose();
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnPreferences = new JMenu("Preferences");
		menuBar.add(mnPreferences);
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PackageSettings.main(null);
			}
		});
		mnPreferences.add(mntmSettings);
		
		JMenuItem mntmCompany = new JMenuItem("Modify Company Details");
		mntmCompany.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CompanyDetails.main(null);
			}
		});
		mnPreferences.add(mntmCompany);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAboutInvoiceGenerator = new JMenuItem("About Invoice Generator");
		mnHelp.add(mntmAboutInvoiceGenerator);
	}
}
