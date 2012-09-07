package clap.uat.app.hello;

import com.github.enr.clap.api.AppMeta;

public class HelloMeta implements AppMeta {

	@Override
	public String name() {
		return "hello";
	}

	@Override
	public String version() {
		return "0.1-SNAPSHOT";
	}
	
	@Override
	public String displayName() {
		return "Hello";
	}
}
