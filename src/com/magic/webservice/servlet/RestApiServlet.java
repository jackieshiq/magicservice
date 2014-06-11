package com.magic.webservice.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.magic.tomcat.TomcatEventSubscriber;
import com.magic.tomcat.TomcatImpl;
import com.magic.tomcat.TomcatObjectSubscriber;
import com.magic.webservice.process.MagicServiceMain;
import com.magic.webservice.util.Constants;
import com.magic.webservice.util.Log4jLoad;

/**
 * Servlet implementation class RestApiServlet
 */
public class RestApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static Logger logger = Logger.getLogger(Constants.log4jPrefix+RestApiServlet.class.getName());
	MagicServiceMain main;
	
	/**
	 * start up tasks
	 */
    public void init(ServletConfig config) throws ServletException 
    {
        //must load the init-parameter from web.xml
        super.init(config);
        //load log4j properties file
        Log4jLoad.loadAndConfigLog4j("log4j.properties");
          
        //start main thread to register listener with TomcatSubscriber, 
        MagicServiceMain main = new MagicServiceMain();
        Thread t= new Thread(main);
        t.start();
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestApiServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * clean up
	 */
	@Override
	public void destroy() {
		if (main !=null)
			main.unregisterListenersFromTomcat();
		super.destroy();
	}
	
	

}
