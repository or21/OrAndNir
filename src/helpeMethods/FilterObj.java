package helpeMethods;

public class FilterObj {
	
	private String categoryName;
	private int image;
	
	public FilterObj(String categoryName, int image) {
		this.setCategoryName(categoryName);
		this.setImage(image);
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
