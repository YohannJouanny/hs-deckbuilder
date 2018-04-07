package donnees;

import java.io.Serializable;

public class Config implements Serializable {
	private static final long serialVersionUID = -6529134948650046705L;
	
	
	private boolean hideWildExtensions;
	
	public Config() {
		hideWildExtensions = false;
	}

	public boolean isHideWildExtensions() {
		return hideWildExtensions;
	}

	public void setHideWildExtensions(boolean hideWildExtensions) {
		this.hideWildExtensions = hideWildExtensions;
	}

}
