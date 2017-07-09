package com.barath.app;

import java.util.Set;

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
public class Customer {
	
	private Long customerId;
	
	private String customerName;
	
	private CustomerGender customerGender;	
	
	private Set<Account> accounts;
	
	public enum CustomerGender {
		MALE,
		FEMALE
	}

}
