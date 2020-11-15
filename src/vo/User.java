package vo;

public class User {
	private String userName;
	private String chrName;
	private String password;
	private String mail;
	private String provinceCode;
	private String cityCode;
	private String provinceName;
	private String cityName;
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public User() {
		super();
	}
	public User(String userName, String chrName, String password) {
		super();
		this.userName = userName;
		this.chrName = chrName;
		this.password = password;
	}
	
	
	public User(String userName,  String password, String chrName,String mail, String province,
			String city) {
		super();
		this.userName = userName;
		this.chrName = chrName;
		this.password = password;
		this.mail = mail;
		this.provinceCode = province;
		this.cityCode = city;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getChrName() {
		return chrName;
	}
	public void setChrName(String chrName) {
		this.chrName = chrName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String province) {
		this.provinceCode = province;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String city) {
		this.cityCode = city;
	}
	
	
	
	
	
}
