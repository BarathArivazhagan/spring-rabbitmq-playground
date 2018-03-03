package com.barath.app;



import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account  implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 773784271152664523L;



    private Long accountNumber;



}
