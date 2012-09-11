package clap.uat.app.noop;

import com.github.enr.clap.api.AppMeta;

public class NoopMeta implements AppMeta {

	@Override
	public String name() {
		return "noop-app";
	}

	@Override
	public String version() {
		return "0.1.2";
	}
	
	@Override
	public String displayName() {
		return "Noop App";
	}
}
