package com.greenfox.opal.gitinder.model.response;

/**
 * Created by Nagy Dóra on 2017.06.22..
 */

public class SwipingResponse extends BaseResponse {

	private boolean matchStatus;

	public SwipingResponse() {
		super("error", "Unauthorized request!");
	}

	public SwipingResponse(boolean match_status) {
		super("ok", "success");
		this.matchStatus = matchStatus;
	}

	public boolean isMatch_status() {
		return matchStatus;
	}
}
