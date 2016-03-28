package kr.ac.kw.domain;


public class QueueInputData implements Comparable<QueueInputData>{
	private String keyword;
	private String url;
	private int depth;
	private int referenceCount;
	

	public QueueInputData(String keyword, String url, int depth,
			int referenceCount) {
		super();
		this.keyword = keyword;
		this.url = url;
		this.depth = depth;
		this.referenceCount = referenceCount;
	}

	public String getKeyword() {
		return keyword;
	}


	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public int getDepth() {
		return depth;
	}


	public void setDepth(int depth) {
		this.depth = depth;
	}


	public int getReferenceCount() {
		return referenceCount;
	}


	public void setReferenceCount(int referenceCount) {
		this.referenceCount = referenceCount;
	}


	@Override
	public int compareTo(QueueInputData data) {
		if(this.depth < data.getDepth()){
			return -1;
		} else if (this.depth == data.getDepth()){
			return 0;
		} else {
			return 1;
		}
	}
	

	public QueueInputData addReferCount(){
		this.referenceCount++;		
		return this;
	}
	
	public QueueInputData addDepthCount(){
		this.depth++;
		return this;
	}
}
