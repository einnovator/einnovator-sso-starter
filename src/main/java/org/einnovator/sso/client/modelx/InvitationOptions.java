package org.einnovator.sso.client.modelx;

import org.einnovator.sso.client.model.Invitation;
import org.einnovator.util.model.EntityOptions;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvitationOptions extends EntityOptions<Invitation> {

	private Boolean sendMail;

	private Boolean sendSms;

	/**
	 * Create instance of {@code InvitationOptions}.
	 *
	 */
	public InvitationOptions() {
	}

	/**
	 * Get the value of property {@code sendMail}.
	 *
	 * @return the sendMail
	 */
	public Boolean getSendMail() {
		return sendMail;
	}

	/**
	 * Set the value of property {@code sendMail}.
	 *
	 * @param sendMail the value of property sendMail
	 */
	public void setSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
	}

	/**
	 * Get the value of property {@code sendSms}.
	 *
	 * @return the sendSms
	 */
	public Boolean getSendSms() {
		return sendSms;
	}

	/**
	 * Set the value of property {@code sendSms}.
	 *
	 * @param sendSms the value of property sendSms
	 */
	public void setSendSms(Boolean sendSms) {
		this.sendSms = sendSms;
	}

	//
	// With
	//


	/**
	 * Set the value of property {@code sendMail}.
	 *
	 * @param sendMail the value of property sendMail
	 * @return this {@code InvitationOptions}
	 */
	public InvitationOptions withSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
		return this;
	}

	/**
	 * Set the value of property {@code sendSms}.
	 *
	 * @param sendSms the value of property sendSms
	 * @return this {@code InvitationOptions}
	 */
	public InvitationOptions withSendSms(Boolean sendSms) {
		this.sendSms = sendSms;
		return this;
	}
	
	@Override
	public ToStringCreator toString1(ToStringCreator creator) {
		return super.toString1(creator)
			.append("sendMail", sendMail)
			.append("sendSms", sendSms)
			;
	}
}
