package helpeMethods;

public class ChatItem {
	
	private String chatid;
	private String myName;
	private String otherUser;
	private String dealid;
	private String dealdescription;
	private String date;
	
	public ChatItem(String chatid, String myName, String otherUser, String dealid, String dealdescription, String date) {
		this.chatid = chatid;
		this.myName = myName;
		this.otherUser = otherUser;
		this.dealid = dealid;
		this.dealdescription = dealdescription;
		this.date = date;
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
	
}
