package com.greenfox.opal.gitinder.model.response;

/**
 * Created by Nagy Dóra on 2017.06.22..
 */

public class SwipingResponse extends BaseResponse {

	private boolean match_status;

	public SwipingResponse() {
		super("error", "Unauthorized request!");
	}

	public SwipingResponse(boolean match_status) {
		super("ok", "success");
		this.match_status = match_status;
	}

	public boolean isMatch_status() {
		return match_status;
	}
}
