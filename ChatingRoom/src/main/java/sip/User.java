package sip;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
	private String name;
	private String address;
	public String toAddress(){
		return "sip:"+name+"@"+address;
	}
}
