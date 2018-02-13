package view;


import java.lang.reflect.InvocationTargetException;

class SplashThread extends Thread {	
	private SplashScreen splash;
	
	public SplashThread(SplashScreen splashScreen) {
		splash = splashScreen; 
	}
	public void run() {
		try {
			sleep(3000);
			splash.close();
			FerrareView application = new FerrareView();				
			application.getJFrame().setVisible(true);			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("Starting the application");
	}
}