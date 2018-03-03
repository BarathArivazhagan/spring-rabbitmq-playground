package com.barath.app;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2689174839009620081L;
	

	private Long customerId;
	
	private String customerName;
	
	private CustomerGender customerGender;	
	
	private Set<Account> accounts;
	
	public enum CustomerGender {
		MALE,
		FEMALE
	}

}
