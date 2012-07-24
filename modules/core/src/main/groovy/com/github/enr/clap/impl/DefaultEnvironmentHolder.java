package com.github.enr.clap.impl;

import java.io.File;

import javax.inject.Inject;


import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.util.ClasspathUtil;

/*
 * default implementation for environment holder.
 * this class is used as extension-point from the environment holder used in acceptance tests.
 */
public class DefaultEnvironmentHolder implements EnvironmentHolder {

    private Reporter reporter;
    
    private File home;

    private AppMeta meta;
    
    private final String os = System.getProperty("os.name").toLowerCase();
    
    @Inject
    public DefaultEnvironmentHolder(AppMeta meta, Reporter reporter) {
    	this.meta = meta;
    	this.reporter = reporter;
        File location = ClasspathUtil.getClasspathForClass(DefaultEnvironmentHolder.class);
        if (home == null) {
            home = location.getParentFile().getParentFile();
        }
        this.reporter.debug("home = %s", home);
	}
    
	@Override
	public File applicationHome() {
		return home;
	}
	
    public boolean isWindows() {
        return (os.indexOf("win") >= 0);
    }

    public boolean isMac() {
        return (os.indexOf("mac") >= 0);
    }

    public boolean isUnix() {
        return (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0);
    }

	@Override
	public File installationConfigurationDirectory() {
		return new File(home, "conf");
	}

	@Override
	public File systemConfigurationDirectory() {
		File directory = null;
        if (isWindows()) {
            directory = new File("C:/"+meta.name()+"/" + meta.version());
        } else {
        	directory = new File("/etc/"+meta.name()+"/" + meta.version());
        }
        return directory;
	}

	@Override
	public File userConfigurationDirectory() {
        StringBuilder sb = new StringBuilder().append(System.getProperty("user.home")).append(File.separator)
                .append(".").append(meta.name()).append(File.separator).append(meta.version());
        return new File(sb.toString());
	}

	@Override
	public boolean canExit() {
		return true;
	}

}
