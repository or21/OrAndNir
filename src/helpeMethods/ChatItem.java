package helpeMethods;

public class ChatItem {
	
	private String chatid;
	private String myName;
	private String otherUser;
	private String dealid;
	private String dealdescription;
	private String date;
	private String storeid;
	private String picture;
	
	public ChatItem(String chatid, String myName, String otherUser, String dealid, String dealdescription, String date
			,String storeid, String picture) {
		this.chatid = chatid;
		this.myName = myName;
		this.otherUser = otherUser;
		this.dealid = dealid;
		this.dealdescription = dealdescription;
		this.date = date;
		this.storeid = storeid;
		this.picture = picture;
	}

	public String getChatid() {
		return chatid;
	}

	public void setChatid(String chatid) {
		this.chatid = chatid;
	}

	public String getMyName() {
		return this.myName;
	}

	public void setMyName(String user1) {
		this.myName = user1;
	}

	public String getOtherUser() {
		return otherUser;
	}

	public void setOtherUser(String user2) {
		this.otherUser = user2;
	}

	public String getDealid() {
		return dealid;
	}

	public void setDealid(String dealid) {
		this.dealid = dealid;
	}

	public String getDealdescription() {
		return dealdescription;
	}

	public void setDealdescription(String dealdescription) {
		this.dealdescription = dealdescription;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
