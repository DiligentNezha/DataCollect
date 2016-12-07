package io.vicp.goradical.datacollect.entity;

public class ActionInfo {
	private int actionInfoId;
	private int fileInfoId;
	private int totalPlay;
	private int totalGlance;
	private int totalComment;
	private int totalMark;
	private int totalLike;
	private int totalDislike;
	private int totalCollect;
	private int totalSearch;
	private int totalRecommend;

	public ActionInfo() {
	}

	public int getActionInfoId() {
		return actionInfoId;
	}

	public void setActionInfoId(int actionInfoId) {
		this.actionInfoId = actionInfoId;
	}

	public int getFileInfoId() {
		return fileInfoId;
	}

	public void setFileInfoId(int fileInfoId) {
		this.fileInfoId = fileInfoId;
	}

	public int getTotalPlay() {
		return totalPlay;
	}

	public void setTotalPlay(int totalPlay) {
		this.totalPlay = totalPlay;
	}

	public int getTotalGlance() {
		return totalGlance;
	}

	public void setTotalGlance(int totalGlance) {
		this.totalGlance = totalGlance;
	}

	public int getTotalComment() {
		return totalComment;
	}

	public void setTotalComment(int totalComment) {
		this.totalComment = totalComment;
	}

	public int getTotalMark() {
		return totalMark;
	}

	public void setTotalMark(int totalMark) {
		this.totalMark = totalMark;
	}

	public int getTotalLike() {
		return totalLike;
	}

	public void setTotalLike(int totalLike) {
		this.totalLike = totalLike;
	}

	public int getTotalDislike() {
		return totalDislike;
	}

	public void setTotalDislike(int totalDislike) {
		this.totalDislike = totalDislike;
	}

	public int getTotalCollect() {
		return totalCollect;
	}

	public void setTotalCollect(int totalCollect) {
		this.totalCollect = totalCollect;
	}

	public int getTotalSearch() {
		return totalSearch;
	}

	public void setTotalSearch(int totalSearch) {
		this.totalSearch = totalSearch;
	}

	public int getTotalRecommend() {
		return totalRecommend;
	}

	public void setTotalRecommend(int totalRecommend) {
		this.totalRecommend = totalRecommend;
	}

	@Override
	public String toString() {
		return "ActionInfo{" +
				"actionInfoId=" + actionInfoId +
				", fileInfoId=" + fileInfoId +
				", totalPlay=" + totalPlay +
				", totalGlance=" + totalGlance +
				", totalComment=" + totalComment +
				", totalMark=" + totalMark +
				", totalLike=" + totalLike +
				", totalDislike=" + totalDislike +
				", totalCollect=" + totalCollect +
				", totalSearch=" + totalSearch +
				", totalRecommend=" + totalRecommend +
				'}';
	}
}
