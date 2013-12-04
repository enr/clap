package clap.uat.app.over;

import com.atoito.clap.api.AppMeta;

public class OverMeta implements AppMeta {

	@Override
	public String name() {
		return "over";
	}

	@Override
	public String version() {
		return "3.2.1";
	}
	
	@Override
	public String displayName() {
		return "OverApp";
	}
}
