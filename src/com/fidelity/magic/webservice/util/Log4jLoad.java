package com.fidelity.magic.webservice.util;

import java.io.*;
import java.util.*;

import org.apache.log4j.Logger; //log4j
import org.apache.log4j.PropertyConfigurator;


/**
 * @author a322448
 *
 * A utility class that load log4j.properties file.
 */
public class Log4jLoad {
	static Logger logger = Logger.getLogger(Constants.log4jPrefix+Log4jLoad.class.getName());
	
	public static Properties loadPropertiesFile(String fileName) 
	{
		Properties prop = new Properties();
		InputStream is = null;
		try
		{
			
			//load props with ClassLoader
			ClassLoader loader = Log4jLoad.class.getClassLoader();
			is = loader.getResourceAsStream( fileName );
			
			if( is != null )
			{
				try
				{
					prop.load( is );
					logger.info("successfully loaded properties from property file " +fileName);
				}
				catch( IOException e )
				{
					logger.error( "Error loading property file" +e.getMessage());
				}
			}else
			{
				logger.error( "Error loading property file, inputstream is null, ");	
			}
		}
		catch( Exception e )
		{
			logger.error(e.getMessage());
		}
		finally
		{
			try
			{
				if (is !=null) is.close();
			}
			catch( Exception ex )
			{}
		}
		if (prop !=null)
	           logger.info(prop);
	      
		return prop;
	
	}
	
    public static void loadAndConfigLog4j(String filename)
    {
        try
        {   Properties props = Log4jLoad.loadPropertiesFile(filename);
            if (props !=null)
            {   PropertyConfigurator.configure(props);
                logger.info("succussfully loaded and configurated log4j property file");
            }
        
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    
    }

}
