package com.barath.app;

import java.io.Serializable;
import java.util.Set;

import com.barath.app.Customer.CustomerGender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 773784271152664523L;
	
	
	private Long accountNumber;
	
	

}
