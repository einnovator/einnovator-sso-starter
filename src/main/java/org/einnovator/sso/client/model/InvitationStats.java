package org.einnovator.sso.client.model;

import org.einnovator.util.model.ObjectBase;
import org.einnovator.util.model.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A descriptor with stats of {@code Invitation}s.
 *
 * @author support@einnovator.org
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class InvitationStats extends ObjectBase {

	private Integer sent;

	private Integer accepted;

	private Integer rejected;

	private Integer pending;

	private Integer failed;

	private Integer requests;

	private Integer count;

	public InvitationStats() {
	}

	public Integer getSent() {
		return sent;
	}

	public void setSent(Integer sent) {
		this.sent = sent;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	public Integer getRejected() {
		return rejected;
	}

	public void setRejected(Integer rejected) {
		this.rejected = rejected;
	}

	public Integer getPending() {
		return pending;
	}

	public void setPending(Integer pending) {
		this.pending = pending;
	}

	public Integer getFailed() {
		return failed;
	}

	public void setFailed(Integer failed) {
		this.failed = failed;
	}

	public Integer getRequests() {
		return requests;
	}

	public void setRequests(Integer requests) {
		this.requests = requests;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public ToStringCreator toString1(ToStringCreator builder) {
		return builder
				.append("sent", sent)
				.append("accepted", accepted)
				.append("rejected", rejected)
				.append("pending", pending)
				.append("failed", failed)
				.append("requests", requests)
				.append("count", count)
				;
	}


}
