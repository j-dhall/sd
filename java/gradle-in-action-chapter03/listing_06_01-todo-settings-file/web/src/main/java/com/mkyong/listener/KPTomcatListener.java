package com.mkyong.listener;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;

public class KPTomcatListener implements LifecycleListener {

    //private static final Logger LOG = LoggerFactory.getLogger(KPTomcatListener.class);
    /**
     * All the events of tomcat
     * AFTER_START_EVENT, 
     * AFTER_STOP_EVENT, 
     * BEFORE_START_EVENT, 
     * BEFORE_STOP_EVENT, 
     * DESTROY_EVENT, 
     * INIT_EVENT, 
     * PERIODIC_EVENT, 
     * START_EVENT, 
     * STOP_EVENT
     */
    private static int counter;

    @Override
    public void lifecycleEvent(LifecycleEvent arg0) {
        String event = arg0.getType();
        //LOG.debug
		System.out.println("Tomcat Envents: " + (++counter) + " :: " + event);
        if(event.equals("AFTER_START_EVENT")) {
            //LOG.debug
			System.out.println("Hey I've started");
        }
    }

}


