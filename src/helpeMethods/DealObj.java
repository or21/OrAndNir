package helpeMethods;

public class DealObj {

	private String id;
	private String storeId;
	private String category;
	private String claimedBy;
	private String picture;
	private String deadLine;
	private String dealName;
	private String city;


	public DealObj(String id, String storeId, String category,
			String claimedBy, String picture, String deadLine, String dealName, String city) {
		super();
		this.id = id;
		this.storeId = storeId;
		this.category = category;
		this.claimedBy = claimedBy;
		this.picture = picture;
		this.deadLine = deadLine;
		this.dealName = dealName;
		this.city = city;

	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setClaimedBy(String claimedBy) {
		this.claimedBy = claimedBy;
	}

	public String getClaimedBy() {
		return claimedBy;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPicture() {
		return picture;
	}
	
	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}

	public String getDeadLine() {
		return deadLine;
	}
	
	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealName() {
		return dealName;
	}
	
	public String getCity() {
		return city;
	}
}
