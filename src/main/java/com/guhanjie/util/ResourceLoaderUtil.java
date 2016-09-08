package com.guhanjie.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * Class Name:		ResourceLoaderUtil<br/>
 * Description:		[description]
 * @time				2016年9月8日 下午2:18:49
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class ResourceLoaderUtil {
	
	public static File getResource(String name) throws Exception {
		URI uri = locate(name);
		if(uri == null) {
			return null;
		}
		return new File(uri);
	}
	
	private static URI locate(String name) throws Exception {
        if (name == null) {
            // undefined, always return null
            return null;
        }
        URI uri = null;        
        // attempt to create an URL directly
        try {
            uri = new URI(name);
            // check if the file exists
            InputStream in = null;
            try {
                in = uri.toURL().openStream();
            }
            finally {
                if (in != null) {
                    in.close();
                }
            }
        }
        catch (IOException e) {
            uri = null;
        }

        // attempt to load from an absolute path
        if (uri == null) {
            File file = new File(name);
            if (file.isAbsolute() && file.exists()) {	// already absolute?
                uri = file.toURI();
            }
        }

        // attempt to load from the base directory
        if (uri == null) {
            File file = new File(name);
            if (file != null && file.exists()) {
                uri = file.toURI();
            }
        }

        // attempt to load from the user home directory
        if (uri == null) {
            File file = new File(System.getProperty("user.home"), name);
            if (file != null && file.exists()) {
                uri = file.toURI();
            }
        }

        // attempt to load from classpath
        if (uri == null) {
        	URL url = null;
        	// attempt to load from the context classpath
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader != null) {
            	url = loader.getResource(name);
            }
            // attempt to load from the system classpath
            if (url == null) {
            	url = ClassLoader.getSystemResource(name);
            }
            if(url != null) {
            	uri = url.toURI();
            }
        }
        return uri;
    }
	
}
