package io.vicp.goradical.datacollect.entity;

public class SearchResult {
	private int searchResultId;
	private SearchRecord searchRecord;
	private FileInfo fileInfo;

	public SearchResult() {
	}

	public int getSearchResultId() {
		return searchResultId;
	}

	public void setSearchResultId(int searchResultId) {
		this.searchResultId = searchResultId;
	}

	public SearchRecord getSearchRecord() {
		return searchRecord;
	}

	public void setSearchRecord(SearchRecord searchRecord) {
		this.searchRecord = searchRecord;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	@Override
	public String toString() {
		return "SearchResult{" +
				"searchResultId=" + searchResultId +
				", searchRecord=" + searchRecord +
				", fileInfo=" + fileInfo +
				'}';
	}
}
