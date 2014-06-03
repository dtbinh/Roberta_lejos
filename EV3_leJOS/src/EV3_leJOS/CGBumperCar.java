package EV3_leJOS;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

/**
 * Demonstration of the Behavior subsumption classes.
 * 
 * Requires a wheeled vehicle with two independently controlled
 * motors connected to motor ports A and C, and 
 * a touch sensor connected to sensor  port 1 and
 * an ultrasonic sensor connected to port 3;
 * 
 * @author Brian Bagnall and Lawrie Griffiths, modified by Roger Glassey
 *
 */
public class CGBumperCar
{
    //static RegulatedMotor leftMotor = MirrorMotor.invertMotor(Motor.A);
    //static RegulatedMotor rightMotor = MirrorMotor.invertMotor(Motor.B);
    static RegulatedMotor leftMotor = Motor.A;
    static RegulatedMotor rightMotor = Motor.B;
    static IRSensor sensor;
  
  // Use these definitions instead if your motors are inverted
  // static RegulatedMotor leftMotor = MirrorMotor.invertMotor(Motor.A);
  //static RegulatedMotor rightMotor = MirrorMotor.invertMotor(Motor.C);
  
  public static void main(String[] args)
  {
      leftMotor.resetTachoCount();
      rightMotor.resetTachoCount();
    leftMotor.rotateTo(0);
    rightMotor.rotateTo(0);
    leftMotor.setSpeed(400);
    rightMotor.setSpeed(400);
    leftMotor.setAcceleration(800);
    rightMotor.setAcceleration(800);
    sensor = new IRSensor();
    sensor.setDaemon(true);
    sensor.start();
    Behavior b1 = new DriveForward();
    Behavior b2 = new DetectWall();
    Behavior[] behaviorList =
    {
      b1, b2
    };
    Arbitrator arbitrator = new Arbitrator(behaviorList);
    LCD.drawString("Bumper Car",0,1);
    Button.LEDPattern(6);
    Button.waitForAnyPress();
    arbitrator.start();
  }
}

class IRSensor extends Thread
{
    EV3IRSensor ir = new EV3IRSensor(SensorPort.S1);
    public int control = 0;
    public int distance = 255;

    IRSensor()
    {

    }
    
    public void run()
    {
        while (true)
        {
            float [] sample = new float[ir.sampleSize()];
            control = ir.getRemoteCommand(0);
            ir.fetchSample(sample, 0);
            distance = (int)sample[0];
            System.out.println("Control: " + control + " Distance: " + distance);
            
        }
        
    }
}

class DriveForward implements Behavior
{

  private boolean _suppressed = false;

  public boolean takeControl()
  {
      if (Button.readButtons() != 0)
      {
          _suppressed = true;
          CGBumperCar.leftMotor.stop();
          CGBumperCar.rightMotor.stop();          
          Button.LEDPattern(6);
          Button.discardEvents();
          System.out.println("Button pressed");
          if ((Button.waitForAnyPress() & Button.ID_ESCAPE) != 0)
          {
              Button.LEDPattern(0);
              System.exit(1);
          }
          System.out.println("Button pressed 2");
          Button.waitForAnyEvent();
          System.out.println("Button released");
      }
    return true;  // this behavior always wants control.
  }

  public void suppress()
  {
    _suppressed = true;// standard practice for suppress methods
  }

  public void action()
  {
    _suppressed = false;
    //CGBumperCar.leftMotor.forward();
    //CGBumperCar.rightMotor.forward();
    while (!_suppressed)
    {
      //CGBumperCar.leftMotor.forward();
      //CGBumperCar.rightMotor.forward();
        switch(CGBumperCar.sensor.control)
        {
        case 0:
            CGBumperCar.leftMotor.setSpeed(400);
            CGBumperCar.rightMotor.setSpeed(400);
            CGBumperCar.leftMotor.stop(true);
            CGBumperCar.rightMotor.stop(true);
            break;
        case 1:
            CGBumperCar.leftMotor.setSpeed(400);
            CGBumperCar.rightMotor.setSpeed(400);
            CGBumperCar.leftMotor.forward();
            CGBumperCar.rightMotor.forward();
            break;
        case 2:
            CGBumperCar.leftMotor.backward();
            CGBumperCar.rightMotor.backward();
            break;
        case 3:
            CGBumperCar.leftMotor.setSpeed(200);
            CGBumperCar.rightMotor.setSpeed(200);
            CGBumperCar.leftMotor.forward();
            CGBumperCar.rightMotor.backward();
            break;
        case 4:
            CGBumperCar.leftMotor.setSpeed(200);
            CGBumperCar.rightMotor.setSpeed(200);
            CGBumperCar.leftMotor.backward();
            CGBumperCar.rightMotor.forward();
            break;

            
        }
      Thread.yield(); //don't exit till suppressed
    }
    //CGBumperCar.leftMotor.stop(true); 
    //CGBumperCar.rightMotor.stop(true);
    //CGBumperCar.leftMotor.
  }
}


class DetectWall implements Behavior
{

  public DetectWall()
  {
    //touch = new TouchSensor(SensorPort.S1);
    //sonar = new UltrasonicSensor(SensorPort.S3);
  }
  
  
  private boolean checkDistance()
  {

      int dist = CGBumperCar.sensor.distance;
      if (dist < 30)
      {
          Button.LEDPattern(2);
          return true;
      }
      else
      {
          Button.LEDPattern(1);
          return false;
      }
  }
  
  public boolean takeControl()
  {
    return checkDistance();
  }

  public void suppress()
  {
    //Since  this is highest priority behavior, suppress will never be called.
  }

  public void action()
  {
      CGBumperCar.leftMotor.rotate(-180, true);// start Motor.A rotating backward
      CGBumperCar.rightMotor.rotate(-180);  // rotate C farther to make the turn
    if ((System.currentTimeMillis() & 0x1) != 0)
    {
        CGBumperCar.leftMotor.rotate(-180, true);// start Motor.A rotating backward
        CGBumperCar.rightMotor.rotate(180);  // rotate C farther to make the turn
    }
    else
    {
        CGBumperCar.rightMotor.rotate(-180, true);// start Motor.A rotating backward
        CGBumperCar.leftMotor.rotate(180);  // rotate C farther to make the turn        
    }
  }
  
}