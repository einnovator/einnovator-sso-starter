package org.einnovator.sso.client.modelx;

import java.util.Arrays;
import java.util.Date;

import org.einnovator.sso.client.model.Address;
import org.einnovator.sso.client.model.GenderType;
import org.einnovator.util.model.ToStringCreator;

public class MemberFilter extends UserFilter {

	private String title;
	

	public MemberFilter() {
	}

	
	/**
	 * Get the value of property {@code title}.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}


	/**
	 * Set the value of property {@code title}.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}


	/* (non-Javadoc)
	 * @see org.einnovator.util.model.ObjectBase#toString(org.einnovator.util.model.ToStringCreator)
	 */
	@Override
	public ToStringCreator toString(ToStringCreator creator) {
		return super.toString(creator)
				.append("title", title)
				;
	}
	
}
