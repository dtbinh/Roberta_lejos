package EV3_leJOS;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Sound;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class Remote_Roberta {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RemoteEV3 ev3 = null;
		
		try {
			ev3 = new RemoteEV3("10.0.1.1");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ev3.setDefault();
		Sound.beep();
		Sound.beep();

		RMIRegulatedMotor m = ev3.createRegulatedMotor("D", 'L');
		
		try {;
			m.forward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
