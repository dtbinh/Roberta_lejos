package EV3_leJOS;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;

import lejos.hardware.BrickFinder;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;


public class Remote_Control extends JFrame{
	
	JFrame frame = new JFrame();
	
	RemoteEV3 ev3 = (RemoteEV3) BrickFinder.getDefault();
	final RMIRegulatedMotor leftMotor = ev3.createRegulatedMotor("D", 'L');
	final RMIRegulatedMotor rightMotor = ev3.createRegulatedMotor("A", 'L');
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Remote_Control window = new Remote_Control();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Remote_Control() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
	    
	    try {
	    	leftMotor.setSpeed(400);
			rightMotor.setSpeed(400);
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
      
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
	        @Override
	        public void windowClosing(WindowEvent e) {
	            try {
					leftMotor.close();
					rightMotor.close();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        
	            System.exit(0);
	        }

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	        
		
		JButton btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            try {
	            	leftMotor.rotate(100,true);
		            rightMotor.rotate(100);
		        } catch (RemoteException exp) {
		               exp.printStackTrace();
		        }
			}
		});
		frame.getContentPane().add(btnForward, BorderLayout.NORTH);
		
		JButton btnBackward = new JButton("Backward");
		btnBackward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            try {
	            	leftMotor.rotate(-100,true);
		            rightMotor.rotate(-100);
		        } catch (RemoteException exp) {
		               exp.printStackTrace();
		        }
			}
		});
		frame.getContentPane().add(btnBackward, BorderLayout.SOUTH);
		
		JButton btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            try {
	            	leftMotor.rotate(-100,true);
		            rightMotor.rotate(100);
		        } catch (RemoteException exp) {
		               exp.printStackTrace();
		        }
			}
		});
		frame.getContentPane().add(btnLeft, BorderLayout.WEST);
		
		JButton btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            try {
	            	leftMotor.rotate(100,true);
		            rightMotor.rotate(-100);
		        } catch (RemoteException exp) {
		               exp.printStackTrace();
		        }
			}
		});
		frame.getContentPane().add(btnRight, BorderLayout.EAST);
		
		JButton btnOff = new JButton("Off");
		btnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Keyboard");
			}
		});
		frame.getContentPane().add(btnOff, BorderLayout.CENTER);
		
		
		btnOff.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stu	
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
	            try {
	            	leftMotor.close();
	            	leftMotor.close();
		        } catch (RemoteException exp) {
		               exp.printStackTrace();
		        }
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			    int keyCode = e.getKeyCode();
			    switch( keyCode ) { 
			        case KeyEvent.VK_UP:
			            try {
			            	leftMotor.rotate(50, true);
			            	leftMotor.rotate(50, true);
			            	Delay.msDelay(500);
				        } catch (RemoteException exp) {
				               exp.printStackTrace();
				        }
			            // handle up 
			            break;
			        case KeyEvent.VK_DOWN:
			            try {
			            	leftMotor.rotate(-50, true);
				            rightMotor.rotate(-50, true);
				        } catch (RemoteException exp) {
				               exp.printStackTrace();
				        }
			            // handle down 
			            break;
			        case KeyEvent.VK_LEFT:
			            try {
			            	leftMotor.rotate(-50, true);
				            rightMotor.rotate(50, true);
				        } catch (RemoteException exp) {
				               exp.printStackTrace();
				        }
			            // handle left
			            break;
			        case KeyEvent.VK_RIGHT :
			            try {
			            	leftMotor.rotate(50, true);
				            rightMotor.rotate(-50, true);
				        } catch (RemoteException exp) {
				               exp.printStackTrace();
				        }
			            // handle right
			            break;
			     }
				} 
				// TODO Auto-generated method stub
		});
	}

}
