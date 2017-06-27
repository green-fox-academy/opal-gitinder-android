package com.greenfox.opal.gitinder.model.response;

public class SwipingResponse extends BaseResponse {

	private Match match;

	public SwipingResponse() {
	}

	public SwipingResponse(String message) {
		super("error", message);
	}

	public SwipingResponse(Match match) {
		super("ok", "success");
		this.match = match;
	}

	public Match getMatch() {
		return match;
	}
}
